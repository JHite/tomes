package com.hitej.android.metalarchives;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jhite on 5/16/16.
 */
public class RandomBandFragment extends Fragment {

    private static boolean isInflated;

    public static RandomBandFragment newInstance(){
        return new RandomBandFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_randomband,container, false);
        //setInflated to true
        setIsInflated(true);
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        setIsInflated(false);
    }

    public static boolean isInflated() {
        return isInflated;
    }

    public static void setIsInflated(boolean isInflated) {
        RandomBandFragment.isInflated = isInflated;
    }
}
