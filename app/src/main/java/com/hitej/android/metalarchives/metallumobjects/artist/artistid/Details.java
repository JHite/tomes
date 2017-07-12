package com.hitej.android.metalarchives.metallumobjects.artist.artistid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("bands")
    @Expose
    private Bands bands;
    @SerializedName("real/full name")
    @Expose
    private String realFullName;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("place of origin")
    @Expose
    private String placeOfOrigin;
    @SerializedName("gender")
    @Expose
    private String gender;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Bands getBands() {
        return bands;
    }

    public void setBands(Bands bands) {
        this.bands = bands;
    }

    public String getRealFullName() {
        return realFullName;
    }

    public void setRealFullName(String realFullName) {
        this.realFullName = realFullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}