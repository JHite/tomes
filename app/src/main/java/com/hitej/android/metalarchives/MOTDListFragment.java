package com.hitej.android.metalarchives;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

/**
 * Created by jhite on 3/26/16.
 */
public class MOTDListFragment extends Fragment {

//TODO: Correcly populate text with MOTDs from site
    //waiting on additonal api work
    private static final String TAG = "MOTDListFragment";

    private static final String BAND_QUERY = "Band query";
    private static final String ALBUM_QUERY = "Album query";
    private static final String ARTIST_QUERY = "Artist query";


    private RecyclerView mNewsItemRecyclerView;
    private MOTDAdapter mAdapter;

    private FloatingActionMenu mSearchFAM;
    private FloatingActionButton mSearchArtistFAB;
    private FloatingActionButton mSearchBandFAB;

    private Callbacks mCallbacks;

    private static boolean isInflated;

    public interface Callbacks{
        void onMOTDSelected(MOTDItem motdItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        //setInflated to true
        setInflated(true);


        mNewsItemRecyclerView = (RecyclerView)view
                .findViewById(R.id.fragment_news_list_recycler_view);
        mNewsItemRecyclerView.setHasFixedSize(true);

        mNewsItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Clans FAB code
        mSearchFAM = (FloatingActionMenu) view.findViewById(R.id.search_menu_fam);
        mSearchBandFAB = (FloatingActionButton) view.findViewById(R.id.search_band_fab);
        mSearchBandFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = MASearchActivity.newIntent(getContext(),BAND_QUERY);
                startActivity(intent);
                Log.i(TAG, "button pressed");

            }
        });

        mSearchArtistFAB = (FloatingActionButton) view.findViewById(R.id.search_artist_fab);
        mSearchArtistFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = MASearchActivity.newIntent(getContext(),ARTIST_QUERY);
               // intent.putExtra(EXTRA_SEARCH_TYPE,ARTIST_QUERY);
                startActivity(intent);
            //TODO: unpack extras in SEARCHACTIVIty, save as bundle
                // to maintain search state through rotations
            }
        });

        updateUI();

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //TODO: Create search options dialog
        // Boom menu buttons not compatible with Fragments without extra weird config
        //search for Material Design dialog libs
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_motd_list, menu);
    }
    /*      Unnecessary until settings menu required
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                Log.i(TAG, "Search button pressed");


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
    @Override
    public void onDestroy(){
        super.onDestroy();
        setInflated(false);
    }


    public static MOTDListFragment newInstance(){
        return new MOTDListFragment();
    }


    private class MOTDHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private MOTDItem mMOTDItem;

        private TextView mTitleTextView;
        private TextView mBodyTextView;

        public MOTDHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.motd_title_textview);
            mBodyTextView = (TextView) itemView.findViewById(R.id.motd_body_textview);
        }

        public void bindMOTD(MOTDItem motdItem){
            mMOTDItem = motdItem;
            mTitleTextView.setText(motdItem.getMotdTitle());
            mBodyTextView.setText(motdItem.getMotdBody());
        }

        @Override
        public void onClick(View v){
            //mCallbacks.onMOTDSelected(mMOTDItem);
            Log.i(TAG, "News Story Clicked");

        }
    }

    private class MOTDAdapter extends RecyclerView.Adapter<MOTDHolder>{

        private List<MOTDItem> mMOTDItems;

        public MOTDAdapter(List<MOTDItem> motdItems){
            mMOTDItems = motdItems;
        }

        @Override
        public MOTDHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_motd_item, parent, false);
            return new MOTDHolder(view);
        }

        @Override
        public void onBindViewHolder(MOTDHolder holder, int position){
            MOTDItem motdItem = mMOTDItems.get(position);
            holder.bindMOTD(motdItem);
            Log.i(TAG, "View binded!");
        }

        @Override
        public int getItemCount(){
            return mMOTDItems.size();
        }

        public void setMOTDItems(List<MOTDItem> motdItems){
            mMOTDItems = motdItems;
        }
    }

    public void updateUI(){
       MOTDList motdList = MOTDList.get(getActivity());
       List<MOTDItem> motdItems = motdList.getMOTDs();

       if(motdItems.size() == 0){
           motdList.addMOTDs(getActivity(),10);
           motdItems = motdList.getMOTDs();
       }

       Log.i(TAG, "MOTDItems count = " + motdItems.size());

        if(mAdapter == null){
            mAdapter = new MOTDAdapter(motdItems);
            mNewsItemRecyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.setMOTDItems(motdItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    public static boolean isInflated() {
        return isInflated;
    }

    private void setInflated(boolean inflated) {
        isInflated = inflated;
    }
}
