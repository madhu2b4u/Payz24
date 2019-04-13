package com.payz24.responseModels.internationalOneWayRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/19/2018.
 */

public class InternationalOneWay {

    @SerializedName("searchData")
    @Expose
    private SearchData searchData;
    @SerializedName("postData")
    @Expose
    private PostData postData;
    @SerializedName("flightData")
    @Expose
    private FlightData flightData;

    public InternationalOneWay(SearchData searchData, PostData postData, FlightData flightData) {
        this.searchData = searchData;
        this.postData = postData;
        this.flightData = flightData;
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

}
