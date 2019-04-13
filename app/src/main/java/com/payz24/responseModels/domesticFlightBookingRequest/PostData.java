package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 2/21/2018.
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
    @SerializedName("child_title")
    @Expose
    private List<String> childTitle = null;
    @SerializedName("child_first_name")
    @Expose
    private List<String> childFirstName = null;
    @SerializedName("child_last_name")
    @Expose
    private List<String> childLastName = null;
    @SerializedName("child_age")
    @Expose
    private List<String> childAge = null;
    @SerializedName("infant_title")
    @Expose
    private List<String> infantTitle = null;
    @SerializedName("infant_first_name")
    @Expose
    private List<String> infantFirstName = null;
    @SerializedName("infant_last_name")
    @Expose
    private List<String> infantLastName = null;
    @SerializedName("infant_age")
    @Expose
    private List<String> infantAge = null;
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

    public PostData(String userEmail, String walletAmount, String countryCode, String userPhone, String userid, String type, String totalamountTosend, String totalamountForpayment,
                    List<String> listOfAdultTitle, List<String> listOfAdultFirstNames, List<String> listOfAdultLastNames, List<String> listOfAdultAge, List<String> listOfChildrenTitle,
                    List<String> listOfChildrenFirstNames, List<String> listOfChildrenLastNames, List<String> listOfChildrenAge, List<String> listOfInfantTitle,
                    List<String> listOfInfantFirstNames, List<String> listOfInfantLastNames, List<String> listOfInfantAge, String fromCountry, String toCountry, String newsletter,
                    String tandc, String useWallet, String remainingAmount, String flightMType, double conventionalFee, double markUpFee, double apiTotalTax, double apiBaseFare) {

        this.userEmail = userEmail;
        this.walletAmount = walletAmount;
        this.countryCode = countryCode;
        this.userPhone = userPhone;
        this.userid = userid;
        this.type = type;
        this.totalamountTosend = totalamountTosend;
        this.totalamountForpayment = totalamountForpayment;
        this.adultTitle = listOfAdultTitle;
        this.adultFirstName = listOfAdultFirstNames;
        this.adultLastName = listOfAdultLastNames;
        this.adultAge = listOfAdultAge;
        this.childTitle = listOfChildrenTitle;
        this.childFirstName = listOfChildrenFirstNames;
        this.childLastName = listOfChildrenLastNames;
        this.childAge = listOfChildrenAge;
        this.infantTitle = listOfInfantTitle;
        this.infantFirstName = listOfInfantFirstNames;
        this.infantLastName = listOfInfantLastNames;
        this.infantAge = listOfInfantAge;
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
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

    public List<String> getChildTitle() {
        return childTitle;
    }

    public void setChildTitle(List<String> childTitle) {
        this.childTitle = childTitle;
    }

    public List<String> getChildFirstName() {
        return childFirstName;
    }

    public void setChildFirstName(List<String> childFirstName) {
        this.childFirstName = childFirstName;
    }

    public List<String> getChildLastName() {
        return childLastName;
    }

    public void setChildLastName(List<String> childLastName) {
        this.childLastName = childLastName;
    }

    public List<String> getChildAge() {
        return childAge;
    }

    public void setChildAge(List<String> childAge) {
        this.childAge = childAge;
    }

    public List<String> getInfantTitle() {
        return infantTitle;
    }

    public void setInfantTitle(List<String> infantTitle) {
        this.infantTitle = infantTitle;
    }

    public List<String> getInfantFirstName() {
        return infantFirstName;
    }

    public void setInfantFirstName(List<String> infantFirstName) {
        this.infantFirstName = infantFirstName;
    }

    public List<String> getInfantLastName() {
        return infantLastName;
    }

    public void setInfantLastName(List<String> infantLastName) {
        this.infantLastName = infantLastName;
    }

    public List<String> getInfantAge() {
        return infantAge;
    }

    public void setInfantAge(List<String> infantAge) {
        this.infantAge = infantAge;
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
