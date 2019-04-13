
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightSegments {

    @SerializedName("FlightSegment")
    @Expose
    private List<FlightSegment> flightSegment = null;

    public List<FlightSegment> getFlightSegment() {
        return flightSegment;
    }

    public void setFlightSegment(List<FlightSegment> flightSegment) {
        this.flightSegment = flightSegment;
    }

}
