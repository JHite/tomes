package com.hitej.android.metalarchives.metallumobjects.album.upcoming;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album_ {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("id")
    @Expose
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}