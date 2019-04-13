package com.payz24.responseModels.CancelBusTicketRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 3/29/2018.
 */

public class CancelBusTicketRequest {

    @SerializedName("etsTicketNo")
    @Expose
    private String etsTicketNo;
    @SerializedName("seatNbrsToCancel")
    @Expose
    private List<String> seatNbrsToCancel = null;

    public CancelBusTicketRequest(String etsTicketNo, LinkedList<String> listOfSeats) {
        this.etsTicketNo = etsTicketNo;
        this.seatNbrsToCancel = listOfSeats;
    }

    public String getEtsTicketNo() {
        return etsTicketNo;
    }

    public void setEtsTicketNo(String etsTicketNo) {
        this.etsTicketNo = etsTicketNo;
    }

    public List<String> getSeatNbrsToCancel() {
        return seatNbrsToCancel;
    }

    public void setSeatNbrsToCancel(List<String> seatNbrsToCancel) {
        this.seatNbrsToCancel = seatNbrsToCancel;
    }
}
