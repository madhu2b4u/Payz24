package com.payz24.activities.recharge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.activities.BaseActivity;
import com.payz24.activities.DashboardActivity;
import com.payz24.http.ProfileHttpClient;
import com.payz24.http.RechargeHttpClinet;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.ProfileResponse.ProfileResponse;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;
import com.worldline.in.constant.Param;
import com.worldline.in.ipg.PaymentStandard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RechargeResponseActivity extends BaseActivity {

    String serviceProviderName,enteredAmount,serviceType,contactNumber,status;
    TextView tvRnumber,tvRnetwork,tvRamount,tvRtype,rstatus;
    Double grandTotal=0.0;

        @Override
        public void onBackPressed() {

        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_response);
        enableActionBar(true);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle("Recharge Confirmation");
        setSupportActionBar(toolbar);
        tvRamount=(TextView)findViewById(R.id.tvRamount);
        tvRnumber=(TextView)findViewById(R.id.tvRnumber);
        tvRtype=(TextView)findViewById(R.id.tvRtype);
        tvRnetwork=(TextView)findViewById(R.id.tvRnetwork);
        rstatus=(TextView)findViewById(R.id.rstatus);
        Button home=findViewById(R.id.rHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent busIntent= new Intent(RechargeResponseActivity.this, DashboardActivity.class);
                busIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(busIntent);
            }
        });


        getValuesFromIntent();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private void getValuesFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            serviceProviderName = intent.getStringExtra("serviceProviderName");
            tvRnetwork.setText(serviceProviderName);
            enteredAmount = intent.getStringExtra("enteredAmount");
            grandTotal= Double.valueOf(enteredAmount);
            tvRamount.setText(enteredAmount);
            serviceType = intent.getStringExtra("serviceType");
            if (serviceType.equals("1"))
            {
                tvRtype.setText("MOBILE");
            } else if (serviceType.equals("2"))
            {
                tvRtype.setText("DATACARD");

            }else  if (serviceType.equals("3"))
            {
                tvRtype.setText("DTH");


            }
            contactNumber = intent.getStringExtra("contactNumber");
            status = intent.getStringExtra("status");
            tvRnumber.setText(contactNumber);
            rstatus.setText(status);
        }
    }

}

