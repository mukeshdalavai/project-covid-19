
package com.covidMapper.domain;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GovStats {

    @SerializedName("index_name")
    @Expose
    private String indexName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("updated")
    @Expose
    private Integer updated;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("visualizable")
    @Expose
    private String visualizable;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("field")
    @Expose
    private Object field = null;
    @SerializedName("external_ws")
    @Expose
    private Integer externalWs;
    @SerializedName("external_ws_url")
    @Expose
    private String externalWsUrl;
    @SerializedName("target_bucket")
    @Expose
    private Object targetBucket;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("limit")
    @Expose
    private String limit;
    @SerializedName("offset")
    @Expose
    private String offset;
    @SerializedName("records")
    @Expose
    private List<Record> records;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getVisualizable() {
        return visualizable;
    }

    public void setVisualizable(String visualizable) {
        this.visualizable = visualizable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getField() {
        return field;
    }

    public void setField(Object field) {
        this.field = field;
    }

    public Integer getExternalWs() {
        return externalWs;
    }

    public void setExternalWs(Integer externalWs) {
        this.externalWs = externalWs;
    }

    public String getExternalWsUrl() {
        return externalWsUrl;
    }

    public void setExternalWsUrl(String externalWsUrl) {
        this.externalWsUrl = externalWsUrl;
    }

    public Object getTargetBucket() {
        return targetBucket;
    }

    public void setTargetBucket(Object targetBucket) {
        this.targetBucket = targetBucket;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
