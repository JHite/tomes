package com.hitej.android.metalarchives.metallumobjects.artist.artistid;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bands {

    @SerializedName("active")
    @Expose
    private List<Active> active = null;
    @SerializedName("past")
    @Expose
    private List<Past> past = null;
    @SerializedName("guest")
    @Expose
    private List<Guest> guest = null;

    public List<Active> getActive() {
        return active;
    }

    public void setActive(List<Active> active) {
        this.active = active;
    }

    public List<Past> getPast() {
        return past;
    }

    public void setPast(List<Past> past) {
        this.past = past;
    }

    public List<Guest> getGuest() {
        return guest;
    }

    public void setGuest(List<Guest> guest) {
        this.guest = guest;
    }

}