package com.covidMapper.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Stats {
    @Id
    @SerializedName("total_rows")
    @Expose
    private String rowCount;
    @Id
    @SerializedName("offset")
    @Expose
    private String offset;
    @Id
    @SerializedName("rows")
    @Expose
    private List<StatsRow> rows;

    public Stats() {
    }

    public Stats(String rowCount, String offset, List<StatsRow> rows) {
        this.rowCount = rowCount;
        this.offset = offset;
        this.rows = rows;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public List<StatsRow> getRows() {
        return rows;
    }

    public void setRows(List<StatsRow> rows) {
        this.rows = rows;
    }
}
