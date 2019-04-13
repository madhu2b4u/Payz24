package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 2/8/2018.
 */

public class OriginDestinationOptions {

    @SerializedName("OriginDestinationOption")
    @Expose
    private List<OriginDestinationOption> originDestinationOption = null;

    public List<OriginDestinationOption> getOriginDestinationOption() {
        return originDestinationOption;
    }

    public void setOriginDestinationOption(List<OriginDestinationOption> originDestinationOption) {
        this.originDestinationOption = originDestinationOption;
    }
}
