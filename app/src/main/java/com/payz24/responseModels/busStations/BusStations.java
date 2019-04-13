package com.payz24.responseModels.busStations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 1/24/2018.
 */

public class BusStations {
    @SerializedName("apiStatus")
    @Expose
    private ApiStatus apiStatus;
    @SerializedName("stationList")
    @Expose
    private List<StationList> stationList = null;

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }

    public List<StationList> getStationList() {
        return stationList;
    }

    public void setStationList(List<StationList> stationList) {
        this.stationList = stationList;
    }
}
