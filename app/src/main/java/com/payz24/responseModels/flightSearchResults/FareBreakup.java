package com.payz24.responseModels.flightSearchResults;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/8/2018.
 */

public class FareBreakup {

    @SerializedName("FareAry")
    @Expose
    private JsonObject fareAry;

    public JsonObject getFareAry() {
        return fareAry;
    }

    public void setFareAry(JsonObject fareAry) {
        this.fareAry = fareAry;
    }
}
