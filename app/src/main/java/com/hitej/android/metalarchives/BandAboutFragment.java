package com.hitej.android.metalarchives;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.loki.afro.metallum.entity.Band;


/**
 * Created by jhite on 7/24/16.
 *
 * Initial fragment returned upon clicking on a Band's page.
 * Displays band logo, photo and general info about the band.
 *
 */

public class BandAboutFragment extends Fragment {

    private static boolean isInflated;

    private static final String TAG = "BandAboutFragment";
    private static final String ARGS_BAND = "Band Argument";


    private static String searchTypePlaceholder;
    private Band mBandResult;



    public static BandAboutFragment newInstance(){
        return new BandAboutFragment();
    }

    public static BandAboutFragment newInstance(Band bandResult){
        BandAboutFragment fragment = newInstance();

        MASearchResultsActivity.mBandCursor = bandResult;
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
        View view = inflater.inflate(R.layout.fragment_band_about, container, false);

        final String searchType = getActivity().getIntent()
                .getStringExtra(MASearchActivity.EXTRA_SEARCH_TYPE);
        mBandResult = BandInfoActivity.mBand;

        //setInflated to true
        setIsInflated(true);

        TextView bandNameText = (TextView) view.findViewById(R.id.band_about_name_textview);
        Log.i(TAG, "Setting bandNameText to :" +  mBandResult.getName());
        bandNameText.setText(mBandResult.getName());

        TextView bandOriginText = (TextView)view.findViewById(R.id.band_about_origin_textview);
        Log.i(TAG, "Setting bandOriginText to :" +  mBandResult.getLocation());
        bandOriginText.setText(mBandResult.getLocation());

        TextView bandGenreText = (TextView)view.findViewById(R.id.band_about_genre_textview);
        Log.i(TAG, "Setting bandGenreText to :" +  mBandResult.getGenre());
        bandGenreText.setText(mBandResult.getGenre());

        TextView bandInfoText = (TextView)view.findViewById(R.id.band_about_info_textview);
        Log.i(TAG, "Setting bandInfoText to :" +  mBandResult.getInfo());
        bandInfoText.setText(mBandResult.getInfo());





        Log.i(TAG,searchType + " = searchType");



        return view;
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
        BandAboutFragment.isInflated = isInflated;
    }


}



