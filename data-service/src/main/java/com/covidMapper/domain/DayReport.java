package com.covidMapper.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document
@Data
public class DayReport {
    private LocalDate date;
    private int confirmed;
    private int active;
    private int recovered;
    private int deaths;

    public DayReport() {
    }

    public DayReport(LocalDate date, int confirmed, int active, int recovered, int deaths) {
        this.date = date;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.deaths = deaths;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
}
