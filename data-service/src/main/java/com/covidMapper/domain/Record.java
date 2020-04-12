
package com.covidMapper.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("s_no")
    @Expose
    private String sNo;
    @SerializedName("name_of_state_ut")
    @Expose
    private String nameOfStateUt;
    @SerializedName("total_confirmed_cases")
    @Expose
    private String totalConfirmedCases;
    @SerializedName("cured_discharged_migrated")
    @Expose
    private String curedDischargedMigrated;
    @SerializedName("death")
    @Expose
    private String death;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getSNo() {
        return sNo;
    }

    public void setSNo(String sNo) {
        this.sNo = sNo;
    }

    public String getNameOfStateUt() {
        return nameOfStateUt;
    }

    public void setNameOfStateUt(String nameOfStateUt) {
        this.nameOfStateUt = nameOfStateUt;
    }

    public String getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public void setTotalConfirmedCases(String totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public String getCuredDischargedMigrated() {
        return curedDischargedMigrated;
    }

    public void setCuredDischargedMigrated(String curedDischargedMigrated) {
        this.curedDischargedMigrated = curedDischargedMigrated;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
