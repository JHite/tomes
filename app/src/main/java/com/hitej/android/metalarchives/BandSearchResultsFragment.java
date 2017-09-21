package com.hitej.android.metalarchives;

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

    private ArrayList<SearchResult> mBandResults = new ArrayList<>();
    private BandNameQuery mSearchQuery;
    private CompositeDisposable mCompositeDisposable;


    public static BandSearchResultsFragment newInstance() {
        return new BandSearchResultsFragment();
    }

    public static BandSearchResultsFragment newInstance(String queryText) {
        BandSearchResultsFragment fragment = newInstance();

        Bundle args = new Bundle();

        args.putString(ARG_QUERY_TEXT, queryText );
        Log.i(TAG, queryText + " placed into bundle");

        fragment.setArguments(args);
        return fragment;
    }


    //When this fragment is created, it will perform the search from SearchQueryfragment
    //Be aware of any weird Fragment lifecycle stuff esp. with rotation

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        searchType = getArguments().getString(ARG_SEARCH_TYPE);
        queryText = getArguments().getString(ARG_QUERY_TEXT);
        Log.i(TAG, queryText + " is text placed in search query");

        mCompositeDisposable = new CompositeDisposable();


        //add band search results to a list to display when fragment is inflated
        mSearchQuery = new BandNameQuery(queryText);
        mSearchQuery.start();

        }





    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_band_search_results, container, false);

        mResultsRecyclerView =
                (RecyclerView) view.findViewById(R.id.band_search_results_recycler_view);

        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();

        return view;

    }

    @Override
    public void onDestroy(){
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
    private class BandHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Band mBand;

        private ImageView mBandLogoImageView;
        private TextView mBandNameText, mBandGenreText, mBandOriginText;


        public BandHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


            mBandNameText = (TextView)itemView.findViewById(R.id.search_results_band_name);
            mBandGenreText = (TextView)itemView.findViewById(R.id.search_results_band_genre);
            mBandOriginText = (TextView)itemView.findViewById(R.id.search_results_band_origin);

        }

        public void bindBand(Band band){
            mBand = band;
            mBandNameText.setText(mBand.getName().toString());
            mBandGenreText.setText(mBand.getGenre().toString());
            mBandOriginText.setText(mBand.getLocation());

        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, mBand.toString() + "'s BandHolder clicked");
            // an extra to show BandAboutFragment
            Intent intent = BandInfoActivity.newIntent(getContext(), mBand);
            startActivity(intent);


        }
    }


    private class BandAdapter extends RecyclerView.Adapter<BandHolder> {

        //private List<Band> mBandList;

        public BandAdapter(List<Band> bandItems) {
            mBandResults = bandItems;
        }


        @Override
        public BandHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_band_search_results_item, viewGroup, false);
            return new BandHolder(view);
        }

        @Override
        public void onBindViewHolder(BandHolder bandHolder, int position) {
            Band bandResult = mBandResults.get(position);
            bandHolder.bindBand(bandResult);

        }

        @Override
        public int getItemCount() {
            return mBandResults.size();
        }

    }



    public static boolean isInflated() {
        return isInflated;
    }

    public static void setIsInflated(boolean isInflated) {
        BandSearchResultsFragment.isInflated = isInflated;
    }
    */
}
