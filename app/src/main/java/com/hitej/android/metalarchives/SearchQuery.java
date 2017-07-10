package com.hitej.android.metalarchives;

import android.util.Log;

import com.github.loki.afro.metallum.entity.Band;
import com.github.loki.afro.metallum.search.query.BandSearchQuery;
import com.github.loki.afro.metallum.search.service.advanced.BandSearchService;


import java.util.List;

/**
 * Created by jhite on 6/5/16.
 */
public class SearchQuery {

    private final String TAG = "SearchQuery";

    protected List<Band> getBands() {
        //6/4 -  create method to get search results
        //       preferably in its own helper class

        final BandSearchService bandSearchService = new BandSearchService();
        final BandSearchQuery bandSearchQuery = new BandSearchQuery();

        //set bandSearchQuerys band name to query text and exact match's boolean to false to get all
        //results instead of exact results

        bandSearchQuery.setBandName(BandSearchResultsFragment.getQueryText(), false);
        try{
            final List<Band> resultBandList = bandSearchService.performSearch(bandSearchQuery);
            Log.i(TAG, resultBandList.size()+ " = size of search Result List ");
            for(final Band band : resultBandList) {
                Log.i(TAG, "Band name = " + band.getName());
                Log.i(TAG, "Band Genre = " + band.getGenre());
                Log.i(TAG, "end of current band \n --------");
            }

            return resultBandList;

        //} catch (MetallumException e ) {
        } catch (Exception e ) {
            Log.i(TAG, e.toString());}




        return null;
    }

    public List<BandSearchResultsFragment.BandResult> convertBandsToResults(List<Band> bands) {
        //http://stackoverflow.com/questions/933447/how-do-you-cast-a-list-of-supertypes-to-a-list-of-subtypes
        //needed a way to cast bands to bandresults

        List<BandSearchResultsFragment.BandResult> mBandResultsList
                = (List<BandSearchResultsFragment.BandResult>)(List<?>) bands;


        return mBandResultsList;
    }
}
