package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.payz24.R;
import com.payz24.http.ChangePasswordHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PasswordValidator;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahesh on 3/15/2018.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack {

    private TextInputLayout inputLayoutOldPassword, inputLayoutNewPassword, inputLayoutConfirmPassword;
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private LinearLayout llSave;
    private ImageView ivSave;
    private NetworkDetection networkDetection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_password);
        initializeUi();
        enableActionBar(true);
        initializeListeners();
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.change_password));
        setSupportActionBar(toolbar);

        inputLayoutOldPassword = findViewById(R.id.inputLayoutOldPassword);
        inputLayoutNewPassword = findViewById(R.id.inputLayoutNewPassword);
        inputLayoutConfirmPassword = findViewById(R.id.inputLayoutConfirmPassword);

        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        llSave = findViewById(R.id.llSave);
        ivSave = findViewById(R.id.ivSave);
        networkDetection = new NetworkDetection();
    }

    private void initializeListeners() {
        llSave.setOnClickListener(this);
        ivSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        switch (id) {
            case R.id.llSave:
                if (!oldPassword.isEmpty()) {
                    inputLayoutOldPassword.setError("");
                    if (!newPassword.isEmpty()) {
                        inputLayoutNewPassword.setError("");
                        if (!confirmPassword.isEmpty()) {
                            if (newPassword.equalsIgnoreCase(confirmPassword)) {
                                inputLayoutConfirmPassword.setError("");

                                if (new PasswordValidator().validate(newPassword))
                                {
                                    if (new PasswordValidator().validate(confirmPassword))
                                    {
                                        serverCallForChangePassword(oldPassword, newPassword, confirmPassword);
                                    }else
                                    {
                                        inputLayoutConfirmPassword.setErrorEnabled(true);
                                        inputLayoutConfirmPassword.setError("Password required atleast 1 uppercase and 1 lower case,1 digit 1 symbol , length from 6 to 20");
                                    }
                                }else
                                {
                                    inputLayoutConfirmPassword.setErrorEnabled(true);
                                    inputLayoutNewPassword.setError("Password required atleast 1 uppercase and 1 lower case,1 digit 1 symbol , length from 6 to 20");
                                }

                            } else {
                                Toast.makeText(this, getString(R.string.passwords_mismatched_please_try_again), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            inputLayoutConfirmPassword.setErrorEnabled(true);
                            inputLayoutConfirmPassword.setError(getString(R.string.please_enter_valid_confirm_password));
                        }
                    } else {
                        inputLayoutNewPassword.setErrorEnabled(true);
                        inputLayoutNewPassword.setError(getString(R.string.please_enter_valid_new_password));
                    }
                } else {
                    inputLayoutOldPassword.setErrorEnabled(true);
                    inputLayoutOldPassword.setError(getString(R.string.please_enter_valid_old_password));
                }
                break;
            case R.id.ivSave:
                if (!oldPassword.isEmpty()) {
                    inputLayoutOldPassword.setError("");
                    if (!newPassword.isEmpty()) {
                        inputLayoutNewPassword.setError("");
                        if (!confirmPassword.isEmpty()) {
                            if (newPassword.equalsIgnoreCase(confirmPassword)) {
                                inputLayoutConfirmPassword.setError("");
                                serverCallForChangePassword(oldPassword, newPassword, confirmPassword);
                            } else {
                                Toast.makeText(this, getString(R.string.passwords_mismatched_please_try_again), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            inputLayoutConfirmPassword.setErrorEnabled(true);
                            inputLayoutConfirmPassword.setError(getString(R.string.please_enter_valid_confirm_password));
                        }
                    } else {
                        inputLayoutNewPassword.setErrorEnabled(true);
                        inputLayoutNewPassword.setError(getString(R.string.please_enter_valid_new_password));
                    }
                } else {
                    inputLayoutOldPassword.setErrorEnabled(true);
                    inputLayoutOldPassword.setError(getString(R.string.please_enter_valid_old_password));
                }
                break;
            default:
                break;
        }
    }

    private void serverCallForChangePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            showProgressBar();
            String userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.CHANGE_PASSWORD_PARAM_USER_ID, userId);
            mapOfRequestParams.put(Constants.CHANGE_PASSWORD_PARAM_CHANGE_PASSWORD, oldPassword);
            mapOfRequestParams.put(Constants.CHANGE_PASSWORD_PARAM_NEW_PASSWORD, newPassword);

            ChangePasswordHttpClient changePasswordHttpClient = new ChangePasswordHttpClient(this);
            changePasswordHttpClient.callBack = this;
            changePasswordHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_CHANGE_PASSWORD:
                if (jsonResponse != null) {
                    //  {"status":"success","statusCode":200,"message":"password successfully updated","result":[]}
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            goToLoginScreen();
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
        }
    }

    private void goToLoginScreen() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
    }
}
