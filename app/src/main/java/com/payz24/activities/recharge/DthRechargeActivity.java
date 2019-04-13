package com.payz24.activities.recharge;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.activities.BaseActivity;
import com.payz24.activities.ContactsActivity;
import com.payz24.application.AppController;
import com.payz24.fragment.CreditCardFragment;
import com.payz24.fragment.DebitCardFragment;
import com.payz24.fragment.NetBankingFragment;
import com.payz24.http.MobileRechargeHttpClient;
import com.payz24.http.OperatorsListHttpClient;
import com.payz24.interfaces.ContactsCallBack;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.interfaces.RechargeAmountCallBack;
import com.payz24.responseModels.mobileOperators.MobileOperators;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mahesh on 1/10/2018.
 */

public class DthRechargeActivity extends BaseActivity {

    private Toolbar toolbar;
    private View viewSeparator;
    private TextView tvOperatorName;
    private TextView tvContactName, tvRecharge;
    private EditText etAmount, etContactNumber;
    private LinearLayout llOperatorName;
    private NetworkDetection networkDetection;
    private BottomSheetDialog dialog;
    private int maxAmount = 0;
    String serviceProviderId;
    String selectedNetwork;
    String mobile="3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dth_activity);
        initializeUi();
        enableActionBar(true);
    }



    private void initializeUi() {
        toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.dthr));
        setSupportActionBar(toolbar);
        AppController.getInstance().setMobileRechargeContext(this);
        ImageView  icon= findViewById(R.id.recharge_icon);
        icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_dth));
        tvOperatorName = findViewById(R.id.dtvOperatorName);
        tvContactName = findViewById(R.id.dtvContactName);
        etContactNumber = findViewById(R.id.detContactNumber);
        etContactNumber.setHint("DTH Number");
        tvRecharge = findViewById(R.id.dtvRecharge);
        etAmount = findViewById(R.id.detAmount);
        llOperatorName = findViewById(R.id.dllOperatorName);
        viewSeparator = findViewById(R.id.viewSeparator);
        networkDetection = new NetworkDetection();

        llOperatorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkProvider();
            }
        });

        tvRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String serviceProviderName = tvOperatorName.getText().toString();
                String enteredAmount = etAmount.getText().toString();
                String contactNumber = etContactNumber.getText().toString();


                if (!serviceProviderName.isEmpty()) {


                    if (!contactNumber.isEmpty()) {
                        if (!enteredAmount.isEmpty()) {
                            Intent viewPlansIntent = new Intent(DthRechargeActivity.this, RechargeOverview.class);
                            viewPlansIntent.putExtra("serviceProviderName", serviceProviderName);
                            viewPlansIntent.putExtra("enteredAmount", enteredAmount);
                            viewPlansIntent.putExtra("serviceType", mobile);
                            viewPlansIntent.putExtra("contactNumber", contactNumber);
                            viewPlansIntent.putExtra("serviceProviderId", serviceProviderId);
                            startActivity(viewPlansIntent);
                        } else {
                            Toast.makeText(DthRechargeActivity.this, "Please enter amount to recharge", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(DthRechargeActivity.this, "Please enter your DTH number", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(DthRechargeActivity.this, getString(R.string.please_select_service_operator), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void networkProvider()
    {
        List<String> item = new ArrayList<String>();
        item.add("RELIANCE BIG TV");
        item.add("DISH TV");
        item.add("SUN DIRECT");
        item.add("VIDEOCON D2H");
        item.add("AIRTEL DIGITAL");
        item.add("TATA SKY");



        final CharSequence[] networks = item.toArray(new String[item.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setItems(networks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selectedNetwork = networks[item].toString();
                if(selectedNetwork.equals("RELIANCE BIG TV"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="DTRB";
                    Log.e("serviceProviderId",serviceProviderId);
                }
               if(selectedNetwork.equals("DISH TV"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="DTDT";
                    Log.e("serviceProviderId",serviceProviderId);

                }if(selectedNetwork.equals("SUN DIRECT"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="DTSD";
                    Log.e("serviceProviderId",serviceProviderId);

                }if(selectedNetwork.equals("VIDEOCON D2H"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="DTVD";
                    Log.e("serviceProviderId",serviceProviderId);

                }if(selectedNetwork.equals("AIRTEL DIGITAL"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="DTARD";
                    Log.e("serviceProviderId",serviceProviderId);

                }if(selectedNetwork.equals("TATA SKY"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="DTTS";
                    Log.e("serviceProviderId",serviceProviderId);

                }


            }
        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }




}