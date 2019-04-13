package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/8/2018.
 */

public class BookingClassFare {

    @SerializedName("bookingclass")
    @Expose
    private String bookingclass;
    @SerializedName("classType")
    @Expose
    private String classType;
    @SerializedName("farebasiscode")
    @Expose
    private String farebasiscode;
    @SerializedName("Rule")
    @Expose
    private String rule;

    public String getBookingclass() {
        return bookingclass;
    }

    public void setBookingclass(String bookingclass) {
        this.bookingclass = bookingclass;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getFarebasiscode() {
        return farebasiscode;
    }

    public void setFarebasiscode(String farebasiscode) {
        this.farebasiscode = farebasiscode;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
