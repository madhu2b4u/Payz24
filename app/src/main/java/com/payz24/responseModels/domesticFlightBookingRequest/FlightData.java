package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/21/2018.
 */

public class FlightData {

    @SerializedName("tot_first_price")
    @Expose
    private Integer totFirstPrice;
    @SerializedName("tot_onward_price")
    @Expose
    private Integer totOnwardPrice;
    @SerializedName("tot_return_price")
    @Expose
    private Integer totReturnPrice;
    @SerializedName("onward")
    @Expose
    private Onward onward;
    @SerializedName("tot_sec_onward_price")
    @Expose
    private Integer totSecOnwardPrice;
    @SerializedName("tot_sec_return_price")
    @Expose
    private Integer totSecReturnPrice;
    @SerializedName("return")
    @Expose
    private Return returnTrip;

    public FlightData(int totFirstPrice, int totalOnwardPrice, int totalReturnPrice, Onward onward, int totSecOnwardPrice, int totSecReturnPrice, Return returnTrip) {
        if (totFirstPrice != -1)
            this.totFirstPrice = totFirstPrice;
        if (totalOnwardPrice != -1)
            this.totOnwardPrice = totalOnwardPrice;
        if (totalReturnPrice != -1)
            this.totReturnPrice = totalReturnPrice;
        this.onward = onward;
        if (totSecOnwardPrice != -1)
            this.totSecOnwardPrice = totSecOnwardPrice;
        if (totSecReturnPrice != -1)
            this.totSecReturnPrice = totSecReturnPrice;
        if (returnTrip != null)
            this.returnTrip = returnTrip;
    }


    public Integer getTotFirstPrice() {
        return totFirstPrice;
    }

    public void setTotFirstPrice(Integer totFirstPrice) {
        this.totFirstPrice = totFirstPrice;
    }

    public Integer getTotOnwardPrice() {
        return totOnwardPrice;
    }

    public void setTotOnwardPrice(Integer totOnwardPrice) {
        this.totOnwardPrice = totOnwardPrice;
    }

    public Integer getTotReturnPrice() {
        return totReturnPrice;
    }

    public void setTotReturnPrice(Integer totReturnPrice) {
        this.totReturnPrice = totReturnPrice;
    }

    public Onward getOnward() {
        return onward;
    }

    public void setOnward(Onward onward) {
        this.onward = onward;
    }

    public Integer getTotSecOnwardPrice() {
        return totSecOnwardPrice;
    }

    public void setTotSecOnwardPrice(Integer totSecOnwardPrice) {
        this.totSecOnwardPrice = totSecOnwardPrice;
    }

    public Integer getTotSecReturnPrice() {
        return totSecReturnPrice;
    }

    public void setTotSecReturnPrice(Integer totSecReturnPrice) {
        this.totSecReturnPrice = totSecReturnPrice;
    }

    public Return getReturn() {
        return returnTrip;
    }

    public void setReturn(Return returnTrip) {
        this.returnTrip = returnTrip;
    }
}
