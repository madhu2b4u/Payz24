package com.payz24.interfaces;

import com.payz24.responseModels.busStations.StationList;

/**
 * Created by mahesh on 1/24/2018.
 */

public interface BusStationsCallBack {
    void selectedBusStation(StationList stationList,String clickedOn);
}
