package com.payz24.responseModels.blockTicket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 1/31/2018.
 */

public class BoardingPoint {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("time")
    @Expose
    private String time;

    public BoardingPoint(String boardingPointId, String boardingPointLocation, String boardingTime) {
        this.id = boardingPointId;
        this.location = boardingPointLocation;
        this.time = boardingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
