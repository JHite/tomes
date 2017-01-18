package com.hitej.android.metalarchives;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import de.loki.metallum.entity.Band;

/**
 * Created by jhite on 7/14/16
 *
 * Hosts all individual search result fragments (ie singleband, album, ect
 */

public class MASearchResultsActivity extends SingleFragmentActivity {

    private static final String TAG = "MASearchResultsActivity";

    //holder for BandSearchResult's Band
    protected static Band mBandCursor;

    public static final String EXTRA_SEARCH_TYPE =
            "com.jhite.android.metalarchives.search_type";

    public static final String EXTRA_RESULTS_SHOW =
            "com.jhite.android.metalarchives.results_show";
    public static final String ARG_SEARCH_TYPE = "Search Type";

    public static Intent newIntent(Context context, String searchType) {
        Intent intent = new Intent(context, MASearchResultsActivity.class);
        intent.putExtra(EXTRA_SEARCH_TYPE, searchType);
        return intent;
    }


    //TODO: create intents that'll open SearchResultFragment with appropriate results


    @Override
    protected Fragment createFragment() {
        //check extras for results and show proper fragment
        //if intent has results show flagged false, show search
        if(!getIntent().getBooleanExtra(EXTRA_RESULTS_SHOW, false)) {
            Log.i(TAG, "fragment created - results show = false");
            return new SearchQueryFragment().newInstance();
        }else {
            //put search type as bundle for SearchResults to distinguish search type

            Log.i(TAG, "There is something weird going on in your createFragment()");
           /* return new BandSearchResultsFragment()
                    .newInstance( "");
                    */
        }
        //this will probably be really dangerous YOLO
        return null;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    protected void onSavedInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    //(7/3) The below will decide which fragment to show based upon search typed decided in
    // SearchQueryFragment's buttonSubmitQuery

    public void showBandSearchResults( String queryTextString){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container
                        , BandSearchResultsFragment.newInstance(queryTextString))
                //TODO: pack query properly and use proper method
                .commit();
    }
}
