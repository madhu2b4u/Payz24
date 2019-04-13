package com.payz24.responseModels.domesticPreFare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/19/2018.
 */

public class SearchData {

    @SerializedName("adult")
    @Expose
    private String adult;
    @SerializedName("child")
    @Expose
    private String child;
    @SerializedName("infant")
    @Expose
    private String infant;
    @SerializedName("Journey_type")
    @Expose
    private String journeyType;

    public SearchData(int adultCount, int childrenCount, int infantCount, String journeyType) {
        this.adult = String.valueOf(adultCount);
        this.child = String.valueOf(childrenCount);
        this.infant = String.valueOf(infantCount);
        this.journeyType = journeyType;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getInfant() {
        return infant;
    }

    public void setInfant(String infant) {
        this.infant = infant;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

}
