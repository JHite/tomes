package com.hitej.android.metalarchives.metallumobjects.album.upcoming;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingAlbum {

    @SerializedName("band")
    @Expose
    private Band band;
    @SerializedName("album")
    @Expose
    private Album_ album;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}