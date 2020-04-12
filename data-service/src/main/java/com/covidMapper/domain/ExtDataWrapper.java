package com.covidMapper.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ExtDataWrapper {
    @SerializedName("states")
    @Expose
    private List<ExtState> states;

    public ExtDataWrapper() {
    }

    public ExtDataWrapper(List<ExtState> states) {
        this.states = states;
    }

    public List<ExtState> getStates() {
        return states;
    }

    public void setStates(List<ExtState> states) {
        this.states = states;
    }
}
