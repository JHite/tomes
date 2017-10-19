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

import com.hitej.android.metalarchives.metallumobjects.band.bandid.CurrentLineup;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Discography;

import java.util.List;

/**
 * Created by jhite on 1/18/17.
 * Fragment of BandInfoActivity that will display a band's roster
 */

public class BandRosterFragment extends Fragment{

    private static final String TAG = "BandRosterFragment";
    private List<CurrentLineup> mCurrentLineup;
    private RecyclerView mRosterRecyclerView;
    private static boolean isInflated;
    private LineupAdapter mAdapter;


    public static BandRosterFragment newInstance() {
        return new BandRosterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentLineup = BandInfoActivity.mBand.getData().getCurrentLineup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_band_roster, container, false);

        //setInflated to true
        mRosterRecyclerView
                = (RecyclerView)view.findViewById(R.id.band_roster_recycler_view);
        mRosterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setIsInflated(true);
        updateUI();

        return view;
    }


    private void setupAdapter() {
        if (isAdded()) {
            mRosterRecyclerView.setAdapter(mAdapter);
        }
    }


    private class LineupHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private CurrentLineup mCurrentLineup;

        private ImageView mBandLogoImageView;
        private TextView mLineupNameText, mLineupInstrumentText, mLineupYearText;


        public LineupHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //TODO:fix onclick crash

            mLineupNameText = (TextView) itemView.findViewById(R.id.band_roster_name_textview);
            mLineupInstrumentText = (TextView) itemView.findViewById(R.id.band_roster_instrument_textview);
            mLineupYearText = (TextView)itemView.findViewById(R.id.band_roster_years_textview);

        }

        public void bindLineup(CurrentLineup currentLineup) {
            mCurrentLineup = currentLineup;
            Log.i(TAG,  mCurrentLineup.getName() + "'s member being bound");
            mLineupNameText.setText((CharSequence)mCurrentLineup.getName());
            mLineupInstrumentText.setText(mCurrentLineup.getInstrument());
            mLineupYearText.setText(mCurrentLineup.getYears());
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, mCurrentLineup.getName() + "'s member clicked");


        }
    }


    private class LineupAdapter extends RecyclerView.Adapter<BandRosterFragment.LineupHolder> {

        private List<CurrentLineup> mLineupList;

        public LineupAdapter(List<CurrentLineup> lineupList) {
            mLineupList = lineupList;
        }


        @Override
        public LineupHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_band_roster_item, viewGroup, false);
            return new BandRosterFragment.LineupHolder(view);
        }

        @Override
        public void onBindViewHolder(LineupHolder lineupHolder, int position) {
            CurrentLineup lineup = mLineupList.get(position);
            lineupHolder.bindLineup(lineup);

        }

        @Override
        public int getItemCount() {
            return mLineupList.size();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setIsInflated(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public static boolean isInflated() {
        return isInflated;
    }

    public void setIsInflated(boolean isInflated) {
        BandRosterFragment.isInflated = isInflated;
    }

    public void updateUI(){
        if(mAdapter == null){
            mAdapter = new BandRosterFragment.LineupAdapter(mCurrentLineup);
            mRosterRecyclerView.setAdapter(mAdapter);
        } else{
            //may need more here
            mAdapter.notifyDataSetChanged();
        }
    }
}
