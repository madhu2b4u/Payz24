package com.payz24.responseModels.flightSearchResults;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by mahesh on 2/8/2018.
 */

public class Onward {
    @SerializedName("FlightSegments")
    @Expose
    public JsonObject flightSegments;
}
