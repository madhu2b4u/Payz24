package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/21/2018.
 */

public class Opricing {

    @SerializedName("fare")
    @Expose
    private Fare fare;

    public Opricing(Fare fare) {
        this.fare = fare;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }
}
