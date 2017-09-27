package com.hitej.android.metalarchives.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hitej.android.metalarchives.R;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;

import java.util.ArrayList;

/**
 * Created by jhite on 7/25/17.
 */

public class BandSearchResultsAdapter extends RecyclerView.Adapter<BandSearchResultsAdapter.ViewHolder> {

    private ArrayList<SearchResult> mBandArrayList = new ArrayList<>();

    public BandSearchResultsAdapter(ArrayList<SearchResult> bandList) {
        mBandArrayList = bandList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_band_search_results_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mBandName.setText(mBandArrayList.get(position).getName());
        holder.mBandGenre.setText(mBandArrayList.get(position).getGenre());
        holder.mBandOrigin.setText(mBandArrayList.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return mBandArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mBandName,mBandGenre,mBandOrigin;
        public ViewHolder(View view) {
            super(view);

            mBandName = (TextView)view.findViewById(R.id.search_results_band_name);
            mBandGenre = (TextView)view.findViewById(R.id.search_results_band_genre);
            mBandOrigin = (TextView)view.findViewById(R.id.search_results_band_origin);
        }
    }
}