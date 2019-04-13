package com.payz24.activities.recharge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.activities.BaseActivity;
import com.payz24.http.ProfileHttpClient;
import com.payz24.http.RechargeHttpClinet;
import com.payz24.http.SmsHttpClient;
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

public class RechargeOverview extends BaseActivity implements HttpReqResCallBack{

    String serviceProviderName,enteredAmount,serviceType,serviceProviderId,contactNumber,Desc,typeofrc;
    TextView tvOnumber,tvOnetwork,tvOamount,tvOtype;
    Button tvOpayment;
    CheckBox cbWallet;
    TextView wtvMoney;
    private NetworkDetection networkDetection;
    private String walletBalance = "";
    private String useWallet = "";
    private String remainingAmount = "";
    Double grandTotal=0.0;
    String requestDate="";
    private String orderId = "";
    private String transactionRefNo = "";
    private String statusDescription = "";
    private String statusCode = "";
    private String authZstatus = "";
    private String rrn = "";
    String mes;
    String stat;
    Double walletAmount = 0.0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_overview);
        tvOamount=(TextView)findViewById(R.id.tvOamount);
        tvOnumber=(TextView)findViewById(R.id.tvOnumber);
        tvOtype=(TextView)findViewById(R.id.tvOtype);
        tvOnetwork=(TextView)findViewById(R.id.tvOnetwork);
        wtvMoney=(TextView)findViewById(R.id.wtvMoney);
        tvOpayment=(Button)findViewById(R.id.tvOpayment);
        cbWallet=(CheckBox)findViewById(R.id.cbWallet);
        networkDetection = new NetworkDetection();
        tvOpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgressBar();
                serviceCallForPayment();




            }
        });

        getValuesFromIntent();
        serviceCallForGetProfileDetails();

    }

    public static int getRandomOrderId() {
        Random random = new Random();
        int randomOrderId = random.nextInt();
        if (randomOrderId < 0) {
            randomOrderId = randomOrderId * -1;
        }
        return randomOrderId;
    }

    private void getValuesFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            serviceProviderName = intent.getStringExtra("serviceProviderName");
            tvOnetwork.setText(serviceProviderName);
            enteredAmount = intent.getStringExtra("enteredAmount");
            grandTotal= Double.valueOf(enteredAmount);
            tvOamount.setText(enteredAmount);
            serviceType = intent.getStringExtra("serviceType");
            if (serviceType.equals("1"))
            {
                tvOtype.setText("MOBILE");
                typeofrc="prepaid";
                Desc="Recharge Payment";
            } else if (serviceType.equals("2"))
            {
                tvOtype.setText("DATACARD");
                Desc="Datacard Payment";
                typeofrc="prepaid-datacard";

            }else  if (serviceType.equals("3"))
            {
                tvOtype.setText("DTH");
                Desc="DTH Payment";
                typeofrc="Dth";


            }
            serviceProviderId = intent.getStringExtra("serviceProviderId");
            contactNumber = intent.getStringExtra("contactNumber");
            tvOnumber.setText(contactNumber);
        }
    }


    private void serviceCallForPayment() {

        Log.e("grandTotal",""+grandTotal);

        if (grandTotal != 0.0) {
            if (cbWallet.isChecked()) {
                useWallet = "1";
                if (Double.parseDouble(walletBalance) >= grandTotal) {
                    walletAmount = grandTotal;
                    remainingAmount = "0";
                    Log.e("WalletAmmount",""+walletAmount);
                    serviceCallForRecharge();


                } else {

                    Log.e("WalletAmmount",""+walletAmount);
                    walletAmount = Double.parseDouble(walletBalance);
                    remainingAmount = String.valueOf(grandTotal - Double.parseDouble(walletBalance));
                    Log.e("remainingAmount",""+remainingAmount);
                    Double totalFare = Double.parseDouble(remainingAmount) * 100.0;
                    Intent intent = new Intent(RechargeOverview.this, PaymentStandard.class);
                    intent.putExtra(Param.ORDER_ID, "" + getRandomOrderId());
                    intent.putExtra(Param.TRANSACTION_AMOUNT, String.valueOf(totalFare.intValue()));
                    intent.putExtra(Param.TRANSACTION_CURRENCY, "INR");
                    intent.putExtra(Param.TRANSACTION_DESCRIPTION, "Sock money");
                    intent.putExtra(Param.TRANSACTION_TYPE, "S");
                    intent.putExtra(Param.KEY, "6375b97b954b37f956966977e5753ee6");//*Optional
                    intent.putExtra(Param.MID, "WL0000000027698");//*Optional
                    startActivityForResult(intent, 1);

                }
            } else {
                useWallet = "0";
                remainingAmount = "0";
                Log.e("remainingAmount",""+grandTotal);
                Double totalFare = grandTotal * 100.0;
                Log.e("totalFare",""+totalFare);
                Intent intent = new Intent(RechargeOverview.this, PaymentStandard.class);
                intent.putExtra(Param.ORDER_ID, "" + getRandomOrderId());
                intent.putExtra(Param.TRANSACTION_AMOUNT, String.valueOf(""+totalFare.intValue()));
                intent.putExtra(Param.TRANSACTION_CURRENCY, "INR");
                intent.putExtra(Param.TRANSACTION_DESCRIPTION, "Sock money");
                intent.putExtra(Param.TRANSACTION_TYPE, "S");
                intent.putExtra(Param.KEY, "6375b97b954b37f956966977e5753ee6");//*Optional
                intent.putExtra(Param.MID, "WL0000000027698");//*Optional
                startActivityForResult(intent, 1);
            }
        } else {
            useWallet = "0";
            remainingAmount = "0";
        }



    }



    private void serviceCallForRecharge()
    {

        String selectedUserId = PreferenceConnector.readString(this, getString(R.string.user_id), "");


        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.rechargeNumber, contactNumber);
        mapOfRequestParams.put(Constants.rechargeAmount, ""+grandTotal);
        mapOfRequestParams.put(Constants.servicetype, serviceType);
        mapOfRequestParams.put(Constants.OperatorName, serviceProviderName);
        mapOfRequestParams.put(Constants.OperatorId, serviceProviderId);
        mapOfRequestParams.put(Constants.typeofrc, typeofrc);
        mapOfRequestParams.put(Constants.complete_j, "");
        mapOfRequestParams.put(Constants.PgMeTrnRefNo, transactionRefNo);
        mapOfRequestParams.put(Constants.TrnAmt, ""+grandTotal);
        mapOfRequestParams.put(Constants.StatusCode, statusCode);
        mapOfRequestParams.put(Constants.StatusDesc, statusDescription);
        mapOfRequestParams.put(Constants.TrnReqDate, requestDate);
        mapOfRequestParams.put(Constants.ResponseCode, "");
        mapOfRequestParams.put(Constants.Rrn, rrn);
        mapOfRequestParams.put(Constants.AuthZCode, authZstatus);
        mapOfRequestParams.put(Constants.Desc, Desc);
        mapOfRequestParams.put(Constants.userid, selectedUserId);
        mapOfRequestParams.put(Constants.usertype, "3");
        mapOfRequestParams.put(Constants.usewallet, useWallet);
        mapOfRequestParams.put(Constants.wallet_amount, String.valueOf(walletAmount));
        mapOfRequestParams.put(Constants.rem_amt, remainingAmount);

        mapOfRequestParams.put(Constants.TotalFare_org, ""+grandTotal);
        mapOfRequestParams.put(Constants.dis_comision, "0");
        mapOfRequestParams.put(Constants.agent_markup, "");
        mapOfRequestParams.put(Constants.admin_m_type, "M");



        Log.e(Constants.rechargeNumber, contactNumber);
        Log.e(Constants.rechargeAmount, enteredAmount);
        Log.e(Constants.servicetype, serviceType);
        Log.e(Constants.OperatorName, serviceProviderName);
        Log.e(Constants.OperatorId, serviceProviderId);
        Log.e(Constants.typeofrc, typeofrc);
        Log.e(Constants.complete_j, "");
        Log.e(Constants.PgMeTrnRefNo, transactionRefNo);
        Log.e(Constants.TrnAmt, enteredAmount);
        Log.e(Constants.StatusCode, statusCode);
        Log.e(Constants.StatusDesc, statusDescription);
        Log.e(Constants.TrnReqDate,  requestDate);
        Log.e(Constants.ResponseCode, "");
        Log.e(Constants.Rrn, rrn);
        Log.e(Constants.AuthZCode, authZstatus);
        Log.e(Constants.Desc, Desc);
        Log.e(Constants.userid, selectedUserId);
        Log.e(Constants.usertype, "3");
        Log.e(Constants.usewallet, useWallet);
        Log.e(Constants.wallet_amount, String.valueOf(walletAmount));
        Log.e(Constants.rem_amt, remainingAmount);



        RechargeHttpClinet mobileRechargeHttpClient = new RechargeHttpClinet(this);
        mobileRechargeHttpClient.callBack = this;
        mobileRechargeHttpClient.getJsonForType(mapOfRequestParams);
    }





    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
