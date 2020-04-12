package com.covidMapper.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExtState {
    @SerializedName("state")
    @Expose
    private String name;
    @SerializedName("districtData")
    @Expose
    private List<ExtDistrict> districts;

    public ExtState() {
    }

    public ExtState(String name, List<ExtDistrict> districts) {
        this.name = name;
        this.districts = districts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExtDistrict> getDistricts() {
        return districts;
    }

    public void setDistricts(List<ExtDistrict> districts) {
        this.districts = districts;
    }
}
