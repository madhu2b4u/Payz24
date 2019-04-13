package com.payz24.responseModels.internationalOneWayRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahesh on 2/19/2018.
 */

public class PostData {

    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("totalamount_tosend")
    @Expose
    private String totalamountTosend;
    @SerializedName("totalamount_forpayment")
    @Expose
    private String totalamountForpayment;
    @SerializedName("adult_title")
    @Expose
    private List<String> adultTitle = null;
    @SerializedName("adult_first_name")
    @Expose
    private List<String> adultFirstName = null;
    @SerializedName("adult_last_name")
    @Expose
    private List<String> adultLastName = null;
    @SerializedName("adult_age")
    @Expose
    private List<String> adultAge = null;
    @SerializedName("adult_passport_no")
    @Expose
    private List<String> adultPassportNo = null;
    @SerializedName("adult_passport_issuing")
    @Expose
    private List<String> adultPassportIssuing = null;
    @SerializedName("adult_passport_expiry")
    @Expose
    private List<String> adultPassportExpiry = null;
    @SerializedName("adult_passport_issuing_place")
    @Expose
    private List<String> adultPassportIssuingPlace = null;
    @SerializedName("adult_visa_type")
    @Expose
    private List<String> adultVisaType = null;
    @SerializedName("fromCountry")
    @Expose
    private String fromCountry;
    @SerializedName("toCountry")
    @Expose
    private String toCountry;
    @SerializedName("newsletter")
    @Expose
    private String newsletter;
    @SerializedName("tandc")
    @Expose
    private String tandc;
    @SerializedName("usewallet")
    @Expose
    private String useWallet;
    @SerializedName("rem_amt")
    @Expose
    private String remainingAmount;
    @SerializedName("mark_type")
    @Expose
    private String mark_type;

    @SerializedName("fcfee")
    @Expose
    private String fcfee;

    @SerializedName("fmarkup")
    @Expose
    private String fmarkup;

    @SerializedName("api_total_tax")
    @Expose
    private String apiTotalTax;

    @SerializedName("api_base_fare")
    @Expose
    private String apiBaseFare;


    public PostData(String email, String walletAmount, String countryCode, String userPhone, String userId, String type, String totalamountTosend, String totalamountForpayment,
                    List<String> listOfAdultTitle, List<String> listOfAdultFirstNames, List<String> listOfAdultLastNames, List<String> listOfAdultAge,
                    List<String> listOfAdultPassportNumber, ArrayList<String> objects, List<String> listOfAdultPassportExpiryDate, ArrayList<String> objects1,
                    List<String> listOfAdultVisaType, String newsletter, String tandc, String useWallet, String remainingAmount, String flightMType, double conventionalFee, double markUpFee, double apiTotalTax, double apiBaseFare) {
        this.userEmail = email;
        this.walletAmount = walletAmount;
        this.countryCode = countryCode;
        this.userPhone = userPhone;
        this.userid = userId;
        this.type = type;
        this.totalamountTosend = totalamountTosend;
        this.totalamountForpayment = totalamountForpayment;
        this.adultTitle = listOfAdultTitle;
        this.adultFirstName = listOfAdultFirstNames;
        this.adultLastName = listOfAdultLastNames;
        this.adultAge = listOfAdultAge;
        this.adultPassportNo = listOfAdultPassportNumber;
        this.adultPassportIssuing = objects;
        this.adultPassportExpiry = listOfAdultPassportExpiryDate;
        this.adultPassportIssuingPlace = objects1;
        this.adultVisaType = listOfAdultVisaType;
        this.newsletter = newsletter;
        this.tandc = tandc;
        this.useWallet = useWallet;
        this.remainingAmount = remainingAmount;
        this.mark_type = flightMType;
        this.fcfee = String.valueOf(conventionalFee);
        this.fmarkup = String.valueOf(markUpFee);
        this.apiTotalTax = String.valueOf(apiTotalTax);
        this.apiBaseFare = String.valueOf(apiBaseFare);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotalamountTosend() {
        return totalamountTosend;
    }

    public void setTotalamountTosend(String totalamountTosend) {
        this.totalamountTosend = totalamountTosend;
    }

    public String getTotalamountForpayment() {
        return totalamountForpayment;
    }

    public void setTotalamountForpayment(String totalamountForpayment) {
        this.totalamountForpayment = totalamountForpayment;
    }

    public List<String> getAdultTitle() {
        return adultTitle;
    }

    public void setAdultTitle(List<String> adultTitle) {
        this.adultTitle = adultTitle;
    }

    public List<String> getAdultFirstName() {
        return adultFirstName;
    }

    public void setAdultFirstName(List<String> adultFirstName) {
        this.adultFirstName = adultFirstName;
    }

    public List<String> getAdultLastName() {
        return adultLastName;
    }

    public void setAdultLastName(List<String> adultLastName) {
        this.adultLastName = adultLastName;
    }

    public List<String> getAdultAge() {
        return adultAge;
    }

    public void setAdultAge(List<String> adultAge) {
        this.adultAge = adultAge;
    }

    public List<String> getAdultPassportNo() {
        return adultPassportNo;
    }

    public void setAdultPassportNo(List<String> adultPassportNo) {
        this.adultPassportNo = adultPassportNo;
    }

    public List<String> getAdultPassportIssuing() {
        return adultPassportIssuing;
    }

    public void setAdultPassportIssuing(List<String> adultPassportIssuing) {
        this.adultPassportIssuing = adultPassportIssuing;
    }

    public List<String> getAdultPassportExpiry() {
        return adultPassportExpiry;
    }

    public void setAdultPassportExpiry(List<String> adultPassportExpiry) {
        this.adultPassportExpiry = adultPassportExpiry;
    }

    public List<String> getAdultPassportIssuingPlace() {
        return adultPassportIssuingPlace;
    }

    public void setAdultPassportIssuingPlace(List<String> adultPassportIssuingPlace) {
        this.adultPassportIssuingPlace = adultPassportIssuingPlace;
    }

    public List<String> getAdultVisaType() {
        return adultVisaType;
    }

    public void setAdultVisaType(List<String> adultVisaType) {
        this.adultVisaType = adultVisaType;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public String getTandc() {
        return tandc;
    }

    public void setTandc(String tandc) {
        this.tandc = tandc;
    }

    public String getUseWallet() {
        return useWallet;
    }

    public void setUseWallet(String useWallet) {
        this.useWallet = useWallet;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getMark_type() {
        return mark_type;
    }

    public void setMark_type(String mark_type) {
        this.mark_type = mark_type;
    }

    public String getFcfee() {
        return fcfee;
    }

    public void setFcfee(String fcfee) {
        this.fcfee = fcfee;
    }

    public String getFmarkup() {
        return fmarkup;
    }

    public void setFmarkup(String fmarkup) {
        this.fmarkup = fmarkup;
    }

    public String getApiTotalTax() {
        return apiTotalTax;
    }

    public void setApiTotalTax(String apiTotalTax) {
        this.apiTotalTax = apiTotalTax;
    }

    public String getApiBaseFare() {
        return apiBaseFare;
    }

    public void setApiBaseFare(String apiBaseFare) {
        this.apiBaseFare = apiBaseFare;
    }

}
