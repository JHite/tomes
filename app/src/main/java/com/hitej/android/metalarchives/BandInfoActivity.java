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


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Band;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.CurrentLineup;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Details;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Discography;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.BandName;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;
import com.hitej.android.metalarchives.net.MetalArchivesAPI;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.roughike.bottombar.OnTabClickListener;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class BandInfoActivity extends SingleFragmentActivity {

    private static final String TAG = "BandInfoActivity";
    private BottomBar mBottomBar;
    private Context mContext = this;
    protected static String mBandID; // reference to the band to display info about
    public static final String EXTRA_QUERY_ID = "com.jhite.android.metalarchives.bandinfo.queryid";

    public static Intent newIntent(Context context, String stringID) {
        Intent intent = new Intent(context, BandInfoActivity.class);
        intent.putExtra(EXTRA_QUERY_ID, stringID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new BandAboutFragment().newInstance(getIntent().getStringExtra(EXTRA_QUERY_ID));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBandID = getIntent().getStringExtra(EXTRA_QUERY_ID);


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
                                            BandAboutFragment.newInstance
                                                    (getIntent().getStringExtra(EXTRA_QUERY_ID)))
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






}

