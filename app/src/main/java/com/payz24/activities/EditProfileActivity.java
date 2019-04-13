package com.payz24.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.payz24.R;
import com.payz24.http.EditProfileHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahesh on 3/15/2018.
 */

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack {


    private Button btnSubmit;
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
    String state,city,country,dob;
    String userNames,addresss,pinCodes,dobs,citys,countrys,states;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_profile);
        initializeUi();
        enableActionBar(true );
        initializeListeners();
        prepareDetails();
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.edit_profile));
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
        btnSubmit=findViewById(R.id.btnSubmit);


        etFirstName = findViewById(R.id.etFirstName);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etMobileNumber.setEnabled(false);
        etEmail = findViewById(R.id.etEmail);
        etEmail.setEnabled(false);
        etAddress = findViewById(R.id.etAddress);
        etState = findViewById(R.id.etState);
        etPinCode = findViewById(R.id.etPinCode);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
    }

    private void initializeListeners() {
        btnSubmit.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                serviceCallForProfileUpdate();
                break;
            default:
                break;
        }
    }

    private void serviceCallForProfileUpdate() {


        userNames = etFirstName.getText().toString();
        addresss = etAddress.getText().toString();
        pinCodes = etPinCode.getText().toString();
        dobs = etDateOfBirth.getText().toString();
        citys = etCity.getText().toString();
        countrys = etCountry.getText().toString();
        states = etState.getText().toString();


        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.EDIT_PROFILE_PARAM_USER_ID, userId);
        mapOfRequestParams.put(Constants.EDIT_PROFILE_PARAM_NAME, userNames);
        mapOfRequestParams.put(Constants.EDIT_PROFILE_PARAM_ADDRESS, addresss);
        mapOfRequestParams.put(Constants.EDIT_PROFILE_PARAM_PIN_CODE, pinCodes);
        mapOfRequestParams.put(Constants.EDIT_PROFILE_PARAM_CITY, citys);
        mapOfRequestParams.put(Constants.EDIT_PROFILE_PARAM_COUNTRY, countrys);
        mapOfRequestParams.put(Constants.EDIT_PROFILE_PARAM_STATE, states);
        mapOfRequestParams.put(Constants.EDIT_PROFILE_PARAM_DOB, dobs);



        EditProfileHttpClient editProfileHttpClient = new EditProfileHttpClient(this);
        editProfileHttpClient.callBack = this;
        editProfileHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_EDIT_PROFILE:
                if (jsonResponse != null) {

                    Log.e("response",jsonResponse);
                    //{"status":"success","statusCode":200,"message":"Profile Updated Successfully","result":[]}
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if(status.equalsIgnoreCase(getString(R.string.success))){
                            PreferenceConnector.writeString(this,getString(R.string.USER_NAME),userNames);
                            PreferenceConnector.writeString(this,getString(R.string.address),addresss);
                            PreferenceConnector.writeString(this,getString(R.string.pin_code),pinCodes);
                            PreferenceConnector.writeString(this,getString(R.string.city),citys);
                            PreferenceConnector.writeString(this,getString(R.string.state),states);
                            PreferenceConnector.writeString(this,getString(R.string.country),countrys);
                            PreferenceConnector.writeString(this,getString(R.string.date_of_birth),dobs);
                            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
