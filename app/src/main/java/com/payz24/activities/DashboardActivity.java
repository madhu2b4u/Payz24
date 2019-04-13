package com.payz24.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.payz24.R;
import com.payz24.fragment.HistoryFragment;
import com.payz24.fragment.HomeFragment;
import com.payz24.fragment.ProfileFragment;
import com.payz24.fragment.WalletFragment;
import com.payz24.http.MarkUpFeeHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.notifications.NotificationUtils;
import com.payz24.notifications.app.Config;
import com.payz24.responseModels.MarkUpFeeResponse.MarkUpFee;
import com.payz24.responseModels.MarkUpFeeResponse.Result;
import com.payz24.utils.Constants;
import com.payz24.utils.HttpsTrustManager;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.payz24.utils.Constants.TRAVEX_SOFT_UTILITIES_BASE_URL;
//import static com.payz24.utils.Constants.getBaseUrl;

/**
 * Created by mahesh on 3/5/2018.
 */

public class DashboardActivity extends BaseActivity implements HttpReqResCallBack {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private NetworkDetection networkDetection;
    private String to = "";
    private String flightMarkUpFee = "";
    private String flightConventionalFee = "";
    private String flightMType = "";
    private String busMarkUpFee = "";
    private String busConventionalFee = "";
    private String busMType = "";
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String regId,userId;
  //  String s=getBaseUrl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
       // Log.e("s",s);

        HttpsTrustManager.allowAllSSL();
        getDataFromIntent();
        initializeUi();
        initializeListeners();
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        showProgressBar();
        requestStoragePermission();
        Log.e("userid",userId);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(getString(R.string.to)))
                to = intent.getStringExtra(getString(R.string.to));
            else
                to = "";
        } else {
            to = "";
        }
    }

    private void initializeUi() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        networkDetection = new NetworkDetection();
        displayFirebaseRegId();
        userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
       // updateReg(userId,regId);

        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("sid", regId);
        HttpPostAsyncTask task = new HttpPostAsyncTask(params);
        task.execute(TRAVEX_SOFT_UTILITIES_BASE_URL+"/rest/updatepushsubcriptionid");

    }



    private void initializeListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_home:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(getString(R.string.home));
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_history:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(getString(R.string.history));
                        fragment = new HistoryFragment();
                        break;
                    case R.id.action_wallet:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(getString(R.string.wallet));
                        fragment = new WalletFragment();
                        break;
                    case R.id.action_profile:
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(getString(R.string.my_account));
                        }
                        fragment = new ProfileFragment();
                        break;
                    default:
                        break;
                }

                if (fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string.to), to);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.executePendingTransactions();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.detailsFragment, fragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        serviceCallForGetMarkUp();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    private void serviceCallForGetMarkUp() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            MarkUpFeeHttpClient markUpFeeHttpClient = new MarkUpFeeHttpClient(this);
            markUpFeeHttpClient.callBack = this;
            markUpFeeHttpClient.getJsonForType();
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showAlertDialog();
    }

    public void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(getResources().getString(R.string.Are_you_sure_do_you_want_to_exit));
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // finish();

            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_MARK_UP_FEE:

                try
                {
                    if (jsonResponse != null) {
                        Log.e("jsonMarkup",jsonResponse.trim());
                        MarkUpFee markUpFee = new Gson().fromJson(jsonResponse, MarkUpFee.class);
                        if (markUpFee != null) {
                            List<Result> listOfResults = markUpFee.getResult();
                            flightMarkUpFee = listOfResults.get(0).getFlightMarkup();
                            flightConventionalFee = listOfResults.get(0).getFlightConvFee();
                            flightMType = listOfResults.get(0).getFlightMType();
                            busMarkUpFee = listOfResults.get(0).getBusMarkup();
                            busConventionalFee = listOfResults.get(0).getBusConvFee();
                            busMType = listOfResults.get(0).getBusMType();
                        } else {
                            Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                    }
                    PreferenceConnector.writeString(this, getString(R.string.flight_mark_up), flightMarkUpFee);
                    PreferenceConnector.writeString(this, getString(R.string.flight_conventional_fee), flightConventionalFee);
                    PreferenceConnector.writeString(this, getString(R.string.flight_m_type), flightMType);

                    PreferenceConnector.writeString(this, getString(R.string.bus_mark_up), busMarkUpFee);
                    PreferenceConnector.writeString(this, getString(R.string.bus_conventional_fee), busConventionalFee);
                    PreferenceConnector.writeString(this, getString(R.string.bus_m_type), busMType);
                    //bottomNavigationView.setSelectedItemId(R.id.action_home);
                    closeProgressbar();
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                
                break;
            default:
                break;
        }
    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
       // Log.e(TAG, "Firebase reg id: " + regId);

    }



    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }





    public class HttpPostAsyncTask extends AsyncTask<String, Void, Void> {
        // This is the JSON body of the post
        JSONObject postData;

        // This is a constructor that allows you to pass in the JSON body
        public HttpPostAsyncTask(Map<String, String> postData) {
            if (postData != null) {
                this.postData = new JSONObject(postData);
            }
        }

        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected Void doInBackground(String... params) {

            try {
                // This is getting the url from the string we passed in
                URL url = new URL(params[0]);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("POST");
                // OPTIONAL - Sets an authorization header


                // Send the post body
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(postData.toString());
                    writer.flush();
                }

                int statusCode = urlConnection.getResponseCode();
                String response = urlConnection.getResponseMessage();

                if (statusCode == 200) {
                    Log.e("Success", "Success");
                    Log.e("getResponseMessage", response);


                } else {
                    // Status code is not 200
                    // Do something to handle the error
                    Log.e("Fail", "Fail");
                }

            } catch (Exception e) {
                Log.d("EditProfile", e.getLocalizedMessage());
            }
            return null;
        }
    }


    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_CONTACTS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}






