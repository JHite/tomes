package com.hitej.android.metalarchives.metallumobjects.artist.deceased;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deceased {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("cause_of_death")
    @Expose
    private String causeOfDeath;
    @SerializedName("date_of_death")
    @Expose
    private String dateOfDeath;
    @SerializedName("band")
    @Expose
    private Band band;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

}