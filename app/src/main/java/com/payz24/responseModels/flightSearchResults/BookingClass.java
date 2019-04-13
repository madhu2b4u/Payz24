package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/8/2018.
 */

public class BookingClass {

    @SerializedName("Availability")
    @Expose
    private String availability;
    @SerializedName("BIC")
    @Expose
    private String bIC;

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBIC() {
        return bIC;
    }

    public void setBIC(String bIC) {
        this.bIC = bIC;
    }

}
