package com.hitej.android.metalarchives.metallumobjects.album.albumid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Band {

    @SerializedName("band_name")
    @Expose
    private String bandName;
    @SerializedName("id")
    @Expose
    private String id;

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}