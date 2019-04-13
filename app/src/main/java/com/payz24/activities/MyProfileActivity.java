package com.payz24.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.payz24.R;
import com.payz24.utils.PreferenceConnector;

/**
 * Created by mahesh on 3/14/2018.
 */

public class MyProfileActivity extends BaseActivity {

    private TextInputLayout inputLayoutFirstName, inputLayoutDateOfBirth, inputLayoutMobileNumber, inputLayoutEmail,
            inputLayoutAddress,inputLayoutState,inputLayoutPinCode,inputLayoutCity,inputLayoutCountry;
    private EditText etFirstName, etDateOfBirth, etMobileNumber, etEmail, etAddress,etState,etPinCode,etCountry,etCity;
    private String userId = "";
    private String userType = "";
    private String email = "";
    private String userName = "";
    private String pinCode = "";
    private String address = "";
    private String mobileNumber = "";
    private String dateOfBirth = "";
    String state,city,country;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_profile);
        initializeUi();
        enableActionBar(true);
        prepareDetails();
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.my_profile));
        setSupportActionBar(toolbar);

        inputLayoutFirstName = findViewById(R.id.inputLayoutFirstName);
        inputLayoutDateOfBirth = findViewById(R.id.inputLayoutDateOfBirth);
        inputLayoutMobileNumber = findViewById(R.id.inputLayoutMobileNumber);
        inputLayoutEmail = findViewById(R.id.inputLayoutEmail);
        inputLayoutAddress = findViewById(R.id.inputLayoutAddress);
        inputLayoutPinCode = findViewById(R.id.inputLayoutPinCode);
        inputLayoutState = findViewById(R.id.inputLayoutState);
        inputLayoutCity = findViewById(R.id.inputLayoutCity);
        inputLayoutCountry = findViewById(R.id.inputLayoutCountry);

        etFirstName = findViewById(R.id.etFirstName);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etState = findViewById(R.id.etState);
        etPinCode = findViewById(R.id.etPinCode);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
    }

    private void prepareDetails() {
        userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        userType = PreferenceConnector.readString(this, getString(R.string.user_type), "");
        email = PreferenceConnector.readString(this, getString(R.string.user_email), "");
        userName = PreferenceConnector.readString(this, getString(R.string.USER_NAME), "");
        pinCode = PreferenceConnector.readString(this, getString(R.string.pin_code), "");
        address = PreferenceConnector.readString(this, getString(R.string.address), "");
        dateOfBirth = PreferenceConnector.readString(this, getString(R.string.date_of_birth), "");
        mobileNumber = PreferenceConnector.readString(this, getString(R.string.user_mobile_number), "");

        state = PreferenceConnector.readString(this, getString(R.string.state), "");
        city = PreferenceConnector.readString(this, getString(R.string.city), "");
        country = PreferenceConnector.readString(this, getString(R.string.country), "");

        etFirstName.setText(userName);
        if (address.isEmpty())
            etAddress.setText("N/A");
        else
            etAddress.setText(address);
        if (dateOfBirth.isEmpty())
            etDateOfBirth.setText("N/A");
        else
            etDateOfBirth.setText(dateOfBirth);
        etMobileNumber.setText(mobileNumber);
        etEmail.setText(email);

        if (city.isEmpty())
            etCity.setText("N/A");
        else
            etCity.setText(city);

        if (state.isEmpty())
            etState.setText("N/A");
        else
            etState.setText(state);

        if (country.isEmpty())
            etCountry.setText("N/A");
        else
            etCountry.setText(country);



        if (pinCode.isEmpty())
            etPinCode.setText("N/A");
        else
            etPinCode.setText(pinCode);


    }
}
