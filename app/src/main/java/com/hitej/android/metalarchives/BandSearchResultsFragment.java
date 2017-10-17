package com.hitej.android.metalarchives;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.BandName;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;
import com.hitej.android.metalarchives.net.MetalArchivesAPI;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by jhite on 5/23/16.
 *
 * This may ultimately be combined with MASearchActivity to reduce use of Fragments
 *
 * TODO: Update Activity title, implement correct backstack
 */
public class BandSearchResultsFragment extends Fragment {
    private static String searchType, queryText;
    private static Boolean isInflated;

    private static final String TAG = "BandSearchResultsFrag";
    private static final String ARG_SEARCH_TYPE = "Search Type";
    public static final String ARG_QUERY_TEXT = "Query Text";

    private RecyclerView mResultsRecyclerView;
    private BandSearchResultsAdapter mAdapter;
    private static View mView;
    private Callbacks mCallbacks;
    private static String mSearchResultID;

    private ArrayList<SearchResult> mBandResults = new ArrayList<>();
    private SearchResult mSearchResult;
    private BandNameQuery mSearchQuery;
    private CompositeDisposable mCompositeDisposable;

    public interface Callbacks{
        void onResultSelected(SearchResult sr);
    }

    public static BandSearchResultsFragment newInstance() {
        return new BandSearchResultsFragment();
    }

    public static BandSearchResultsFragment newInstance(String queryText) {
        BandSearchResultsFragment fragment = newInstance();
        Bundle args = new Bundle();

        args.putString(ARG_QUERY_TEXT, queryText);
        Log.i(TAG, queryText + " placed into bundle");

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchType = getArguments().getString(ARG_SEARCH_TYPE);
        queryText = getArguments().getString(ARG_QUERY_TEXT);
        Log.i(TAG, queryText + " is text placed in search query");

        mCompositeDisposable = new CompositeDisposable();


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_band_search_results, container, false);
        setIsInflated(true);
        Log.i(TAG, "FRAGMENT_BAND_SEARCH_RESULTS INFLATED");

        mResultsRecyclerView =
                (RecyclerView) view.findViewById(R.id.band_search_results_recycler_view);
        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //add band search results to a list to display when fragment is inflated
        //placing here instead of onCreate to test null RecyclerView
        mSearchQuery = new BandNameQuery(queryText);
        mSearchQuery.start();





        setupAdapter();
        updateUI();
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setIsInflated(false);
        mCompositeDisposable.clear();
    }

    public static String getQueryText() {
        return queryText;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mResultsRecyclerView.setAdapter(new BandSearchResultsAdapter(mBandResults));
        }
    }



    public static Boolean getIsInflated() {
        return isInflated;
    }

    public static void setIsInflated(Boolean isInflated) {
        BandSearchResultsFragment.isInflated = isInflated;
    }

    private class ResultHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        private TextView mBandName, mBandGenre, mBandOrigin;


        public ResultHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mBandName = (TextView) view.findViewById(R.id.search_results_band_name);
            mBandGenre = (TextView) view.findViewById(R.id.search_results_band_genre);
            mBandOrigin = (TextView) view.findViewById(R.id.search_results_band_origin);
        }

        public void bindResult(SearchResult searchResult){
            mSearchResult = searchResult;
            Log.i(TAG, mSearchResult.getName() + "\n" + mSearchResult.getId() + "being bound to view");
            mBandName.setText(searchResult.getName());
            mBandGenre.setText(searchResult.getGenre());
            mBandOrigin.setText(searchResult.getCountry());
        }

        @Override
        public void onClick(View v){
            mSearchResultID = mSearchResult.getId();
            Log.i(TAG,  mSearchResult.getName() + "'s ResultHolder clicked!"
                    + mSearchResultID + " = ID");

            //pass the band's id to BandInfoActivity in order
            Intent intent = BandInfoActivity.newIntent(getContext(), mSearchResultID);
            startActivity(intent);
        }
    }

    private class BandSearchResultsAdapter extends RecyclerView.Adapter<ResultHolder> {
        //TODO: 10-16-17: fix bug where onClick will load wrong band
        private List<SearchResult> mBandResultList ;

        private BandSearchResultsAdapter(List<SearchResult> bandList) {
            mBandResultList = bandList;

        }

        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.fragment_band_search_results_item, parent, false);
            return new ResultHolder(view);
        }

        @Override
        public void onBindViewHolder(ResultHolder holder, int position) {
            SearchResult sr = mBandResultList.get(position);
            holder.bindResult(sr);
            Log.i(TAG, "View binded! Array Position: " + position);
        }

        @Override
        public int getItemCount() {
            return mBandResultList.size();
        }


    }

    public void updateUI() {
        if(mAdapter == null){
            Log.i(TAG, "UpdateUI is launching new bandresultAdapter");
            mAdapter = new BandSearchResultsAdapter(mBandResults);
            mResultsRecyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.notifyDataSetChanged();
        }
    }

    private class BandNameQuery {

        static final String BASE_URL = "http://em.wemakesites.net/";
        public static final String TAG = "BandNameQuery";
        private String queryText = "";
        private ArrayList mBandSearchResultsList;
        private BandSearchResultsAdapter mAdapter;

        private final String metalArchivesAPIKey = "f60b07b8-612e-4a3b-95f5-1df3250a72ac";

        private BandNameQuery(String bandName){
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

