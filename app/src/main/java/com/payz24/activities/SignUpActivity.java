package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.http.LoginHttpClient;
import com.payz24.http.SignUpHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.Login.LoginResponse;
import com.payz24.responseModels.signUp.SignUpResponse;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PasswordValidator;
import com.payz24.utils.PreferenceConnector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahesh on 2/12/2018.
 */

public class SignUpActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack {


    private TextInputLayout inputLayoutFullName, inputLayoutEmailAddress, inputLayoutMobileNumber, inputLayoutPassword, inputLayoutConfirmPassword,inputLayoutRefer;
    private EditText etFullName, etEmailAddress, etMobileNumber, etPassword, etConfirmPassword,etRefer;
    private NetworkDetection networkDetection;
    private Button btnRegister;
    private String fullName = "";
    private String emailAddress = "";
    private String mobileNumber = "";
    private String password = "";
    private String confirmPassword = "";
    private String refer = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_up);
        initializeUi();
        enableActionBar(true);
        initializeListeners();
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.sign_up));
        setSupportActionBar(toolbar);

        inputLayoutFullName = findViewById(R.id.inputLayoutFullName);
        inputLayoutEmailAddress = findViewById(R.id.inputLayoutEmailAddress);
        inputLayoutMobileNumber = findViewById(R.id.inputLayoutMobileNumber);
        inputLayoutPassword = findViewById(R.id.inputLayoutPassword);
        inputLayoutConfirmPassword = findViewById(R.id.inputLayoutConfirmPassword);
        inputLayoutRefer = findViewById(R.id.inputLayoutRefer);

        etFullName = findViewById(R.id.etFullName);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        etRefer = findViewById(R.id.etRefer);
        networkDetection = new NetworkDetection();
    }

    private void initializeListeners() {
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnRegister:
                fullName = etFullName.getText().toString();
                emailAddress = etEmailAddress.getText().toString();
                mobileNumber = etMobileNumber.getText().toString();
                password = etPassword.getText().toString();
                refer = etRefer.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                if (!fullName.isEmpty()) {
                    inputLayoutFullName.setError("");
                    inputLayoutFullName.setErrorEnabled(false);
                    if (!emailAddress.isEmpty()) {
                        inputLayoutEmailAddress.setErrorEnabled(false);
                        inputLayoutEmailAddress.setError("");
                        if (!mobileNumber.isEmpty()) {
                            inputLayoutMobileNumber.setErrorEnabled(false);
                            inputLayoutMobileNumber.setError("");
                            if (!password.isEmpty()) {
                                inputLayoutPassword.setErrorEnabled(false);
                                inputLayoutPassword.setError("");
                                if (new PasswordValidator().validate(password)) {
                                    inputLayoutPassword.setErrorEnabled(false);
                                    inputLayoutPassword.setError("");
                                    if (!confirmPassword.isEmpty()) {
                                        if (password.equalsIgnoreCase(confirmPassword)) {
                                            inputLayoutConfirmPassword.setErrorEnabled(false);
                                            inputLayoutConfirmPassword.setError("");
                                            showProgressBar();

                                            if (refer==null||refer.equals(""))
                                            {
                                                Log.e("No","No Refer");
                                                serviceCallForSignUp(fullName, emailAddress, mobileNumber, password, confirmPassword);
                                            }else
                                            {
                                                Log.e("No","Yes Refer");
                                                serviceCallForSignUpWithRefer(fullName, emailAddress, mobileNumber, password, confirmPassword,refer);

                                            }

                                        } else {
                                            inputLayoutConfirmPassword.setErrorEnabled(true);
                                            inputLayoutConfirmPassword.setError(getString(R.string.password_and_confirm_password_are_not_equal));
                                        }
                                    } else {
                                        inputLayoutConfirmPassword.setErrorEnabled(true);
                                        inputLayoutConfirmPassword.setError(getString(R.string.please_enter_confirm_password));
                                    }
                                } else {
                                    inputLayoutPassword.setErrorEnabled(true);
                                    inputLayoutPassword.setError("Password required atleast 1 uppercase and 1 lower case,1 digit 1 symbol , length from 6 to 20");
                                }
                            } else {
                                inputLayoutPassword.setErrorEnabled(true);
                                inputLayoutPassword.setError(getString(R.string.please_enter_password));
                            }
                        } else {
                            inputLayoutMobileNumber.setErrorEnabled(true);
                            inputLayoutMobileNumber.setError(getString(R.string.please_enter_mobile_number));
                        }
                    } else {
                        inputLayoutEmailAddress.setErrorEnabled(true);
                        inputLayoutEmailAddress.setError(getString(R.string.please_enter_email_address));
                    }
                } else {
                    inputLayoutFullName.setError(getString(R.string.please_enter_full_name));
                    inputLayoutFullName.setErrorEnabled(true);
                }
                break;
            default:
                break;
        }
    }

    private void serviceCallForSignUp(String fullName, String emailAddress, String mobileNumber, String password, String confirmPassword) {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_NAME, fullName);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_EMAIL, emailAddress);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_MOBILE_NUMBER, mobileNumber);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_PASSWORD, password);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_STATUS_REF, "");
            SignUpHttpClient signUpHttpClient = new SignUpHttpClient(this);
            signUpHttpClient.callBack = this;
            signUpHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            closeProgressbar();
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }


    private void serviceCallForSignUpWithRefer(String fullName, String emailAddress, String mobileNumber, String password, String confirmPassword,String refer) {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_NAME, fullName);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_EMAIL, emailAddress);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_MOBILE_NUMBER, mobileNumber);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_PASSWORD, password);

            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_REFER, refer);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_STATUS_REF, "1");
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_PROMOCODE, "refer");
            SignUpHttpClient signUpHttpClient = new SignUpHttpClient(this);
            signUpHttpClient.callBack = this;
            signUpHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            closeProgressbar();
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void serviceCallForLogin(String userName, String password) {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.LOGIN_PARAM_EMAIL_ID, userName);
            mapOfRequestParams.put(Constants.LOGIN_PARAM_PASSWORD, password);
            LoginHttpClient loginHttpClient = new LoginHttpClient(this);
            loginHttpClient.callBack = this;
            loginHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            closeProgressbar();
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_SIGN_UP:
                if (jsonResponse != null) {
                    Log.e("jsonR",jsonResponse);
                    SignUpResponse signUpResponse = new Gson().fromJson(jsonResponse, SignUpResponse.class);
                    if (signUpResponse != null) {
                        String status = signUpResponse.getStatus();
                        String message = signUpResponse.getMessage();
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                            serviceCallForLogin(emailAddress, password);
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            case Constants.SERVICE_LOGIN:
                if (jsonResponse != null) {
                    try {
                        LoginResponse loginResponse = new Gson().fromJson(jsonResponse, LoginResponse.class);
                        String status = loginResponse.getStatus();
                        String message = loginResponse.getMessage();
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            String userId = loginResponse.getResult().getUid();
                            String userType = loginResponse.getResult().getType();
                            String email = loginResponse.getResult().getUemail();
                            String userName = loginResponse.getResult().getUname();
                            String pinCode = loginResponse.getResult().getUzipcode();
                            String address = loginResponse.getResult().getAddress();
                            String dateOfBirth = loginResponse.getResult().getDob();
                            String mobileNumber = loginResponse.getResult().getUphone();

                            PreferenceConnector.writeString(this, getString(R.string.user_id), userId);
                            PreferenceConnector.writeString(this, getString(R.string.user_type), userType);
                            PreferenceConnector.writeBoolean(this, getString(R.string.is_first_time_login), true);
                            PreferenceConnector.writeString(this, getString(R.string.user_email), email);
                            PreferenceConnector.writeString(this, getString(R.string.USER_NAME), userName);
                            PreferenceConnector.writeString(this, getString(R.string.pin_code), pinCode);
                            PreferenceConnector.writeString(this, getString(R.string.address), address);
                            PreferenceConnector.writeString(this, getString(R.string.date_of_birth), dateOfBirth);
                            PreferenceConnector.writeString(this, getString(R.string.user_mobile_number), mobileNumber);
                            goToHomeActivity();
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception) {
                        Toast.makeText(this, getString(R.string.unable_to_login_please_enter_valid_details), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            default:
                break;
        }
    }

    private void goToHomeActivity() {
        Intent homeIntent = new Intent(this, DashboardActivity.class);
        startActivity(homeIntent);
    }
}
