package com.payz24.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.adapter.WalletAdapter;
import com.payz24.application.AppController;
import com.payz24.http.AddWalletBalanceHttpClient;
import com.payz24.http.ProfileHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.ProfileResponse.ProfileResponse;
import com.payz24.responseModels.addMoneyWallet.WalletAddMoney;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;
import com.payz24.utils.WalletItem;
import com.worldline.in.constant.Param;
import com.worldline.in.ipg.PaymentStandard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static com.payz24.utils.Constants.TRAVEX_SOFT_UTILITIES_BASE_URL;

/**
 * Created by mahesh on 3/15/2018.
 */

public class WalletFragment extends BaseFragment implements View.OnClickListener, HttpReqResCallBack {


    private LinearLayout llAddMoney;
    private TextView tvAddMoney, tvPromotionalBalance, tvNonPromotionalBalance;
    private NetworkDetection networkDetection;
    private BottomSheetDialog dialog;
    private TextDrawable.IBuilder mDrawableBuilder;
    private ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
    private EditText etMoney;
    private String text = "";
    private int money = 0;
    private String orderId = "";
    private String transactionRefNo = "";
    private String statusDescription = "";
    private String transactionAmount = "";
    private String selectedDate;
    private String walletBalance = "0";
    TextView nottrans;

