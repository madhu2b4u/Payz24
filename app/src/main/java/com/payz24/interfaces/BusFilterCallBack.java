package com.payz24.interfaces;

import java.util.LinkedList;

/**
 * Created by mahesh on 2/18/2018.
 */

public interface BusFilterCallBack {
    void busFilter(LinkedList<String> listOfSelectedOperatorsNames,boolean isAcSelected,boolean isNonAcSelected,boolean isSeaterSelected,boolean isSleeperSelected,boolean isSixAMSelected,boolean isTwelvePMSelected,
                   boolean isSixPMSelected,boolean isTwelveAMSelected);
}