//         showProgressBar();
       // tvOpayment.setEnabled(false);
        switch (requestType) {

            case Constants.SERVICE_PROFILE:
                if (jsonResponse != null) {

                    Log.e("json",jsonResponse);
                    ProfileResponse profileResponse = new Gson().fromJson(jsonResponse, ProfileResponse.class);
                    if (profileResponse != null) {
                        if (profileResponse.getResult().getWalletAmt() != null)
                            walletBalance = profileResponse.getResult().getWalletAmt();
                        wtvMoney.setText(walletBalance);
                        cbWallet.setChecked(true);
                    }
                }
                closeProgressbar();
                break;

            case Constants.SERVICE_MOBILE_RECHARGES:
                Log.e("jsonResponse",jsonResponse);

                if (jsonResponse != null) {

                   try
                   {
                       closeProgressbar();
                       JSONObject jsonObject = new JSONObject(jsonResponse);
                       String status = jsonObject.getString("status");
                       String message = jsonObject.getString("message");
                       stat=jsonObject.getJSONObject("result").getString("status").toLowerCase();
                       mes=jsonObject.getJSONObject("result").getString("Message");
                       Log.e("mes",mes);
                       Log.e("stat",stat);

                       serviceCallForSMSGateWay(stat);
                       goToReviewActivity(stat);



                   }catch (JSONException e)
                   {
                       e.printStackTrace();
                   }

                }

                closeProgressbar();
                break;


        }

    }

    private void goToReviewActivity(String message) {

        Intent viewPlansIntent=new Intent(RechargeOverview.this,RechargeResponseActivity.class);
        viewPlansIntent.putExtra("serviceProviderName", serviceProviderName);
        viewPlansIntent.putExtra("enteredAmount", enteredAmount);
        viewPlansIntent.putExtra("serviceType", serviceType);
        viewPlansIntent.putExtra("contactNumber", contactNumber);
        viewPlansIntent.putExtra("status", message);
        startActivity(viewPlansIntent);
        finish();
    }




    @Override
    public void onBackPressed() {

    }

    private void serviceCallForGetProfileDetails() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            String userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.PROFILE_PARAM_USER_ID, userId);

            ProfileHttpClient profileHttpClient = new ProfileHttpClient(this);
            profileHttpClient.callBack = this;
            profileHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            closeProgressbar();
            if (resultCode == RESULT_OK) {

                Log.e("data",data.toString());
                orderId = data.getStringExtra(Param.ORDER_ID);
                transactionRefNo = data.getStringExtra(Param.TRANSACTION_REFERENCE_NUMBER);
                rrn = data.getStringExtra(Param.RRN);
                statusCode = data.getStringExtra(Param.STATUS_CODE);
                statusDescription = data.getStringExtra(Param.STATUS_DESCRIPTION);
                String transactionAmount = data.getStringExtra(Param.TRANSACTION_AMOUNT);
                requestDate = data.getStringExtra(Param.TRANSACTION_REQUEST_DATE);
                String authNStatus = data.getStringExtra(Param.AUTH_N_STATUS);
                authZstatus = data.getStringExtra(Param.AUTH_Z_STATUS);
                String captureStatus = data.getStringExtra(Param.CAPTURE_STATUS);
                String pgRefCancelReqId = data.getStringExtra(Param.PG_REF_CANCEL_REQ_ID);
                String refundAmount = data.getStringExtra(Param.REFUND_AMOUNT);
                String addField1 = data.getStringExtra(Param.ADDL_FIELD_1);
                String addField2 = data.getStringExtra(Param.ADDL_FIELD_2);
                String addField3 = data.getStringExtra(Param.ADDL_FIELD_3);
                String addField4 = data.getStringExtra(Param.ADDL_FIELD_4);
                String addField5 = data.getStringExtra(Param.ADDL_FIELD_5);
                String addField6 = data.getStringExtra(Param.ADDL_FIELD_6);
                String addField7 = data.getStringExtra(Param.ADDL_FIELD_7);
                String addField8 = data.getStringExtra(Param.ADDL_FIELD_8);
                String addField9 = data.getStringExtra(Param.ADDL_FIELD_9);
                if (statusDescription.equalsIgnoreCase("Transaction is Successful")) {


                    serviceCallForRecharge();



                } else {
                    Toast.makeText(this, statusDescription, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void serviceCallForSMSGateWay(String status) {

        if (status.equalsIgnoreCase("success"))
        {
            status="success";

        }else if (status.equalsIgnoreCase("pending"))
        {
            status="success, status is pending";
        }else if (status.equalsIgnoreCase("failed"))
        {
            status="failed";
        }

        String message="Hi ,Thanks for using recharge facilities and your order id is "+orderId+" and recharge number is "+contactNumber+" with amount "+ enteredAmount+"  is "+status+ "\n For More Details Visit https://payz24.com";

        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SMS_PARAM_PHONE, contactNumber);
        if (status.equalsIgnoreCase("success")||status.equalsIgnoreCase("success, status is pending")) {
            mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, message);
        } else {
            mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, "Hi ,Thanks for using recharge facilities and your order id is "+orderId+"   and recharge number is "+contactNumber+" with amount "+ enteredAmount+"  is failed .For More Details Visit https://payz24.com");
        }
        SmsHttpClient smsHttpClient = new SmsHttpClient(this);
        smsHttpClient.callBack = this;
        smsHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
