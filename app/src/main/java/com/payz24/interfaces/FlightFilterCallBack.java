package com.payz24.interfaces;

import java.util.LinkedList;

/**
 * Created by mahesh on 2/25/2018.
 */

public interface FlightFilterCallBack {
    void flightFilter(boolean isNonStopSelected,
                      boolean isOneStopSelected,
                      boolean isTwoStopSelected,
                      boolean isBeforeElevenAM,
                      boolean isElevenAM,
                      boolean isFivePM,
                      boolean isAfterNinePM,
                      LinkedList<String> listOfCheckNames);
}
