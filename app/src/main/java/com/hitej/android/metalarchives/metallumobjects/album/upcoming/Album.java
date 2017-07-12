package com.hitej.android.metalarchives.metallumobjects.album.upcoming;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("upcoming_albums")
    @Expose
    private List<UpcomingAlbum> upcomingAlbums = null;

    public List<UpcomingAlbum> getUpcomingAlbums() {
        return upcomingAlbums;
    }

    public void setUpcomingAlbums(List<UpcomingAlbum> upcomingAlbums) {
        this.upcomingAlbums = upcomingAlbums;
    }

}