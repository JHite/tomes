package com.hitej.android.metalarchives.metallumobjects.search.songtitle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song_ {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("album_title")
    @Expose
    private String albumTitle;
    @SerializedName("id")
    @Expose
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}