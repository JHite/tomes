package com.hitej.android.metalarchives;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.loki.metallum.entity.Band;
import de.loki.metallum.entity.Disc;

/**
 * Created by jhite on 11/26/16.
 */

public class BandDiscogFragment  extends Fragment{

    private static boolean isInflated;
    private static final String TAG = "BandDiscogFragment";
    private static List<Disc> mDiscs;


    public static BandDiscogFragment newInstance(Band bandResult){
        BandDiscogFragment fragment = new BandDiscogFragment();
        //load selected Band's discograpgy into an array of disc to use in this fragment
        mDiscs = bandResult.getDiscs();

        //Bundle args = new Bundle();
       /* args.putSerializable(ARGS_BAND, (Serializable) bandResult);
        Intent intent = new Intent();
        intent.putExtras(args);
        */


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_band_discog, container, false);

        final String searchType = getActivity().getIntent()
                .getStringExtra(MASearchActivity.EXTRA_SEARCH_TYPE);

        //setInflated to true
        setIsInflated(true);








        return view;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mResultsRecyclerView.setAdapter(new BandSearchResultsFragment.BandAdapter(mBandResults));
        }
    }
    //TODO: set up RecyclerView for Disc to load as results load from query **

    private class BandHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Band mBand;

        private ImageView mBandLogoImageView;
        private TextView mBandNameText, mBandGenreText, mBandOriginText;


        public BandHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //TODO:fix onclick crash

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


    private class BandAdapter extends RecyclerView.Adapter<BandSearchResultsFragment.BandHolder> {

        //private List<Band> mBandList;

        public BandAdapter(List<Band> bandItems) {
            mBandResults = bandItems;
        }


        @Override
        public BandSearchResultsFragment.BandHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_band_search_results_item, viewGroup, false);
            return new BandSearchResultsFragment.BandHolder(view);
        }

        @Override
        public void onBindViewHolder(BandSearchResultsFragment.BandHolder bandHolder, int position) {
            //Band band = mBandList.get(position);
            Band bandResult = mBandResults.get(position);
            bandHolder.bindBand(bandResult);

        }

        @Override
        public int getItemCount() {
            return mBandResults.size();
        }

    @Override
    public void onDestroy(){
        super.onDestroy();
        setIsInflated(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

    }

    public static boolean isInflated() {
        return isInflated;
    }

    public static void setIsInflated(boolean isInflated) {
        BandDiscogFragment.isInflated = isInflated;
    }
}
