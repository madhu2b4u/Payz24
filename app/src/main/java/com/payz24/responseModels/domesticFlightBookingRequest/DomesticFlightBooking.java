package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/21/2018.
 */

public class DomesticFlightBooking {

    @SerializedName("searchData")
    @Expose
    private SearchData searchData;
    @SerializedName("postData")
    @Expose
    private PostData postData;
    @SerializedName("flightData")
    @Expose
    private FlightData flightData;
    @SerializedName("opricing")
    @Expose
    private Opricing opricing;
    @SerializedName("rpricing")
    @Expose
    private Rpricing rpricing;

    public DomesticFlightBooking(SearchData searchData, PostData postData, FlightData flightData, Opricing opricing, Rpricing rpricing) {
        this.searchData = searchData;
        this.postData = postData;
        this.flightData = flightData;
        this.opricing = opricing;
        if (rpricing != null)
            this.rpricing = rpricing;
    }

    public SearchData getSearchData() {
        return searchData;
    }

    public void setSearchData(SearchData searchData) {
        this.searchData = searchData;
    }

    public PostData getPostData() {
        return postData;
    }

    public void setPostData(PostData postData) {
        this.postData = postData;
    }

    public FlightData getFlightData() {
        return flightData;
    }

    public void setFlightData(FlightData flightData) {
        this.flightData = flightData;
    }

    public Opricing getOpricing() {
        return opricing;
    }

    public void setOpricing(Opricing opricing) {
        this.opricing = opricing;
    }

    public Rpricing getRpricing() {
        return rpricing;
    }

    public void setRpricing(Rpricing rpricing) {
        this.rpricing = rpricing;
    }
}
