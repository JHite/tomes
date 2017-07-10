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

import com.github.loki.afro.metallum.entity.Band;
import com.github.loki.afro.metallum.entity.Disc;
import com.github.loki.afro.metallum.entity.Track;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jhite on 1/18/17.
 * Activity launched from BandInfoActivity - BandDiscogFragment that will display Album details
 */

public class DiscInfoFragment extends Fragment{
    private static Disc mDisc;
    private static Band mBandResult;
    private List<Track> mTrackList;
    private RecyclerView mDiscInfoRecyclerView;
    private static boolean isInflated = false;
    private TrackAdapter mAdapter;
    private static final String TAG = "DiscInfoFragment";

    public static DiscInfoFragment newInstance(){
        return new DiscInfoFragment();
    }

    public static DiscInfoFragment newInstance(Disc disc){
        DiscInfoFragment fragment = newInstance();
        DiscInfoActivity.mDiscCursor = disc;
        return fragment;
    }

    public static Intent newIntent(Context context, Disc disc) {
        mDisc = disc;
        return new Intent(context, DiscInfoFragment.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_disc_info, container, false);

        //sets tracklist for recyclerview
        mTrackList = mDisc.getTrackList();

        mDiscInfoRecyclerView = (RecyclerView)
                view.findViewById(R.id.disc_info_recycler_view);
        mDiscInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setIsInflated(true);

        updateUI();


        return view;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mDiscInfoRecyclerView.setAdapter(new TrackAdapter(mTrackList));
        }
    }

    private class TrackHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Track mTrack;

        private TextView mTrackName;


        public TrackHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTrackName = (TextView)itemView.findViewById(R.id.disc_info_track_name);

        }

        public void bindTrack(Track track){
            mTrack = track;
            mTrackName.setText(track.getName());

        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, mTrack.toString() + "'s TrackHolders clicked");
            // an extra to show BandAboutFragment
            //Intent intent = BandInfoActivity.newIntent(getContext(), mBand);
            //startActivity(intent);


        }
    }


    private class TrackAdapter extends RecyclerView.Adapter<TrackHolder> {


        public TrackAdapter(List<Track> trackItems) {
              mTrackList = trackItems;
        }


        @Override
        public DiscInfoFragment.TrackHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_disc_info_track_item, viewGroup, false);
            return new DiscInfoFragment.TrackHolder(view);
        }

        @Override
        public void onBindViewHolder(TrackHolder trackHolder, int position) {
            Track track = mTrackList.get(position);
            trackHolder.bindTrack(track);

        }

        @Override
        public int getItemCount() {
            return mTrackList.size();
        }

    }




    public static boolean isInflated() {
        return isInflated;
    }

    public static void setIsInflated(boolean isInflated) {
       DiscInfoFragment.isInflated = isInflated;
    }
    public void updateUI(){
        if(mAdapter == null){
            mAdapter = new TrackAdapter(mTrackList);
            mDiscInfoRecyclerView.setAdapter(mAdapter);
        } else{
            //may need more here
            mAdapter.notifyDataSetChanged();
        }
    }
}





