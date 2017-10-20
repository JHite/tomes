package com.hitej.android.metalarchives;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitej.android.metalarchives.metallumobjects.album.albumid.Album;
import com.hitej.android.metalarchives.metallumobjects.album.albumid.Song;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Band;
import com.hitej.android.metalarchives.net.MetalArchivesAPI;

import java.io.IOException;
import java.io.Serializable;
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

/**
 * Created by jhite on 1/18/17.
 * Activity launched from BandInfoActivity - BandDiscogFragment that will display Album details
 */

public class DiscInfoFragment extends Fragment{



    private RecyclerView mDiscInfoRecyclerView;
    private static boolean isInflated = false;
    private SongAdapter mAdapter;
    private static final String TAG = "DiscInfoFragment";
    public static final String ARGS_DISC = "Disc Argument";
    private static String mDiscID;
    private static Album mAlbum;
    private static List<Song> mSongList;


    private static  DiscInfoFragment newInstance(){return new DiscInfoFragment();}

    public static DiscInfoFragment newInstance(String discID){
        DiscInfoFragment fragment = newInstance();
        Bundle args = new Bundle();
        args.putString(ARGS_DISC, discID);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_disc_info, container, false);
        mDiscInfoRecyclerView = (RecyclerView)
                view.findViewById(R.id.disc_info_recycler_view);
        mDiscInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setIsInflated(true);

        Log.i(TAG, getArguments().getString(ARGS_DISC) + " is ID query is being ran for");
        DiscInfoQuery query = new DiscInfoQuery(getArguments().getString(ARGS_DISC));
        query.start();

        updateUI();

        return view;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mDiscInfoRecyclerView.setAdapter(new SongAdapter(mSongList));
        }
    }

    private class SongHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Song mSong;
        private TextView mSongName;


        public SongHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mSongName = (TextView)itemView.findViewById(R.id.disc_info_song_name);

        }

        public void bindSong(Song song){
            mSong = song;
            mSongName.setText(song.getTitle());

        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, mSong.getTitle() + "'s TrackHolders clicked");
            // an extra to show BandAboutFragment
            //Intent intent = BandInfoActivity.newIntent(getContext(), mBand);
            //startActivity(intent);


        }
    }


    private class SongAdapter extends RecyclerView.Adapter<SongHolder> {


        public SongAdapter(List<Song> songItems) {
              mSongList = songItems;
        }


        @Override
        public SongHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_disc_info_track_item, viewGroup, false);
            return new DiscInfoFragment.SongHolder(view);
        }

        @Override
        public void onBindViewHolder(SongHolder songHolder, int position) {
            Song song = mSongList.get(position);
            songHolder.bindSong(song);

        }

        @Override
        public int getItemCount() {
            return mSongList.size();
        }

    }




    public static boolean isInflated() {
        return isInflated;
    }

    public static void setIsInflated(boolean isInflated) {
       DiscInfoFragment.isInflated = isInflated;
    }
    public void updateUI(){
        if(mAdapter == null){
            mAdapter = new SongAdapter(mSongList);
            mDiscInfoRecyclerView.setAdapter(mAdapter);
        } else{
            //may need more here
            mAdapter.notifyDataSetChanged();
        }
    }

    private class DiscInfoQuery {

        static final String BASE_URL = "http://em.wemakesites.net/";
        public static final String TAG = "DiscInfoQuery";
        private String queryText = "";
        private final String metalArchivesAPIKey = "f60b07b8-612e-4a3b-95f5-1df3250a72ac";
        private Album mAlbum;

        private DiscInfoQuery(String discID){
            queryText = discID;
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
            Observable<Album> album = api.getAlbum(queryText);

            album
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse,this::handleError);

        }


        private void handleResponse(Album albumResults){
            Log.i(TAG, "Handling response...");
            mAlbum = albumResults;
            DiscInfoActivity.mAlbum = albumResults;

            Log.i(TAG, "Album Page for Album id" + mAlbum.getData().getAlbum().getId() +
                    " loading");
            /*
            See if notifyDataChange() will handle reloading results when query finished.

            TextView bandInfoText = (TextView)getView().findViewById(R.id.band_about_info_textview);
            bandInfoText.setText(mBand.getData().getBio());
             */

            mSongList = mAlbum.getData().getAlbum().getSongs();
            mAdapter.notifyDataSetChanged();

        }

        private void handleError(Throwable e){
            Log.i (TAG, "ERROR = " + e.toString());
        }
    }



}






