package com.hitej.android.metalarchives;
//BottomBar from https://github.com/roughike/BottomBar

/*
    Created on 2/16/17
    */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;




public class DiscInfoActivity extends SingleFragmentActivity {

    private static final String TAG = "MetalArchivesActivity";
    private Context mContext = this;
    //private static Disc mDisc;

    //holder for DiscInfoFragment
    //protected static Disc mDiscCursor;



    public static Intent newIntent(Context context) {
        //mDisc = disc;
        return new Intent(context, DiscInfoActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return new DiscInfoFragment().newInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    protected void onSavedInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


}

