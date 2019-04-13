package com.payz24.responseModels.BusStoreDetailsDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahesh on 3/2/2018.
 */

public class UserData {

    @SerializedName("bus_fname")
    @Expose
    private List<String> busFname = null;
    @SerializedName("bus_lname")
    @Expose
    private List<String> busLname = null;
    @SerializedName("bus_gender")
    @Expose
    private List<String> busGender = null;
    @SerializedName("bus_age")
    @Expose
    private List<String> busAge = null;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_phone_code")
    @Expose
    private String userPhoneCode;
    @SerializedName("bus_seat")
    @Expose
    private List<String> busSeat = null;
    @SerializedName("board_point")
    @Expose
    private String boardPoint;
    @SerializedName("drop_point")
    @Expose
    private String dropPoint;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_number")
    @Expose
    private String userNumber;
    @SerializedName("user_emer_number")
    @Expose
    private String userEmerNumber;
    @SerializedName("user_city")
    @Expose
    private String userCity;
    @SerializedName("user_state")
    @Expose
    private String userState;
    @SerializedName("user_id_proof")
    @Expose
    private String userIdProof;
    @SerializedName("user_id_number")
    @Expose
    private String userIdNumber;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("user_pincode")
    @Expose
    private String userPincode;
    @SerializedName("usewallet")
    @Expose
    private String useWallet;
    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount;
    @SerializedName("rem_amt")
    @Expose
    private String remainingAmount;

    public UserData(List<String> listOfFirstName, ArrayList<String> listOfLastNames, List<String> listOfGenders, List<String> listOfAge, String userEmail, String userPhoneCode,
                    List<String> listOfBusSeatsNames, String boardingPointId, String dropPointId, String userId, String userType, String userPhoneNumber, String userEmergencyNumber,
                    String userCity, String userState, String userIdProof, String userIdNumber, String userAddress, String userPincode, String useWallet, String walletAmount, String remainingAmount) {

        this.busFname = listOfFirstName;
        this.busLname = listOfFirstName;
        this.busGender = listOfGenders;
        this.busAge = listOfAge;
        this.userEmail = userEmail;
        this.userPhoneCode = userPhoneCode;
        this.busSeat = listOfBusSeatsNames;
        this.boardPoint = boardingPointId;
        this.dropPoint = dropPointId;
        this.userId = userId;
        this.userType = userType;
        this.userNumber = userPhoneNumber;
        this.userEmerNumber = userEmergencyNumber;
        this.userCity = userCity;
        this.userState = userState;
        this.userIdProof = userIdProof;
        this.userIdNumber = userIdNumber;
        this.userAddress = userAddress;
        this.userPincode = userPincode;
        this.useWallet = useWallet;
        this.walletAmount = walletAmount;
        this.remainingAmount = remainingAmount;
    }

    public List<String> getBusFname() {
        return busFname;
    }

    public void setBusFname(List<String> busFname) {
        this.busFname = busFname;
    }

    public List<String> getBusLname() {
        return busLname;
    }

    public void setBusLname(List<String> busLname) {
        this.busLname = busLname;
    }

    public List<String> getBusGender() {
        return busGender;
    }

    public void setBusGender(List<String> busGender) {
        this.busGender = busGender;
    }

    public List<String> getBusAge() {
        return busAge;
    }

    public void setBusAge(List<String> busAge) {
        this.busAge = busAge;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneCode() {
        return userPhoneCode;
    }

    public void setUserPhoneCode(String userPhoneCode) {
        this.userPhoneCode = userPhoneCode;
    }

    public List<String> getBusSeat() {
        return busSeat;
    }

    public void setBusSeat(List<String> busSeat) {
        this.busSeat = busSeat;
    }

    public String getBoardPoint() {
        return boardPoint;
    }

    public void setBoardPoint(String boardPoint) {
        this.boardPoint = boardPoint;
    }

    public String getDropPoint() {
        return dropPoint;
    }

    public void setDropPoint(String dropPoint) {
        this.dropPoint = dropPoint;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserEmerNumber() {
        return userEmerNumber;
    }

    public void setUserEmerNumber(String userEmerNumber) {
        this.userEmerNumber = userEmerNumber;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserIdProof() {
        return userIdProof;
    }

    public void setUserIdProof(String userIdProof) {
        this.userIdProof = userIdProof;
    }

    public String getUserIdNumber() {
        return userIdNumber;
    }

    public void setUserIdNumber(String userIdNumber) {
        this.userIdNumber = userIdNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPincode() {
        return userPincode;
    }

    public void setUserPincode(String userPincode) {
        this.userPincode = userPincode;
    }

    public String getUseWallet() {
        return useWallet;
    }

    public void setUseWallet(String useWallet) {
        this.useWallet = useWallet;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}
