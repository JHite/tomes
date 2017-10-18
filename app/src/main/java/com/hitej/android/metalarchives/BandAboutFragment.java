package com.hitej.android.metalarchives;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Band;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.CurrentLineup;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Details;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Discography;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.BandName;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;
import com.hitej.android.metalarchives.net.MetalArchivesAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.github.loki.afro.metallum.entity.Band;



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
    public static final String ARGS_BAND = "Band Argument";

    private SearchResult mBandResult;
    private Details mBandDetails;
    private ArrayList<Discography> mBandDiscography;
    private List<CurrentLineup> mCurrentLineup;
    private static String mBandID = "";
    private BandInfoQuery query;



    public static BandAboutFragment newInstance(){
        return new BandAboutFragment();
    }

    public static BandAboutFragment newInstance(String bandID){
        BandAboutFragment fragment = newInstance();
        Bundle args = new Bundle();
        args.putString(ARGS_BAND, bandID);
        fragment.setArguments(args);

        Log.i(TAG, bandID + " placed into Bundle for BandAboutFragment");
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mBandID = getArguments().getString(ARGS_BAND);
        Log.i(TAG, mBandID + " = mBandID in OnCreate[BandAboutFragment]");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_band_about, container, false);

        //setInflated to true
        setIsInflated(true);

        TextView bandInfoText = (TextView)view.findViewById(R.id.band_about_info_textview);
        //bandInfoText.setText(mBandDetails.toString());

        query =  new BandInfoQuery(mBandID);
        query.start();

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

    /*Query's "/band/id" endpoint
       Information such as band details, discog, current lineup obtained here
    */
    private class BandInfoQuery {

        static final String BASE_URL = "http://em.wemakesites.net/";
        public static final String TAG = "BandInfoQuery";
        private String queryText = "";
        private final String metalArchivesAPIKey = "f60b07b8-612e-4a3b-95f5-1df3250a72ac";
        private Band mBand;

        private BandInfoQuery(String bandID){
            queryText = bandID;
        }

        private void start() {
            //Log.i(TAG, Resources.getSystem().getString(R.string.metalArchivesAPIKey));
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            RxJava2CallAdapterFactory rxAdapter
                    = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

            // Can be Level.BASIC, Level.HEADERS, or Level.BODY
            // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //TODO: add log to okhttpclient
            //intercepter code
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    HttpUrl originalHttpUrl = originalRequest.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_key", metalArchivesAPIKey)
                            .build();

                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            }).addInterceptor(httpLoggingInterceptor)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(rxAdapter)
                    .build();

            MetalArchivesAPI api = retrofit.create(MetalArchivesAPI.class);
            Observable<Band> band = api.getBand(queryText);

            band
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse,this::handleError);

        }


        private void handleResponse(Band bandResults){
            Log.i(TAG, "Handling response...");
            mBand = bandResults;
            Log.i(TAG, "About Page for Band id" + mBand.getData().getId() +
                "loading");
            TextView bandInfoText = (TextView)getView().findViewById(R.id.band_about_info_textview);
            bandInfoText.setText(mBand.getData().getBio());

            /*
            mBandDetails = bandResults.getDetails();
            mBandDiscography = new ArrayList<>(bandResults.getDiscography());
            List<CurrentLineup> mCurrentLineup = bandResults.getCurrentLineup();
            Log.i(TAG, mBand.getBandName() + "'s Bio  = " + mBand.getBio());



            */


        }

        private void handleError(Throwable e){
            Log.i (TAG, "ERROR = " + e.toString());
        }
    }


}



