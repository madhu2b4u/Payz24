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

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.http.BaseHttpClient;
import com.payz24.http.ForgotPasswordHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.forgotPassword.ForgotPasswordResponse;
import com.payz24.utils.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahesh on 2/12/2018.
 */

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack {

    private TextInputLayout inputLayoutUsername;
    private EditText etUserName;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgot_password);
        initializeUi();
        enableActionBar(true);
        initializeListeners();
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.forgot_password));
        setSupportActionBar(toolbar);

        inputLayoutUsername = findViewById(R.id.inputLayoutUsername);
        etUserName = findViewById(R.id.etUserName);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private void initializeListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                String userName = etUserName.getText().toString();
                if (!userName.isEmpty()) {
                    inputLayoutUsername.setError("");
                    inputLayoutUsername.setErrorEnabled(false);
                    serviceCallForForgotPassword(userName);
                } else {
                    inputLayoutUsername.setError(getString(R.string.please_enter_valid_user_name));
                    inputLayoutUsername.setErrorEnabled(true);
                }
                break;
            default:
                break;
        }
    }

    private void serviceCallForForgotPassword(String userName) {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.FORGOT_PASSWORD_PARAM_USER_NAME, userName);

        ForgotPasswordHttpClient forgotPasswordHttpClient = new ForgotPasswordHttpClient(this);
        forgotPasswordHttpClient.callBack = this;
        forgotPasswordHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_FORGOT_PASSWORD:
                if (jsonResponse != null) {
                    Log.e("json",jsonResponse);
                    ForgotPasswordResponse forgotPasswordResponse = new Gson().fromJson(jsonResponse, ForgotPasswordResponse.class);
                    String status = forgotPasswordResponse.getStatus();
                    String message = forgotPasswordResponse.getMessage();
                    if (status.equalsIgnoreCase(getString(R.string.success))) {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
