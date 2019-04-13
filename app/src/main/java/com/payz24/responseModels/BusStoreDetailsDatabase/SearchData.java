package com.payz24.responseModels.BusStoreDetailsDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/2/2018.
 */

public class SearchData {

    @SerializedName("jType")
    @Expose
    private String jType;
    @SerializedName("fromCity")
    @Expose
    private String fromCity;
    @SerializedName("toCity")
    @Expose
    private String toCity;
    @SerializedName("from_code")
    @Expose
    private String fromCode;
    @SerializedName("to_code")
    @Expose
    private String toCode;
    @SerializedName("cin")
    @Expose
    private String cin;
    @SerializedName("ed")
    @Expose
    private String ed;

    public SearchData(String jType, String fromCity, String toCity, String fromCode, String toCode, String cin, String ed) {
        this.jType = jType;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.cin = cin;
        this.ed = ed;
    }

    public String getJType() {
        return jType;
    }

    public void setJType(String jType) {
        this.jType = jType;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }
}
