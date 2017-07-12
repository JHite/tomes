package com.hitej.android.metalarchives.metallumobjects.band.random;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentLineup {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("instrument")
    @Expose
    private String instrument;
    @SerializedName("years")
    @Expose
    private Object years;

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

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Object getYears() {
        return years;
    }

    public void setYears(Object years) {
        this.years = years;
    }

}