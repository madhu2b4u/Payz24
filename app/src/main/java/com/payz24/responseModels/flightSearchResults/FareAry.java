package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/8/2018.
 */

public class FareAry {

    @SerializedName("Fare")
    @Expose
    private Fare fare;

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }
}
