package com.payz24.interfaces;

/**
 * Created by mahesh on 2/19/2018.
 */

public interface InfantPersonalDetailsCallBack {
    void infantPersonalDetails(int position, String selectedRadioButtonText, String name, String middleName, String lastName, String dateOfBirth, String passport, String expiryDate, String visaType);
}
