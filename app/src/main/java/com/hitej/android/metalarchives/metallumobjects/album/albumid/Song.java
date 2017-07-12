package com.hitej.android.metalarchives.metallumobjects.album.albumid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("length")
    @Expose
    private String length;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

}