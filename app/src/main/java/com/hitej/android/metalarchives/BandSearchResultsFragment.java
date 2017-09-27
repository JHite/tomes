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

import com.hitej.android.metalarchives.adapters.BandSearchResultsAdapter;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;
import com.hitej.android.metalarchives.net.searchqueries.BandNameQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


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
    private RecyclerView.Adapter mAdapter;
    private static View mView;

    private ArrayList<SearchResult> mBandResults = new ArrayList<>();
    private BandNameQuery mSearchQuery;
    private CompositeDisposable mCompositeDisposable;


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


    //When this fragment is created, it will perform the search from SearchQueryfragment
    //Be aware of any weird Fragment lifecycle stuff esp. with rotation

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

        mResultsRecyclerView =
                (RecyclerView) view.findViewById(R.id.band_search_results_recycler_view);

        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //add band search results to a list to display when fragment is inflated
        //placing here instead of onCreate to test null RecyclerView
        mSearchQuery = new BandNameQuery(queryText, getContext());
        mSearchQuery.start();

        //updateUI();
        setupAdapter();

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

/*
    public void updateUI() {
        if (mResultsRecyclerView == null) {
            mAdapter = new BandSearchResultsAdapter(mBandResults);
            mResultsRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else
            mAdapter.notifyDataSetChanged();
    }
*/

}
