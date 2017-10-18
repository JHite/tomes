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


import com.hitej.android.metalarchives.metallumobjects.band.bandid.Discography;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jhite on 11/26/16.
*/
public class BandDiscogFragment  extends Fragment {

    private static boolean isInflated;
    private static final String TAG = "BandDiscogFragment";
    public static final String ARGS_BAND = "Band Argument";
    private DiscAdapter mAdapter;
    private RecyclerView mDiscRecyclerView;


    public static BandDiscogFragment newInstance(String bandID) {
        BandDiscogFragment fragment = new BandDiscogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_BAND, bandID);
        Intent intent = new Intent();
        intent.putExtras(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_band_discog, container, false);

        //setInflated to true
        mDiscRecyclerView
                = (RecyclerView)view.findViewById(R.id.fragment_band_discog_recycler_view);
        mDiscRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setIsInflated(true);
        updateUI();

        return view;
    }


    private void setupAdapter() {
        if (isAdded()) {
            mDiscRecyclerView.setAdapter(new BandDiscogFragment.DiscAdapter(mDiscs));
        }
    }


    private class DiscHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Discography mDisc;

        private ImageView mBandLogoImageView;
        private TextView mDiscNameText, mDiscRelease, mBandOriginText;


        public DiscHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //TODO:fix onclick crash

            mDiscNameText = (TextView) itemView.findViewById(R.id.fragment_band_discog_album_name_textview);
            mDiscRelease = (TextView) itemView.findViewById(R.id.fragment_band_discog_album_release);

        }

        public void bindDisc(Discography disc) {
            mDisc = disc;
            mDiscNameText.setText(mDisc.getTitle());
            mDiscRelease.setText(mDisc.getYear());
        }

        @Override
        public void onClick(View v) {
            //TODO:7-4-14: Launch DiscInfoActivity with mDisc
            Log.i(TAG, mDisc.getTitle() + "'s Disc clicked");
            //Intent intent = DiscInfoActivity.newIntent(getContext(), mDisc);
            //startActivity(intent);


        }
    }


    private class DiscAdapter extends RecyclerView.Adapter<BandDiscogFragment.DiscHolder> {

        private List<Discography> mDiscList;

        public DiscAdapter(List<Discography> discsItems) {
            mDiscList = discsItems;
        }


        @Override
        public BandDiscogFragment.DiscHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_band_discog_album_item, viewGroup, false);
            return new BandDiscogFragment.DiscHolder(view);
        }

        @Override
        public void onBindViewHolder(BandDiscogFragment.DiscHolder discHolder, int position) {
            Discography disc = mDiscList.get(position);
            discHolder.bindDisc(disc);

        }

        @Override
        public int getItemCount() {
            return mDiscList.size();
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
        BandDiscogFragment.isInflated = isInflated;
    }

    public void updateUI(){
        if(mAdapter == null){
            mAdapter = new DiscAdapter(mDisc);
            mDiscRecyclerView.setAdapter(mAdapter);
        } else{
            //may need more here
            mAdapter.notifyDataSetChanged();
        }
    }
}
