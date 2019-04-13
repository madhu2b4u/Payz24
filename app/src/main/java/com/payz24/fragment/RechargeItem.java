package com.payz24.fragment;

/**
 * Created by someo on 29-05-2018.
 */

public class RechargeItem {

    String rc_id;
    String provider_name;

    public String getRc_id() {
        return rc_id;
    }

    public void setRc_id(String rc_id) {
        this.rc_id = rc_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getRc_no() {
        return rc_no;
    }

    public void setRc_no(String rc_no) {
        this.rc_no = rc_no;
    }

    public String getRc_amount() {
        return rc_amount;
    }

    public void setRc_amount(String rc_amount) {
        this.rc_amount = rc_amount;
    }

    public String getRc_type() {
        return rc_type;
    }

    public void setRc_type(String rc_type) {
        this.rc_type = rc_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTransid() {
        return Transid;
    }

    public void setTransid(String transid) {
        Transid = transid;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPorderId() {
        return porderId;
    }

    public void setPorderId(String porderId) {
        this.porderId = porderId;
    }

    String provider_id;
    String rc_no;
    String rc_amount;
    String rc_type;
    String Message;
    String Transid;
    String created;
    String porderId;
}
