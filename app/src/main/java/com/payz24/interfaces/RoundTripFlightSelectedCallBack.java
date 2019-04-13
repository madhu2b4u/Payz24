package com.payz24.interfaces;


import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption;

/**
 * Created by mahesh on 2/13/2018.
 */

public interface RoundTripFlightSelectedCallBack {
    void roundTripFlightSelected(OriginDestinationOption originDestinationOption, String from);
}
