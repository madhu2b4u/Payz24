package com.payz24.responseModels.availableBuses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 1/26/2018.
 */

public class AvailableBuses {

    @SerializedName("apiStatus")
    @Expose
    private ApiStatus apiStatus;
    @SerializedName("apiAvailableBuses")
    @Expose
    private List<ApiAvailableBus> apiAvailableBuses = null;

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }

    public List<ApiAvailableBus> getApiAvailableBuses() {
        return apiAvailableBuses;
    }

    public void setApiAvailableBuses(List<ApiAvailableBus> apiAvailableBuses) {
        this.apiAvailableBuses = apiAvailableBuses;
    }
}
