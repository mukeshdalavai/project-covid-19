//package com.stackroute.restaurant.restaurantlogserver.domain;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//import java.util.Date;
//import java.util.List;
//
//@Document(collection="restaurantLogs")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class RestaurantLogs {
//    @Id
//    int restaurantlogid;
//    String username;
//    String rating;
//    private List<RestaurantLogs> restaurantLogs;
//}
//
//
package com.covidMapper.domain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class HotSpot {
    @Id
    @SerializedName("_cn6ca")
    @Expose
    int id;
    @SerializedName("accuracyLocation")
    @Expose
    String accuracyLocation;
    @SerializedName("address")
    @Expose
    String description;
    @SerializedName("datasource")
    @Expose
    String dataSource;
    @SerializedName("latlong")
    @Expose
    String latLong;
    @SerializedName("latitude")
    @Expose
    Double latitude;
    @SerializedName("longitude")
    @Expose
    Double longitude;
    @SerializedName("modeoftravel")
    @Expose
    String modeOfTravel;
    @SerializedName("pid")
    @Expose
    String patientID;
    @SerializedName("placename")
    @Expose
    String placeName;
    @SerializedName("timefrom")
    @Expose
    String startTime;
    @SerializedName("timeto")
    @Expose
    String endTime;
    @SerializedName("type")
    @Expose
    String type;

    public HotSpot() {
    }

    public HotSpot(int id, String accuracyLocation, String description, String dataSource, String latLong, Double latitude, Double longitude, String modeOfTravel, String patientID, String placeName, String startTime, String endTime, String type) {
        this.id = id;
        this.accuracyLocation = accuracyLocation;
        this.description = description;
        this.dataSource = dataSource;
        this.latLong = latLong;
        this.latitude = latitude;
        this.longitude = longitude;
        this.modeOfTravel = modeOfTravel;
        this.patientID = patientID;
        this.placeName = placeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccuracyLocation() {
        return accuracyLocation;
    }

    public void setAccuracyLocation(String accuracyLocation) {
        this.accuracyLocation = accuracyLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getModeOfTravel() {
        return modeOfTravel;
    }

    public void setModeOfTravel(String modeOfTravel) {
        this.modeOfTravel = modeOfTravel;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

