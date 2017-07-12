
package com.hitej.android.metalarchives.metallumobjects.search.songtitle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("band")
    @Expose
    private Band band;
    @SerializedName("song")
    @Expose
    private Song_ song;

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Song_ getSong() {
        return song;
    }

    public void setSong(Song_ song) {
        this.song = song;
    }

}