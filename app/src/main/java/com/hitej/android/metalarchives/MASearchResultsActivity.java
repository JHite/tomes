package com.hitej.android.metalarchives;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;


import com.hitej.android.metalarchives.metallumobjects.search.bandname.BandName;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;

/**
 * Created by jhite on 7/14/16
 *
 * Hosts all individual search result fragments (ie singleband, album, ect)
 */

public class MASearchResultsActivity extends SingleFragmentActivity {

    private static final String TAG = "MASearchResultsActivity";

    //holder for BandSearchResult's Band
    protected static SearchResult mBandCursor;

    public static final String EXTRA_SEARCH_TYPE =
            "com.jhite.android.metalarchives.search_type";

    public static final String EXTRA_RESULTS_SHOW =
            "com.jhite.android.metalarchives.results_show";

    public static final String EXTRA_QUERY_TEXT =
            "com.jhite.android.metalarchives.query_text";

    public static Intent newIntent(Context context, String searchType, String queryText) {
        Intent intent = new Intent(context, MASearchResultsActivity.class);
        intent.putExtra(EXTRA_SEARCH_TYPE, searchType);
        intent.putExtra(EXTRA_QUERY_TEXT, queryText);
        intent.putExtra(EXTRA_RESULTS_SHOW, true);
        Log.i(TAG, "Search Type = " + intent.getStringExtra(EXTRA_SEARCH_TYPE) +
                "\n     Query Text = " + intent.getStringExtra(EXTRA_QUERY_TEXT) +
                "\n     ResultsShow = " + intent.getBooleanExtra(EXTRA_RESULTS_SHOW, false));
        return intent;
    }


    //TODO: create intents that'll open SearchResultFragment with appropriate results


    @Override
    protected Fragment createFragment() {
        //check extras for results and show proper fragment
        //if intent has results show flagged false, show search
        if(!getIntent().getBooleanExtra(EXTRA_RESULTS_SHOW, false)) {
            Log.i(TAG, "fragment created - results show = " + EXTRA_RESULTS_SHOW );
            return new SearchQueryFragment().newInstance();
        }else {
            //If EXTRA = BANDSEARCH, SHOW BANDRESULTSFRAGMENT WITH QUERYTEXT
           Log.i(TAG, "returning new BandSearchResultsFragment" +  "extra query text = "
                   + getIntent().getStringExtra(EXTRA_QUERY_TEXT));
           return new BandSearchResultsFragment()
                    .newInstance( getIntent().getStringExtra(EXTRA_QUERY_TEXT));
            }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
                .commit();
    }

}
