package com.hitej.android.metalarchives;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitej.android.metalarchives.net.MetalArchivesAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jhite on 6/5/16.
 *
 * Deprecating 7/25/17 in favor of building using
 * Retrofit
 */

public class SearchQuery {
    /*
    public static final String BASE_URL = "http://em.wemakesites.net/";

    public static final String TAG = "SearchQuery";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    //Search by album title

    //Search by artist alias

    //Search by band genre

    //Search by band name
    private List<Band> searchBandName(String bandName) {
        MetalArchivesAPI api = retrofit.create(MetalArchivesAPI.class);
        Call<List<Band>> band = api.searchBandName(bandName);
        band.enqueue(new Callback<Band>() {
            @Override
            public void onResponse(Call<Band> call, Response<List<Band>> response) {
                Log.i(TAG, response.body().toString());
                int statusCode = response.code();
                List<Band> bandResults = response.body();
            }

            @Override
            public void onFailure(Call<Band> call, Throwable t) {
                Log.i(TAG, "BandNameSearch FAILED!!");
            }
        });




    }
    //Search by band themes

    //Search by song title
    */

}
