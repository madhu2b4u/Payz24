package com.payz24.responseModels.blockTicket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 1/31/2018.
 */

public class BlockSeatPaxDetail {

    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("seatNbr")
    @Expose
    private String seatNbr;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("fare")
    @Expose
    private Float fare;
    @SerializedName("serviceTaxAmount")
    @Expose
    private Double serviceTaxAmount;
    @SerializedName("operatorServiceChargeAbsolute")
    @Expose
    private Double operatorServiceChargeAbsolute;
    @SerializedName("totalFareWithTaxes")
    @Expose
    private Double totalFareWithTaxes;
    @SerializedName("ladiesSeat")
    @Expose
    private Boolean ladiesSeat;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("idType")
    @Expose
    private String idType;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("nameOnId")
    @Expose
    private String nameOnId;
    @SerializedName("primary")
    @Expose
    private Boolean primary;
    @SerializedName("ac")
    @Expose
    private Boolean ac;
    @SerializedName("sleeper")
    @Expose
    private Boolean sleeper;

    public BlockSeatPaxDetail(String age, String name, String seatNbr, String sex, float fare, Double serviceTaxAmount, Double operatorServiceChargeAbsolute, Double totalFareWithTaxes, Boolean ladiesSeat, String lastName, String mobile, String title, String email, String idType, String idNumber, String nameOnId, Boolean primary, Boolean ac, Boolean sleeper) {
        this.age = age;
        this.name = name;
        this.seatNbr = seatNbr;
        this.sex = sex;
        this.fare = fare;
        this.serviceTaxAmount = serviceTaxAmount;
        this.operatorServiceChargeAbsolute = operatorServiceChargeAbsolute;
        this.totalFareWithTaxes = totalFareWithTaxes;
        this.ladiesSeat = ladiesSeat;
        this.lastName = lastName;
        this.mobile = mobile;
        this.title = title;
        this.email = email;
        this.idType = idType;
        this.idNumber = idNumber;
        this.nameOnId = nameOnId;
        this.primary = primary;
        this.ac = ac;
        this.sleeper = sleeper;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeatNbr() {
        return seatNbr;
    }

    public void setSeatNbr(String seatNbr) {
        this.seatNbr = seatNbr;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getFare() {
        return fare;
    }

    public void setFare(Float fare) {
        this.fare = fare;
    }

    public Double getServiceTaxAmount() {
        return serviceTaxAmount;
    }

    public void setServiceTaxAmount(Double serviceTaxAmount) {
        this.serviceTaxAmount = serviceTaxAmount;
    }

    public Double getOperatorServiceChargeAbsolute() {
        return operatorServiceChargeAbsolute;
    }

    public void setOperatorServiceChargeAbsolute(Double operatorServiceChargeAbsolute) {
        this.operatorServiceChargeAbsolute = operatorServiceChargeAbsolute;
    }

    public Double getTotalFareWithTaxes() {
        return totalFareWithTaxes;
    }

    public void setTotalFareWithTaxes(Double totalFareWithTaxes) {
        this.totalFareWithTaxes = totalFareWithTaxes;
    }

    public Boolean getLadiesSeat() {
        return ladiesSeat;
    }

    public void setLadiesSeat(Boolean ladiesSeat) {
        this.ladiesSeat = ladiesSeat;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getNameOnId() {
        return nameOnId;
    }

    public void setNameOnId(String nameOnId) {
        this.nameOnId = nameOnId;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Boolean getAc() {
        return ac;
    }

    public void setAc(Boolean ac) {
        this.ac = ac;
    }

    public Boolean getSleeper() {
        return sleeper;
    }

    public void setSleeper(Boolean sleeper) {
        this.sleeper = sleeper;
    }
}
