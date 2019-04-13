package com.payz24.responseModels.ProfileResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/27/2018.
 */

public class Result {
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("uemail")
    @Expose
    private String uemail;
    @SerializedName("upassword")
    @Expose
    private String upassword;
    @SerializedName("salt")
    @Expose
    private String salt;
    @SerializedName("uphone")
    @Expose
    private String uphone;
    @SerializedName("ucity")
    @Expose
    private String ucity;
    @SerializedName("ustate")
    @Expose
    private String ustate;
    @SerializedName("uzipcode")
    @Expose
    private String uzipcode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("ucountry")
    @Expose
    private String ucountry;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("profilepic")
    @Expose
    private String profilepic;
    @SerializedName("wid")
    @Expose
    private String wid;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("wallet_amt")
    @Expose
    private String walletAmt;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUcity() {
        return ucity;
    }

    public void setUcity(String ucity) {
        this.ucity = ucity;
    }

    public String getUstate() {
        return ustate;
    }

    public void setUstate(String ustate) {
        this.ustate = ustate;
    }

    public String getUzipcode() {
        return uzipcode;
    }

    public void setUzipcode(String uzipcode) {
        this.uzipcode = uzipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUcountry() {
        return ucountry;
    }

    public void setUcountry(String ucountry) {
        this.ucountry = ucountry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getWalletAmt() {
        return walletAmt;
    }

    public void setWalletAmt(String walletAmt) {
        this.walletAmt = walletAmt;
    }
}
