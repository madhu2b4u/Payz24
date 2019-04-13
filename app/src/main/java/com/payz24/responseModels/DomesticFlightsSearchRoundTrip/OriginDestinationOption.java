
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OriginDestinationOption {

    @SerializedName("FareDetails")
    @Expose
    private FareDetails fareDetails;
    @SerializedName("FlightSegments")
    @Expose
    private JsonObject flightSegments;
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

    public JsonObject getFlightSegments() {
        return flightSegments;
    }

    public void setFlightSegments(JsonObject flightSegments) {
        this.flightSegments = flightSegments;
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

}
