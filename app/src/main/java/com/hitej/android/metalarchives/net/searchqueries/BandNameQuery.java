package com.hitej.android.metalarchives.net.searchqueries;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitej.android.metalarchives.R;
import com.hitej.android.metalarchives.adapters.BandSearchResultsAdapter;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.Band;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResultTest;
import com.hitej.android.metalarchives.net.MetalArchivesAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
    private RecyclerView mRecyclerView;
    private final String metalArchivesAPIKey = "f60b07b8-612e-4a3b-95f5-1df3250a72ac";

    public BandNameQuery(String bandName){
        queryText = bandName;
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

                Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        metalArchivesAPIKey);

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
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

        Observable<List<SearchResultTest>> band = api.searchBandName(queryText);
        // 8/20 commenting out to test suscribing off of the observable
        //Disposable disposable = band
        band
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse,this::handleError);
                /*.subscribeWith(new Disposable<SearchResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException) e;
                            int code = response.code();
                            Log.i(TAG, "error - " + response.toString() + code);
                        }
                    }

                    @Override
                    public void onNext(SearchResult result) {
                    }
                });*/

    }
    private void handleResponse(List<SearchResultTest> list){
        mBandSearchResultsList = new ArrayList<Band>(list.getSearchResults());
        Log.i(TAG, "result list size = " + mBandSearchResultsList.size());
        mAdapter = new BandSearchResultsAdapter(mBandSearchResultsList);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void handleError(Throwable e){
        Log.i (TAG, "ERROR = " + e.toString());
    }
}
