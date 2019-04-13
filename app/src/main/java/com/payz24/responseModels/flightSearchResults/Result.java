package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/7/2018.
 */

public class Result {

    @SerializedName("AvailRequest")
    @Expose
    private AvailRequest availRequest;
    @SerializedName("AvailResponse")
    @Expose
    private AvailResponse availResponse;

    public AvailRequest getAvailRequest() {
        return availRequest;
    }

    public void setAvailRequest(AvailRequest availRequest) {
        this.availRequest = availRequest;
    }

    public AvailResponse getAvailResponse() {
        return availResponse;
    }

    public void setAvailResponse(AvailResponse availResponse) {
        this.availResponse = availResponse;
    }
}
