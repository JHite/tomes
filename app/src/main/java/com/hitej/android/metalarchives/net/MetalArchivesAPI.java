package com.hitej.android.metalarchives.net;

import com.hitej.android.metalarchives.metallumobjects.album.albumid.Album;
import com.hitej.android.metalarchives.metallumobjects.album.upcoming.UpcomingAlbum;
import com.hitej.android.metalarchives.metallumobjects.band.bandid.Band;
import com.hitej.android.metalarchives.metallumobjects.search.bandname.SearchResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jhite on 7/20/17.
 */

public interface MetalArchivesAPI {

    @GET("search/band_name/{keyword}")
    Observable<com.hitej.android.metalarchives.metallumobjects.search.bandname.BandName> searchBandName(@Path("keyword") String keyword);

    @GET("albums/upcoming")
    Observable<List<UpcomingAlbum>> getUpcomingAlbums();

    @GET("band/{band_id}")
    Observable<Band> getBand(@Path("band_id") String band_id);

    //@GET



}
