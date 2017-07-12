package com.hitej.android.metalarchives.metallumobjects.album.albumid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("band")
    @Expose
    private Band band;
    @SerializedName("album")
    @Expose
    private Album_ album;

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Album_ getAlbum() {
        return album;
    }

    public void setAlbum(Album_ album) {
        this.album = album;
    }

}