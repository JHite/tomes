package com.hitej.android.metalarchives;
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

import com.hitej.android.metalarchives.metallumobjects.album.albumid.Album;

public class DiscInfoActivity extends SingleFragmentActivity {

    private static final String TAG = "DiscInfoActivity";
    private Context mContext = this;
    protected static Album mAlbum;
    private static String mAlbumId;
    public static final String EXTRA_DISC_ID = "com.jhite.android.metalarchives.discinfo.discid";

    public static Intent newIntent(Context context, String discID) {
        Intent intent = new Intent(context, DiscInfoActivity.class);
        intent.putExtra(EXTRA_DISC_ID, discID);

        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        mAlbumId = getIntent().getStringExtra(EXTRA_DISC_ID);
        return new DiscInfoFragment().newInstance(mAlbumId);
    }

    protected void onSavedInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