    ListView listView;
    private ArrayList<WalletItem> feedItems;
    private WalletAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_wallet_frament, container, false);
        String userId = PreferenceConnector.readString(getActivity(), getString(R.string.user_id), "");

        initializeUi(view);
        initializeListeners();
        getCurrentDate();
        showProgressBar();
        serviceCallToGetProfileDetails();
        fetchSales(TRAVEX_SOFT_UTILITIES_BASE_URL+"/rest/fetchwallettransactions?uid="+userId+"&type=3");

        return view;
    }

    private void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat selectedDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        selectedDate = selectedDateFormat.format(calendar.getTime());
    }

    private void initializeUi(View view) {
        llAddMoney = view.findViewById(R.id.llAddMoney);
        tvAddMoney = view.findViewById(R.id.tvAddMoney);
        nottrans = view.findViewById(R.id.nottrans);
        tvPromotionalBalance = view.findViewById(R.id.tvPromotionalBalance);
        tvNonPromotionalBalance = view.findViewById(R.id.tvNonPromotionalBalance);
        listView=view.findViewById(R.id.transactionList);
        feedItems = new ArrayList<WalletItem>();
        listAdapter = new WalletAdapter(getActivity(), feedItems,this);
        registerForContextMenu(listView);
        listView.setAdapter(listAdapter);
        networkDetection = new NetworkDetection();
        mDrawableBuilder = TextDrawable.builder().beginConfig().width(100).height(100).fontSize(22).endConfig().round();
        tvPromotionalBalance.setText(getString(R.string.Rs) + " " + "1000");
        tvNonPromotionalBalance.setText(getString(R.string.Rs) + " " + "0");
    }

    private void initializeListeners() {
        llAddMoney.setOnClickListener(this);
        tvAddMoney.setOnClickListener(this);
    }

    private void serviceCallToGetProfileDetails() {
        if (networkDetection.isWifiAvailable(getActivity()) || networkDetection.isMobileNetworkAvailable(getActivity())) {
            String userId = PreferenceConnector.readString(getActivity(), getString(R.string.user_id), "");
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.PROFILE_PARAM_USER_ID, userId);

            ProfileHttpClient profileHttpClient = new ProfileHttpClient(getActivity());
            profileHttpClient.callBack = this;
            profileHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            closeProgressbar();
            Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llAddMoney:
                bottomSheetAddMoney();
                break;
            case R.id.tvAddMoney:
                bottomSheetAddMoney();
                break;
            case R.id.ivThousand:
                text = etMoney.getText().toString();
                if (!text.isEmpty())
                    money = Integer.parseInt(text);
                money = money + 1000;
                etMoney.setText(String.valueOf(money));
                etMoney.setSelection(String.valueOf(money).length());
                break;
            case R.id.ivFiveHundred:
                text = etMoney.getText().toString();
                if (!text.isEmpty())
                    money = Integer.parseInt(text);
                money = money + 500;
                etMoney.setText(String.valueOf(money));
                etMoney.setSelection(String.valueOf(money).length());
                break;
            case R.id.ivHundred:
                text = etMoney.getText().toString();
                if (!text.isEmpty())
                    money = Integer.parseInt(text);
                money = money + 100;
                etMoney.setText(String.valueOf(money));
                etMoney.setSelection(String.valueOf(money).length());
                break;
            case R.id.tvContinue:
                text = etMoney.getText().toString();
                if (!text.isEmpty()) {
                    paymentGateway(text);
                }
                break;
            case R.id.ivClose:
                if (dialog != null)
                    dialog.dismiss();
                    money=0;
                break;
            default:
                break;
        }
    }

    private void paymentGateway(String text) {
        Double totalPayableAmount = 0.0;
        transactionAmount = text;
        totalPayableAmount = Double.parseDouble(transactionAmount) * 100.0;

        Intent intent = new Intent(getActivity(), PaymentStandard.class);
        intent.putExtra(Param.ORDER_ID, "" + getRandomOrderId());
        intent.putExtra(Param.TRANSACTION_AMOUNT, String.valueOf(totalPayableAmount.intValue()));
        intent.putExtra(Param.TRANSACTION_CURRENCY, "INR");
        intent.putExtra(Param.TRANSACTION_DESCRIPTION, "Sock money");
        intent.putExtra(Param.TRANSACTION_TYPE, "S");
        intent.putExtra(Param.KEY, "6375b97b954b37f956966977e5753ee6");//*Optional
        intent.putExtra(Param.MID, "WL0000000027698");//*Optional
        startActivityForResult(intent, 1);
    }

    public static int getRandomOrderId() {
        Random random = new Random();
        int randomOrderId = random.nextInt();
        if (randomOrderId < 0) {
            randomOrderId = randomOrderId * -1;
        }
        return randomOrderId;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            closeProgressbar();
            if (resultCode == RESULT_OK) {
                orderId = data.getStringExtra(Param.ORDER_ID);
                transactionRefNo = data.getStringExtra(Param.TRANSACTION_REFERENCE_NUMBER);
                String rrn = data.getStringExtra(Param.RRN);
                String statusCode = data.getStringExtra(Param.STATUS_CODE);
                statusDescription = data.getStringExtra(Param.STATUS_DESCRIPTION);
                String transactionAmount = data.getStringExtra(Param.TRANSACTION_AMOUNT);
                String requestDate = data.getStringExtra(Param.TRANSACTION_REQUEST_DATE);
                String authNStatus = data.getStringExtra(Param.AUTH_N_STATUS);
                String authZstatus = data.getStringExtra(Param.AUTH_Z_STATUS);
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
                    showProgressBar();
                    serviceCallForAddingWalletBalance();
                } else {
                    Toast.makeText(getActivity(), statusDescription, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void bottomSheetAddMoney() {
        Context context = getActivity();
        View view = getLayoutInflater().inflate(R.layout.layout_add_money, null);
        assert context != null;

        TextDrawable thousandDrawable = mDrawableBuilder.build("+" + "" + String.valueOf(1000), colorGenerator.getColor("+" + "" + String.valueOf(1000)));
        TextDrawable fiveHundredDrawable = mDrawableBuilder.build("+" + "" + String.valueOf(500), colorGenerator.getColor("+" + "" + String.valueOf(500)));
        TextDrawable hundredDrawable = mDrawableBuilder.build("+" + "" + String.valueOf(100), colorGenerator.getColor("+" + "" + String.valueOf(100)));

        etMoney = view.findViewById(R.id.etMoney);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        ImageView ivThousand = view.findViewById(R.id.ivThousand);
        ImageView ivFiveHundred = view.findViewById(R.id.ivFiveHundred);
        ImageView ivHundred = view.findViewById(R.id.ivHundred);
        TextView tvContinue = view.findViewById(R.id.tvContinue);

        ivClose.setOnClickListener(this);
        ivThousand.setOnClickListener(this);
        ivFiveHundred.setOnClickListener(this);
        ivHundred.setOnClickListener(this);
        tvContinue.setOnClickListener(this);

        ivThousand.setImageDrawable(thousandDrawable);
        ivFiveHundred.setImageDrawable(fiveHundredDrawable);
        ivHundred.setImageDrawable(hundredDrawable);
        dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.show();

        View parent = (View) view.getParent();
        parent.setFitsSystemWindows(true);

        BottomSheetBehavior<View> mBehavior = BottomSheetBehavior.from(parent);
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void serviceCallForAddingWalletBalance() {
        if (networkDetection.isMobileNetworkAvailable(getActivity()) || networkDetection.isWifiAvailable(getActivity())) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.WALLET_BALANCE_PARAM_ORDER_ID, orderId);
            mapOfRequestParams.put(Constants.WALLET_BALANCE_PARAM_TRANSACTION_AMOUNT, transactionAmount);
            mapOfRequestParams.put(Constants.WALLET_BALANCE_PARAM_REFERENCE_NUMBER, transactionRefNo);
            mapOfRequestParams.put(Constants.WALLET_BALANCE_PARAM_STATUS_CODE, "S");
            mapOfRequestParams.put(Constants.WALLET_BALANCE_PARAM_TRANSACTION_REQUEST_DATE, selectedDate);
            mapOfRequestParams.put(Constants.WALLET_BALANCE_PARAM_USER_ID, PreferenceConnector.readString(getActivity(), getString(R.string.user_id), ""));

            AddWalletBalanceHttpClient addWalletBalanceHttpClient = new AddWalletBalanceHttpClient(getActivity());
            addWalletBalanceHttpClient.callBack = this;
            addWalletBalanceHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            closeProgressbar();
            Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_ADD_MONEY:
                if (jsonResponse != null) {
                    WalletAddMoney walletAddMoney = new Gson().fromJson(jsonResponse, WalletAddMoney.class);
                    if (walletAddMoney != null) {
                        String status = walletAddMoney.getStatus();
                        String message = walletAddMoney.getResult().getMsg();
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            tvPromotionalBalance.setText(getString(R.string.Rs) + " " + transactionAmount);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            if (dialog != null)
                                dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                serviceCallToGetProfileDetails();
                break;
            case Constants.SERVICE_PROFILE:
                if (jsonResponse != null) {
                    ProfileResponse profileResponse = new Gson().fromJson(jsonResponse, ProfileResponse.class);
                    if (profileResponse != null) {
                        if (profileResponse.getResult().getWalletAmt() != null)
                            walletBalance = profileResponse.getResult().getWalletAmt();
                        tvNonPromotionalBalance.setText(walletBalance);
                    }
                }
                closeProgressbar();
                break;
            default:
                break;
        }
    }



    public void fetchSales(String url)
    {
        // We first check for cached request
       // Log.e("url",url);
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null)
        {
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseCommentFeeds(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else {

            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    url, (String)null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d("Error", "Response: " + response.toString());
                    if (response != null) {
                        parseCommentFeeds(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

    }
    private void parseCommentFeeds(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("result");

            if(feedArray==null||feedArray.length()==0)
            {
                nottrans.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }else
            {
                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);

                    WalletItem item = new WalletItem();

                    item.setDescription(feedObj.getString("description"));
                    item.setTransactionamount(feedObj.getString("transactionamount"));
                    item.setDate(feedObj.getString("datetime"));
                    feedItems.add(item);
                    listAdapter.notifyDataSetChanged();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
