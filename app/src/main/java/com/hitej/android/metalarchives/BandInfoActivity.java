package com.hitej.android.metalarchives;


/*
   7/18/16
   Activity that will provide bands information for BandSearchResultsFragment

   Bottombar will host preferable 5 tabs if possble
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;



import com.hitej.android.metalarchives.metallumobjects.search.bandname.BandName;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.roughike.bottombar.OnTabClickListener;

import java.util.List;


public class BandInfoActivity extends SingleFragmentActivity {

    private static final String TAG = "BandInfoActivity";
    private BottomBar mBottomBar;
    private Context mContext = this;
    protected static SearchResult mBand; // reference to the band to display info about

    public static Intent newIntent(Context context, SearchResult band) {
        mBand = band;
        return new Intent(context, BandInfoActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return new BandAboutFragment().newInstance(mBand);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Launch Observables that will gather discog, band member info, etc


        //Attach Bottom bar to this Activity and populate its contents
        //while creating its onClickListeners
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_band_info, new OnMenuTabClickListener() {
            @Override
            //TODO: finish coding bottombar select
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId){
                    case R.id.bottom_bar_band_about:
                        //Check which page is currently inflated and then inflate selected choice
                        if(BandAboutFragment.isInflated())
                            return;
                        else{
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container,
                                            BandAboutFragment.newInstance())
                                    .commit();
                            Log.i(TAG, "inflating BandAboutFragment! BandAboutFragment.isInflated =  "
                                    + BandAboutFragment.isInflated());
                        }
                        break;
                        /*
                    case R.id.bottom_bar_band_discography:

                        if(BandDiscogFragment.isInflated())
                            return;
                        else{
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container,
                                            BandDiscogFragment.newInstance(mBand))
                                    .commit();
                            Log.i(TAG, "inflating BandAboutFragment! BandAboutFragment.isInflated =  "
                                    + BandDiscogFragment.isInflated());
                             }



                        break; */

                    default:

                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottom_bar_band_about) {
                    //home tab is selected
                } else if (menuItemId == R.id.bottom_bar_band_discography) {
                    //favorites
                } else {
                    //random
                }
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");

        mBottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:

                        return;
                    case 1:

                        return;
                    case 2:

                    case 3:

                    case 4:

                    default:
                        Log.i(TAG, "Tab # " + position + " clicked");
                }
            }

            @Override
            public void onTabReSelected(int position) {

            }
        });

    }


    protected void onSavedInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mBottomBar.onSaveInstanceState(outState);
    }

    /*Query's "/band/id" endpoint
        Information such as band details, discog, current lineup obtained here
     */
    private class BandInfoQuery {

        static final String BASE_URL = "http://em.wemakesites.net/";
        public static final String TAG = "BandInfoQuery";
        private String queryText = "";
        private List mResultsList;
       

        private final String metalArchivesAPIKey = "f60b07b8-612e-4a3b-95f5-1df3250a72ac";

        private BandInfoQuery(String bandID){
            queryText = bandName;
        }

        private void start() {
            //Log.i(TAG, Resources.getSystem().getString(R.string.metalArchivesAPIKey));
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            RxJava2CallAdapterFactory rxAdapter
                    = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

            // Can be Level.BASIC, Level.HEADERS, or Level.BODY
            // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //TODO: add log to okhttpclient
            //intercepter code
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    HttpUrl originalHttpUrl = originalRequest.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_key", metalArchivesAPIKey)
                            .build();

                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            }).addInterceptor(httpLoggingInterceptor)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(rxAdapter)
                    .build();

            MetalArchivesAPI api = retrofit.create(MetalArchivesAPI.class);
            Observable<BandName> band = api.searchBandName(queryText);

            band
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse,this::handleError);

        }


        private void handleResponse(BandName bandResults){
            mBandSearchResultsList = new ArrayList(bandResults.getData().getSearchResults());
            Log.i(TAG, "result list size = " + mBandSearchResultsList.size());

            mAdapter = new BandSearchResultsAdapter(mBandSearchResultsList);


            mResultsRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();


        }

        private void handleError(Throwable e){
            Log.i (TAG, "ERROR = " + e.toString());
        }
    }




}

