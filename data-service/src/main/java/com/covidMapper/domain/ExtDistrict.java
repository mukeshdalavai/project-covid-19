package com.covidMapper.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtDistrict {
    @SerializedName("district")
    @Expose
    private String name;
    @SerializedName("confirmed")
    @Expose
    private int confirmed;

    public ExtDistrict() {
    }

    public ExtDistrict(String name, int confirmed) {
        this.name = name;
        this.confirmed = confirmed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
}
