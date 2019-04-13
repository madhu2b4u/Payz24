package com.payz24.interfaces;

import com.payz24.responseModels.flightStations.FlightStations;
import com.payz24.responseModels.flightStations.Station;

/**
 * Created by mahesh on 2/4/2018.
 */

public interface SelectedFlightsStationsCallBack {
    void selectedFlightStations(Station flightStations, String from);
}
