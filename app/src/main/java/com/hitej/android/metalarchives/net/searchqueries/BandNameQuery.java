package com.hitej.android.metalarchives.net.searchqueries;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitej.android.metalarchives.BandSearchResultsFragment;
import com.hitej.android.metalarchives.MASearchActivity;
import com.hitej.android.metalarchives.R;
import com.hitej.android.metalarchives.adapters.BandSearchResultsAdapter;

import com.hitej.android.metalarchives.metallumobjects.search.bandname.BandName;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;

import com.hitej.android.metalarchives.net.MetalArchivesAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jhite on 7/25/17.
 */

public class BandNameQuery {

    static final String BASE_URL = "http://em.wemakesites.net/";
    public static final String TAG = "BandNameQuery";
    private String queryText = "";
    private ArrayList mBandSearchResultsList;
    private BandSearchResultsAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private final String metalArchivesAPIKey = "f60b07b8-612e-4a3b-95f5-1df3250a72ac";

    public BandNameQuery(String bandName, Context context){
        queryText = bandName;
        mContext = context;
    }

    public void start() {
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
        Observable<BandName> band = api.searchBandName(queryText);

        band
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse,this::handleError);

    }


    private void handleResponse(BandName bandResults){
        mBandSearchResultsList = new ArrayList(bandResults.getData().getSearchResults());
        Log.i(TAG, "result list size = " + mBandSearchResultsList.size());

        mAdapter = new BandSearchResultsAdapter(mBandSearchResultsList);
        mRecyclerView = new RecyclerView(mContext);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }

    private void handleError(Throwable e){
        Log.i (TAG, "ERROR = " + e.toString());
    }
}
