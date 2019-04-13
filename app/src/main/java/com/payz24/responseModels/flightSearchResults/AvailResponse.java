package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/8/2018.
 */

public class AvailResponse {
    @SerializedName("OriginDestinationOptions")
    @Expose
    private OriginDestinationOptions originDestinationOptions;

    public OriginDestinationOptions getOriginDestinationOptions() {
        return originDestinationOptions;
    }

    public void setOriginDestinationOptions(OriginDestinationOptions originDestinationOptions) {
        this.originDestinationOptions = originDestinationOptions;
    }
}
