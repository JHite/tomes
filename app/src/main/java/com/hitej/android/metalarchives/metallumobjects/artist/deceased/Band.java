package com.hitej.android.metalarchives.metallumobjects.artist.deceased;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Band {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}