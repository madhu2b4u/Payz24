package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/3/2018.
 */

public class Rpricing {

    @SerializedName("fare")
    @Expose
    private Fare fare;

    public Rpricing(Fare fromFare) {
        this.fare = fromFare;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }
}
