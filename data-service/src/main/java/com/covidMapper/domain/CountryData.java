package com.covidMapper.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class CountryData {
    @Id
    private String name;
    private int confirmed;
    private int active;
    private int recovered;
    private int deaths;
    private List<StateData> states;

    public CountryData() {
    }

    public CountryData(String name, int confirmed, int active, int recovered, int deaths, List<StateData> states) {
        this.name = name;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.deaths = deaths;
        this.states = states;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public List<StateData> getStates() {
        return states;
    }

    public void setStates(List<StateData> states) {
        this.states = states;
    }
}
