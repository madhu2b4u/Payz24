package com.payz24.responseModels.mobileOperators;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 1/13/2018.
 */

public class MobileOperators {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("serviceLable")
    @Expose
    private String serviceLable;
    @SerializedName("accountLable")
    @Expose
    private Object accountLable;
    @SerializedName("authenticatorLable")
    @Expose
    private Object authenticatorLable;
    @SerializedName("isPrepaid")
    @Expose
    private Boolean isPrepaid;
    @SerializedName("partialPay")
    @Expose
    private Boolean partialPay;
    @SerializedName("minAmount")
    @Expose
    private Object minAmount;
    @SerializedName("maxAmount")
    @Expose
    private Object maxAmount;
    @SerializedName("isAcceptAfterDueDate")
    @Expose
    private Boolean isAcceptAfterDueDate;
    @SerializedName("minLengthOfNumber")
    @Expose
    private Integer minLengthOfNumber;
    @SerializedName("maxLengthOfNumber")
    @Expose
    private Integer maxLengthOfNumber;
    @SerializedName("minLengthOfAccountNumber")
    @Expose
    private Object minLengthOfAccountNumber;
    @SerializedName("maxLengthOfAccountNumber")
    @Expose
    private Object maxLengthOfAccountNumber;
    @SerializedName("startsWithNumber")
    @Expose
    private Object startsWithNumber;
    @SerializedName("startsWithAccountNumber")
    @Expose
    private Object startsWithAccountNumber;
    @SerializedName("minLengthOfAuthenticator3")
    @Expose
    private Object minLengthOfAuthenticator3;
    @SerializedName("maxLengthOfAuthenticator3")
    @Expose
    private Object maxLengthOfAuthenticator3;
    @SerializedName("minLengthOfFname")
    @Expose
    private Object minLengthOfFname;
    @SerializedName("maxLengthOfFname")
    @Expose
    private Object maxLengthOfFname;
    @SerializedName("minIntervalInSeconds")
    @Expose
    private Object minIntervalInSeconds;
    @SerializedName("maxRechargeAmountPerDay")
    @Expose
    private float maxRechargeAmountPerDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getServiceLable() {
        return serviceLable;
    }

    public void setServiceLable(String serviceLable) {
        this.serviceLable = serviceLable;
    }

    public Object getAccountLable() {
        return accountLable;
    }

    public void setAccountLable(Object accountLable) {
        this.accountLable = accountLable;
    }

    public Object getAuthenticatorLable() {
        return authenticatorLable;
    }

    public void setAuthenticatorLable(Object authenticatorLable) {
        this.authenticatorLable = authenticatorLable;
    }

    public Boolean getIsPrepaid() {
        return isPrepaid;
    }

    public void setIsPrepaid(Boolean isPrepaid) {
        this.isPrepaid = isPrepaid;
    }

    public Boolean getPartialPay() {
        return partialPay;
    }

    public void setPartialPay(Boolean partialPay) {
        this.partialPay = partialPay;
    }

    public Object getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Object minAmount) {
        this.minAmount = minAmount;
    }

    public Object getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Object maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Boolean getIsAcceptAfterDueDate() {
        return isAcceptAfterDueDate;
    }

    public void setIsAcceptAfterDueDate(Boolean isAcceptAfterDueDate) {
        this.isAcceptAfterDueDate = isAcceptAfterDueDate;
    }

    public Integer getMinLengthOfNumber() {
        return minLengthOfNumber;
    }

    public void setMinLengthOfNumber(Integer minLengthOfNumber) {
        this.minLengthOfNumber = minLengthOfNumber;
    }

    public Integer getMaxLengthOfNumber() {
        return maxLengthOfNumber;
    }

    public void setMaxLengthOfNumber(Integer maxLengthOfNumber) {
        this.maxLengthOfNumber = maxLengthOfNumber;
    }

    public Object getMinLengthOfAccountNumber() {
        return minLengthOfAccountNumber;
    }

    public void setMinLengthOfAccountNumber(Object minLengthOfAccountNumber) {
        this.minLengthOfAccountNumber = minLengthOfAccountNumber;
    }

    public Object getMaxLengthOfAccountNumber() {
        return maxLengthOfAccountNumber;
    }

    public void setMaxLengthOfAccountNumber(Object maxLengthOfAccountNumber) {
        this.maxLengthOfAccountNumber = maxLengthOfAccountNumber;
    }

    public Object getStartsWithNumber() {
        return startsWithNumber;
    }

    public void setStartsWithNumber(Object startsWithNumber) {
        this.startsWithNumber = startsWithNumber;
    }

    public Object getStartsWithAccountNumber() {
        return startsWithAccountNumber;
    }

    public void setStartsWithAccountNumber(Object startsWithAccountNumber) {
        this.startsWithAccountNumber = startsWithAccountNumber;
    }

    public Object getMinLengthOfAuthenticator3() {
        return minLengthOfAuthenticator3;
    }

    public void setMinLengthOfAuthenticator3(Object minLengthOfAuthenticator3) {
        this.minLengthOfAuthenticator3 = minLengthOfAuthenticator3;
    }

    public Object getMaxLengthOfAuthenticator3() {
        return maxLengthOfAuthenticator3;
    }

    public void setMaxLengthOfAuthenticator3(Object maxLengthOfAuthenticator3) {
        this.maxLengthOfAuthenticator3 = maxLengthOfAuthenticator3;
    }

    public Object getMinLengthOfFname() {
        return minLengthOfFname;
    }

    public void setMinLengthOfFname(Object minLengthOfFname) {
        this.minLengthOfFname = minLengthOfFname;
    }

    public Object getMaxLengthOfFname() {
        return maxLengthOfFname;
    }

    public void setMaxLengthOfFname(Object maxLengthOfFname) {
        this.maxLengthOfFname = maxLengthOfFname;
    }

    public Object getMinIntervalInSeconds() {
        return minIntervalInSeconds;
    }

    public void setMinIntervalInSeconds(Object minIntervalInSeconds) {
        this.minIntervalInSeconds = minIntervalInSeconds;
    }

    public float getMaxRechargeAmountPerDay() {
        return maxRechargeAmountPerDay;
    }

    public void setMaxRechargeAmountPerDay(float maxRechargeAmountPerDay) {
        this.maxRechargeAmountPerDay = maxRechargeAmountPerDay;
    }
}
