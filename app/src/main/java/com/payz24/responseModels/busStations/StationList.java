package com.payz24.responseModels.busStations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 1/24/2018.
 */

public class StationList {

    @SerializedName("stationName")
    @Expose
    private String stationName;
    @SerializedName("stationId")
    @Expose
    private Integer stationId;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }
}
