package com.covidMapper.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;

import java.util.Map;

public class StatsRow {
    @Id
    @SerializedName("id")
    @Expose
    private String id;
    @Id
    @SerializedName("key")
    @Expose
    private String key;
    @Id
    @SerializedName("value")
    @Expose
    private Map<String,String> properties;

    public StatsRow() {
    }

    public StatsRow(String id, String key, Map<String, String> properties) {
        this.id = id;
        this.key = key;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
