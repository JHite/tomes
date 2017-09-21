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


public class BandInfoActivity extends SingleFragmentActivity {

    private static final String TAG = "BandInfoActivity";
    private BottomBar mBottomBar;
    private Context mContext = this;
    protected static SearchResult mBand; // reference to the band to display info about


    /*      un-used
    public static Intent newIntent(Context context) {
        return new Intent(context, BandInfoActivity.class);
    }*/

    public static Intent newIntent(Context context, SearchResult band) {
        mBand = band;
        return new Intent(context, BandInfoActivity.class);
    }

    @Override
    //TODO: create fragment to replace
    protected Fragment createFragment() {
        return new MOTDListFragment().newInstance();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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



}

