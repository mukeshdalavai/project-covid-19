package com.covidMapper.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class HotSpotWrapper {
    @SerializedName("travel_history")
    @Expose
    private List<HotSpot> hotSpots;

    public HotSpotWrapper() {
    }

    public HotSpotWrapper(List<HotSpot> hotSpots) {
        this.hotSpots = hotSpots;
    }

    public List<HotSpot> getHotSpots() {
        return hotSpots;
    }

    public void setHotSpots(List<HotSpot> hotSpots) {
        this.hotSpots = hotSpots;
    }
}
