package com.hitej.android.metalarchives.metallumobjects.artist.deceased;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {

    @SerializedName("deceased")
    @Expose
    private List<Deceased> deceased = null;

    public List<Deceased> getDeceased() {
        return deceased;
    }

    public void setDeceased(List<Deceased> deceased) {
        this.deceased = deceased;
    }

}