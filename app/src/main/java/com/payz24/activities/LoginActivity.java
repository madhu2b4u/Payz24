package com.payz24.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.http.LoginHttpClient;
import com.payz24.http.SignUpHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.Login.LoginResponse;
import com.payz24.responseModels.signUp.SignUpResponse;
import com.payz24.utils.CommonMethods;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PrefUtil;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.payz24.utils.Constants.TRAVEX_SOFT_UTILITIES_BASE_URL;

/**
 * Created by mahesh on 1/29/2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack,GoogleApiClient.OnConnectionFailedListener  {

    private TextView tvSignIn;
    private ImageView ivLogo;
    private LinearLayout llSignUp, llSignIn;
    private TextInputLayout inputLayoutUsername, inputLayoutPassword;
    private EditText etUserName, etPassword, etForgotPassword;
    private ImageView ivFacebook, ivGooglePlus;
    private boolean isFirstTimeLogin;
    private NetworkDetection networkDetection;

    //facebook
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    PrefUtil prefUtil;
    LoginButton loginButton;
    List< String > permissionNeeds = Arrays.asList("user_photos", "email",
            "user_birthday", "public_profile", "AccessToken");
    //google
    private SignInButton signInButton;
    String email;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 100;   private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isFirstTimeLogin = PreferenceConnector.readBoolean(this, getString(R.string.is_first_time_login), false);
        setContentView(R.layout.layout_login_activity);
        prefUtil=new PrefUtil(this);
        initializeUi();
        initializeListeners();
        printHashKey(getApplicationContext());

        if (isFirstTimeLogin) {
            goToHomeActivity();
        }
    }

    private void initializeUi() {
        tvSignIn = findViewById(R.id.tvSignIn);
        ivLogo = findViewById(R.id.ivLogo);
        llSignIn = findViewById(R.id.llSignIn);
        llSignUp = findViewById(R.id.llSignUp);
        inputLayoutUsername = findViewById(R.id.inputLayoutUsername);
        inputLayoutPassword = findViewById(R.id.inputLayoutPassword);
        etUserName = findViewById(R.id.etUserName);
       // etUserName.setText("neni1006@gmail.com");
        etPassword = findViewById(R.id.etPassword);
      //  etPassword.setText("Vamsi@1006");
        etForgotPassword = findViewById(R.id.etForgotPassword);
        ivFacebook = findViewById(R.id.ivFacebook);
        ivGooglePlus = findViewById(R.id.ivGooglePlus);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.e("Access token",accessToken);

                        // save accessToken to SharedPreference
                        prefUtil.saveAccessToken(accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject jsonObject,
                                                            GraphResponse response) {

                                        // Getting FB User Data
                                        Bundle facebookData = getFacebookData(jsonObject);


                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,gender,location");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }


                    @Override
                    public void onCancel () {
                        Log.d("user", "Login attempt cancelled.");
                    }

                    @Override
                    public void onError (FacebookException e){
                        e.printStackTrace();
                        Log.d("User", "Login attempt failed.");
                        deleteAccessToken();
                    }
                }
        );

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        networkDetection = new NetworkDetection();
    }

    private void initializeListeners() {
        llSignIn.setOnClickListener(this);
        llSignUp.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGooglePlus.setOnClickListener(this);
        etForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llSignIn:
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (!userName.isEmpty()) {
                    //if(CommonMethods.isValidEmailFormat(userName)) {
                    inputLayoutUsername.setError("");
                    if (!password.isEmpty()) {
                        inputLayoutPassword.setError("");
                        serviceCallForLogin(userName.trim(), password.trim(),"0");
                    } else {
                        inputLayoutPassword.setErrorEnabled(true);
                        inputLayoutPassword.setError(getString(R.string.please_enter_password));
                    }
                    //  }else {
                    //     inputLayoutUsername.setErrorEnabled(true);
                    //    inputLayoutUsername.setError(getString(R.string.please_enter_valid_email));
                    //}
                } else {
                    inputLayoutUsername.setErrorEnabled(true);
                    inputLayoutUsername.setError(getString(R.string.please_enter_valid_user_name));
                }
                //goToLocation();
                break;
            case R.id.llSignUp:
                Intent signUpIntent = new Intent(this, SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            case R.id.ivGooglePlus:

                signIn();
                break;
            case R.id.ivFacebook:
                loginButton.performClick();

                break;
            case R.id.etForgotPassword:
                Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(forgotPasswordIntent);
                break;
            default:
                break;
        }
    }

    private void serviceCallForLogin(String userName, String password,String social) {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            showProgressBar();
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.LOGIN_PARAM_EMAIL_ID, userName);
            if (social.equals("1"))
            {
                mapOfRequestParams.put(Constants.LOGIN_PARAM_SOCIAL, "1");
            } else if (social.equals("0"))
            {
                mapOfRequestParams.put(Constants.LOGIN_PARAM_PASSWORD, password);
            }

            LoginHttpClient loginHttpClient = new LoginHttpClient(this);
            loginHttpClient.callBack = this;
            loginHttpClient.getJsonForType(mapOfRequestParams);
            Log.e("map",mapOfRequestParams.toString());
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }



    private void serviceCallForSignUp(String fullName, String emailAddress) {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_NAME, fullName);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_EMAIL, emailAddress);
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_MOBILE_NUMBER, "0");
            mapOfRequestParams.put(Constants.SIGN_UP_PARAM_PASSWORD, "sociallogindummy");

            SignUpHttpClient signUpHttpClient = new SignUpHttpClient(this);
            signUpHttpClient.callBack = this;
            signUpHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            closeProgressbar();
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {

           case Constants.SERVICE_SIGN_UP:
                Log.e("signUpResponse",jsonResponse.toString());

                if (jsonResponse != null) {
                    SignUpResponse signUpResponse = new Gson().fromJson(jsonResponse, SignUpResponse.class);
                    Log.e("signUpResponse",signUpResponse.toString());
                    if (signUpResponse != null) {
                        String status = signUpResponse.getStatus();
                        String message = signUpResponse.getMessage();
                        Log.e("statusSignup",status);
                        Log.e("messageSignup",message);
                        if (status.equalsIgnoreCase(getString(R.string.success))) {

                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                            serviceCallForLogin(email,"dummy","1");
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.SERVICE_LOGIN:
                Log.e("signUpResponse",jsonResponse.toString());
                if (jsonResponse != null) {
                    try {
                        LoginResponse loginResponse = new Gson().fromJson(jsonResponse, LoginResponse.class);
                        Log.e("responseLogin",jsonResponse);
                        String status = loginResponse.getStatus();
                        String message = loginResponse.getMessage();
                        Log.e("statusLogin",status);
                        Log.e("messageLogin",message);
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            String userId = loginResponse.getResult().getUid();
                            String userType = loginResponse.getResult().getType();
                            String email = loginResponse.getResult().getUemail();
                            String userName = loginResponse.getResult().getUname();
                            String pinCode = loginResponse.getResult().getUzipcode();
                            String address = loginResponse.getResult().getAddress();
                            String dateOfBirth = loginResponse.getResult().getDob();
                            String mobileNumber = loginResponse.getResult().getUphone();
                            String promocode = loginResponse.getResult().getPromocode();
                            String city = loginResponse.getResult().getUcity();
                            String country = loginResponse.getResult().getUcountry();
                            String state = loginResponse.getResult().getUstate();

                            PreferenceConnector.writeString(this, getString(R.string.user_id), userId);
                            PreferenceConnector.writeString(this, getString(R.string.user_type), userType);
                            PreferenceConnector.writeBoolean(this, getString(R.string.is_first_time_login), true);
                            PreferenceConnector.writeString(this, getString(R.string.user_email), email);
                            PreferenceConnector.writeString(this, getString(R.string.USER_NAME), userName);
                            PreferenceConnector.writeString(this, getString(R.string.pin_code), pinCode);
                            PreferenceConnector.writeString(this, getString(R.string.address), address);
                            PreferenceConnector.writeString(this, getString(R.string.date_of_birth), dateOfBirth);
                            PreferenceConnector.writeString(this, getString(R.string.user_mobile_number), mobileNumber);
                            PreferenceConnector.writeString(this, getString(R.string.promo), promocode);
                            PreferenceConnector.writeString(this, getString(R.string.state), state);
                            PreferenceConnector.writeString(this, getString(R.string.city), city);
                            PreferenceConnector.writeString(this, getString(R.string.country), country);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));


            Log.e("Name",""+object.getString("first_name"));
            Log.e("lastname",""+object.getString("last_name"));
            Log.e("email",""+object.getString("email"));
            Log.e("pic","https://graph.facebook.com/" + id + "/picture?type=large");

            String name=object.getString("first_name")+" "+object.getString("last_name");
            email=object.getString("email");
            String pic="https://graph.facebook.com/" + id + "/picture?type=large";

            Log.e("FacebookDetails",name+","+email+","+pic);



            checkEmail(email,name);




            prefUtil.saveFacebookUserInfo(object.getString("first_name"),
                    object.getString("last_name"),object.getString("email"),
                    object.getString("gender"), profile_pic.toString());

        } catch (Exception e) {
            Log.d("User", "BUNDLE Exception : "+e.toString());
        }

        return bundle;
    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    prefUtil.clearToken();
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            //Displaying name and email
            String name=acct.getDisplayName();
            email=acct.getEmail();
            String pic=acct.getPhotoUrl().toString();

            Log.e("name",name);
            Log.e("email",email);
            Log.e("pic",pic);

            checkEmail(email,name);


        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }


    private void checkEmail(final String email,final String fullname)
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.GET, TRAVEX_SOFT_UTILITIES_BASE_URL+"/rest/emailCheckvalidate?email="+email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(response);
                            Log.e("response",response);
                            String result=jsonObj.getString("result");
                            JSONObject jsonObjs = new JSONObject(result);
                            String status=jsonObjs.getString("status");
                            Log.e("status",status);
                            if (status.equals("0"))
                            {

                                serviceCallForSignUp(fullname,email);

                            }else if (status.equals("1"))
                            {
                                serviceCallForLogin(email,"dummy","1");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })

        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);

                return params;
            }

            @Override

            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mstatusCode = response.statusCode;
                if (mstatusCode == 200) {


                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(sr);
    }

    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("Hash", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Hash", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Hash", "printHashKey()", e);
        }
    }


}
