package com.hitej.android.metalarchives.metallumobjects.search.albumtitle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("band")
    @Expose
    private Band_ band;
    @SerializedName("album")
    @Expose
    private Album album;

    public Band_ getBand() {
        return band;
    }

    public void setBand(Band_ band) {
        this.band = band;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

}