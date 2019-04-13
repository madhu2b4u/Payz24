package com.payz24.responseModels.flightBookingHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/8/2018.
 */

public class Result {

    @SerializedName("fid")
    @Expose
    private String fid;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pnr")
    @Expose
    private String pnr;
    @SerializedName("booking_status")
    @Expose
    private String bookingStatus;
    @SerializedName("journeyType")
    @Expose
    private String journeyType;
    @SerializedName("stops")
    @Expose
    private String stops;
    @SerializedName("from_city")
    @Expose
    private String fromCity;
    @SerializedName("departDate")
    @Expose
    private String departDate;
    @SerializedName("to_city")
    @Expose
    private String toCity;
    @SerializedName("returnDate")
    @Expose
    private String returnDate;
    @SerializedName("Noadults")
    @Expose
    private String noadults;
    @SerializedName("Nochilds")
    @Expose
    private String nochilds;
    @SerializedName("Noinfants")
    @Expose
    private String noinfants;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("booked_date")
    @Expose
    private String bookedDate;
    @SerializedName("flight_type")
    @Expose
    private String flightType;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("payment_transection_id")
    @Expose
    private String paymentTransectionId;
    @SerializedName("Canid")
    @Expose
    private String canid;
    @SerializedName("can_status")
    @Expose
    private String canStatus;
    @SerializedName("cancelationdtls")
    @Expose
    private String cancelationdtls;
    @SerializedName("CancellationProcessDateTime")
    @Expose
    private String cancellationProcessDateTime;
    @SerializedName("CancellationCharges")
    @Expose
    private String cancellationCharges;
    @SerializedName("RefundStatus")
    @Expose
    private String refundStatus;
    @SerializedName("FinalRefundAmount")
    @Expose
    private String finalRefundAmount;
    @SerializedName("RefundDateTime")
    @Expose
    private String refundDateTime;
    @SerializedName("refund_type")
    @Expose
    private String refundType;
    @SerializedName("usewallet")
    @Expose
    private String usewallet;
    @SerializedName("wallet_amt")
    @Expose
    private String walletAmt;
    @SerializedName("gateway_amt")
    @Expose
    private String gatewayAmt;
    @SerializedName("cr")
    @Expose
    private String cr;

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }





    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getNoadults() {
        return noadults;
    }

    public void setNoadults(String noadults) {
        this.noadults = noadults;
    }

    public String getNochilds() {
        return nochilds;
    }

    public void setNochilds(String nochilds) {
        this.nochilds = nochilds;
    }

    public String getNoinfants() {
        return noinfants;
    }

    public void setNoinfants(String noinfants) {
        this.noinfants = noinfants;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentTransectionId() {
        return paymentTransectionId;
    }

    public void setPaymentTransectionId(String paymentTransectionId) {
        this.paymentTransectionId = paymentTransectionId;
    }

    public String getCanid() {
        return canid;
    }

    public void setCanid(String canid) {
        this.canid = canid;
    }

    public String getCanStatus() {
        return canStatus;
    }

    public void setCanStatus(String canStatus) {
        this.canStatus = canStatus;
    }

    public String getCancelationdtls() {
        return cancelationdtls;
    }

    public void setCancelationdtls(String cancelationdtls) {
        this.cancelationdtls = cancelationdtls;
    }

    public String getCancellationProcessDateTime() {
        return cancellationProcessDateTime;
    }

    public void setCancellationProcessDateTime(String cancellationProcessDateTime) {
        this.cancellationProcessDateTime = cancellationProcessDateTime;
    }

    public String getCancellationCharges() {
        return cancellationCharges;
    }

    public void setCancellationCharges(String cancellationCharges) {
        this.cancellationCharges = cancellationCharges;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getFinalRefundAmount() {
        return finalRefundAmount;
    }

    public void setFinalRefundAmount(String finalRefundAmount) {
        this.finalRefundAmount = finalRefundAmount;
    }

    public String getRefundDateTime() {
        return refundDateTime;
    }

    public void setRefundDateTime(String refundDateTime) {
        this.refundDateTime = refundDateTime;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getUsewallet() {
        return usewallet;
    }

    public void setUsewallet(String usewallet) {
        this.usewallet = usewallet;
    }

    public String getWalletAmt() {
        return walletAmt;
    }

    public void setWalletAmt(String walletAmt) {
        this.walletAmt = walletAmt;
    }

    public String getGatewayAmt() {
        return gatewayAmt;
    }

    public void setGatewayAmt(String gatewayAmt) {
        this.gatewayAmt = gatewayAmt;
    }
}
