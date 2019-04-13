package com.payz24.responseModels.flightSearchResults;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 2/8/2018.
 */

public class OriginDestinationOption {

    @SerializedName("FareDetails")
    @Expose
    private FareDetails fareDetails;
    @SerializedName("onward")
    @Expose
    private Onward onward;
    @SerializedName("Return")
    @Expose
    private Object returnJsonObject;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("key")
    @Expose
    private String key;

    public FareDetails getFareDetails() {
        return fareDetails;
    }

    public void setFareDetails(FareDetails fareDetails) {
        this.fareDetails = fareDetails;
    }

    public Onward getOnward() {
        return onward;
    }

    public void setOnward(Onward onward) {
        this.onward = onward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getReturnJsonObject() {
        return returnJsonObject;
    }

    public void setReturnJsonObject(Object returnJsonObject) {
        this.returnJsonObject = returnJsonObject;
    }
}
