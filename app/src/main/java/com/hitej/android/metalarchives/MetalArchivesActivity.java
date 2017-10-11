package com.hitej.android.metalarchives;
//BottomBar from https://github.com/roughike/BottomBar

/*
    6/3/16
    Hosts initial view fragments and launches MASearchActivity upon searching

 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.roughike.bottombar.OnTabClickListener;
public class MetalArchivesActivity extends SingleFragmentActivity {

    private static final String TAG = "MetalArchivesActivity";
    private BottomBar mBottomBar;
    private Context mContext = this;

    public static Intent newIntent(Context context) {
        return new Intent(context, MetalArchivesActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return new MOTDListFragment().newInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Attach Bottom bar to this Activity and populate its contents
        //while creating its onClickListeners
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_main, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottom_bar_tab_home) {
                    //home tab is selected
                } else if (menuItemId == R.id.bottom_bar_tab_favorites) {
                    //favorites
                } else {
                    //random
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottom_bar_tab_home) {
                    //home tab is selected
                } else if (menuItemId == R.id.bottom_bar_tab_favorites) {
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
                        //Check which page is currently inflated and then inflate selected choice
                        if(MOTDListFragment.isInflated())
                            return;
                        else{
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container,
                                            MOTDListFragment.newInstance())
                                    .commit();
                            Log.i(TAG, "change tab - isInflated Status - "
                                    + MOTDListFragment.isInflated());
                        }

                        return;
                    case 1:
                        if(FavoritesFragment.isInflated())
                            return;
                        else{
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container,
                                            FavoritesFragment.newInstance())
                                    .commit();
                            Log.i(TAG, "change tab - isInflated Status - "
                                    + FavoritesFragment.isInflated());
                        }
                        return;
                    case 2:
                        if(RandomBandFragment.isInflated())
                            return;
                        else{
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container,
                                            RandomBandFragment.newInstance())
                                    .commit();
                            Log.i(TAG, "change tab - isInflated Status - "
                                    + RandomBandFragment.isInflated());
                        }


                        /*
                         if (findViewById(R.id.detail_fragment_container) == null){
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
                         */
                    default:
                        Log.i(TAG, "Tab # " +position + " clicked");
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

