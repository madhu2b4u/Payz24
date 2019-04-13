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
import com.payz24.activities.ViewPlansActivity;
import com.payz24.application.AppController;
import com.payz24.fragment.CreditCardFragment;
import com.payz24.fragment.DebitCardFragment;
import com.payz24.fragment.NetBankingFragment;
import com.payz24.http.MobileRechargeHttpClient;
import com.payz24.http.OperatorsListHttpClient;
import com.payz24.http.RechargeHttpClinet;
import com.payz24.interfaces.ContactsCallBack;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.interfaces.RechargeAmountCallBack;
import com.payz24.responseModels.mobileOperators.MobileOperators;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by mahesh on 1/10/2018.
 */

public class NewMobileRechargeActivity extends BaseActivity implements HttpReqResCallBack, RadioGroup.OnCheckedChangeListener, View.OnClickListener, ContactsCallBack, RechargeAmountCallBack {

    private Toolbar toolbar;
    private RadioButton rbPrepaid;
    private RadioGroup radioGroup;
    private View viewSeparator;
    private TextView tvOperatorName, tvSelectCircle;
    private ImageView ivChange;
    private TextView tvDebitCard, tvCreditCard, tvNetBanking, tvContactName, tvRecharge, tvViewPlans;
    private EditText etAmount, etContactNumber;
    private LinearLayout llOperatorName, llSelectCircleName;
    private Fragment fragment;
    private NetworkDetection networkDetection;
    private int mobileId = 1;
    private LinkedList<MobileOperators> listOfMobileOperators;
    private LinkedList<Integer> listOfMobileOperatorImages;
    private LinkedHashMap<String, String> mapOfContactNameAndNumber;
    private LinkedList<String> listOfCircles;
    private BottomSheetDialog dialog;
    private LinkedHashMap<String, Integer> mapOfServiceOperatorNameAmount;
    private LinkedHashMap<String, Integer> mapOfServiceOperatorNameId;
    private int maxAmount = 0;
    private boolean isPrepaid = true;
    String serviceProviderId;
    String selectedNetwork;
    String mobile="1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mobile_recharge);
        getValuesFromIntent();
        initializeUi();
        initializeListeners();
        prepareListOfCircles();
        enableActionBar(true);
    }

    private void getValuesFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mobileId = intent.getIntExtra(getString(R.string.mobile), 1);
        }
    }

    private void initializeUi() {
        toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.mobile_recharge));
        setSupportActionBar(toolbar);

        radioGroup = findViewById(R.id.radioGroup);
        rbPrepaid = findViewById(R.id.rbPrepaid);
        tvOperatorName = findViewById(R.id.tvOperatorName);
        tvSelectCircle = findViewById(R.id.tvSelectCircle);
        ivChange = findViewById(R.id.ivChange);
        tvCreditCard = findViewById(R.id.tvCreditCard);
        tvDebitCard = findViewById(R.id.tvDebitCard);
        tvNetBanking = findViewById(R.id.tvNetBanking);
        tvViewPlans = findViewById(R.id.tvViewPlans);
        tvContactName = findViewById(R.id.tvContactName);
        etContactNumber = findViewById(R.id.etContactNumber);
        tvRecharge = findViewById(R.id.tvRecharge);
        etAmount = findViewById(R.id.etAmount);
        llOperatorName = findViewById(R.id.llOperatorName);
        llSelectCircleName = findViewById(R.id.llSelectCircleName);
        viewSeparator = findViewById(R.id.viewSeparator);
        networkDetection = new NetworkDetection();
        AppController.getInstance().setMobileRechargeContext(this);
        listOfCircles = new LinkedList<>();
        listOfMobileOperatorImages = new LinkedList<>();
        mapOfContactNameAndNumber = new LinkedHashMap<>();
    }

    private void initializeListeners() {
        radioGroup.setOnCheckedChangeListener(this);
        llOperatorName.setOnClickListener(this);
        ivChange.setOnClickListener(this);
        tvCreditCard.setOnClickListener(this);
        tvDebitCard.setOnClickListener(this);
        tvNetBanking.setOnClickListener(this);
        tvRecharge.setOnClickListener(this);
        tvViewPlans.setOnClickListener(this);
        llSelectCircleName.setOnClickListener(this);
        rbPrepaid.setChecked(true);
        tvDebitCard.performClick();
    }

    private void prepareListOfCircles() {
        listOfCircles.add(getString(R.string.andhra_pradesh_telangana));
        listOfCircles.add(getString(R.string.assam));
        listOfCircles.add(getString(R.string.bihar_jharkhand));
        listOfCircles.add(getString(R.string.chennai));
        listOfCircles.add(getString(R.string.delhi_ncr));
        listOfCircles.add(getString(R.string.gujarat));
        listOfCircles.add(getString(R.string.haryana));
        listOfCircles.add(getString(R.string.himachal_pradesh));
        listOfCircles.add(getString(R.string.jammu_kashmir));
        listOfCircles.add(getString(R.string.karnataka));
        listOfCircles.add(getString(R.string.kerala));
        listOfCircles.add(getString(R.string.kolkata));
        listOfCircles.add(getString(R.string.madhya_pradesh_chhattisgarh));
        listOfCircles.add(getString(R.string.maharashtra_goa));
        listOfCircles.add(getString(R.string.mumbai));
        listOfCircles.add(getString(R.string.north_east));
        listOfCircles.add(getString(R.string.orissa));
        listOfCircles.add(getString(R.string.punjab));
        listOfCircles.add(getString(R.string.rajasthan));
        listOfCircles.add(getString(R.string.tamilnadu));
        listOfCircles.add(getString(R.string.uttar_pradesh_east));
        listOfCircles.add(getString(R.string.uttar_pradesh_west_uttarakhand));
        listOfCircles.add(getString(R.string.west_bengal));
    }

    private void serviceCallForOperatorsList(String isPrepaid) {
        if (networkDetection.isMobileNetworkAvailable(this) || networkDetection.isWifiAvailable(this)) {
            showProgressBar();
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.UTILITY_SERVICE_ID, String.valueOf(mobileId));
            mapOfRequestParams.put(Constants.MOBILE_RECHARGE_PARAM_IS_PREPAID, String.valueOf(isPrepaid));
            OperatorsListHttpClient operatorsListHttpClient = new OperatorsListHttpClient(this);
            operatorsListHttpClient.callBack = this;
            operatorsListHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void serviceCallForRecharge() {
        String contactNumber = etContactNumber.getText().toString();
        String rechargeAmount = etAmount.getText().toString();
        String operatorName = tvOperatorName.getText().toString();
        int id = mapOfServiceOperatorNameId.get(operatorName);
        String selectedUserId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        String dates=dateFormat.format(date);

        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.rechargeNumber, contactNumber);
        mapOfRequestParams.put(Constants.rechargeAmount, rechargeAmount);
        mapOfRequestParams.put(Constants.servicetype, "1");
        mapOfRequestParams.put(Constants.OperatorName, String.valueOf(id));
        mapOfRequestParams.put(Constants.OperatorId, String.valueOf(id));
        mapOfRequestParams.put(Constants.typeofrc, "prepaid");
        mapOfRequestParams.put(Constants.complete_j, "");
        mapOfRequestParams.put(Constants.PgMeTrnRefNo, "NTRC_5ab8f8296681c");
        mapOfRequestParams.put(Constants.TrnAmt, rechargeAmount);
        mapOfRequestParams.put(Constants.StatusCode, "S");
        mapOfRequestParams.put(Constants.StatusDesc, "");
        mapOfRequestParams.put(Constants.TrnReqDate, dates);
        mapOfRequestParams.put(Constants.ResponseCode, "");
        mapOfRequestParams.put(Constants.Rrn, "");
        mapOfRequestParams.put(Constants.AuthZCode, "");
        mapOfRequestParams.put(Constants.Desc, "Desc");
        mapOfRequestParams.put(Constants.userid, selectedUserId);
        mapOfRequestParams.put(Constants.usertype, "3");
        mapOfRequestParams.put(Constants.usewallet, "1");
        mapOfRequestParams.put(Constants.wallet_amount, rechargeAmount);
        mapOfRequestParams.put(Constants.rem_amt, "0");


        RechargeHttpClinet mobileRechargeHttpClient = new RechargeHttpClinet(this);
        mobileRechargeHttpClient.callBack = this;
        mobileRechargeHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_UTILITIES_OPERATORS_LIST:
                try {
                    JSONArray jsonArray = new JSONArray(jsonResponse);
                    listOfMobileOperators = new LinkedList<>();
                    mapOfServiceOperatorNameAmount = new LinkedHashMap<>();
                    mapOfServiceOperatorNameId = new LinkedHashMap<>();
                    for (int index = 0; index < jsonArray.length(); index++) {
                        String response = jsonArray.getString(index);
                        MobileOperators mobileOperators = new Gson().fromJson(response, MobileOperators.class);
                        if (mobileOperators != null) {
                            int maxAmount = (int) mobileOperators.getMaxRechargeAmountPerDay();
                            String serviceOperatorName = mobileOperators.getOperator();
                            if (!serviceOperatorName.equalsIgnoreCase("T24 (Flexi)")) {
                                if (!serviceOperatorName.equalsIgnoreCase("T24 (Special)")) {
                                    if (!serviceOperatorName.equalsIgnoreCase("Tata Indicom")) {
                                        mapOfServiceOperatorNameAmount.put(serviceOperatorName, maxAmount);
                                        mapOfServiceOperatorNameId.put(serviceOperatorName, mobileOperators.getId());
                                        listOfMobileOperators.add(mobileOperators);
                                        if (serviceOperatorName.equalsIgnoreCase(getString(R.string.idea))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_idea);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.airtel))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_airtel);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.aircel))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_aircel);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.mts))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_mts);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.reliance_gsm_cdma))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_reliance);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.tata_docomo_flexi))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_docomo);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.tata_docomo_special))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_docomo);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.bsnl_top_up))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_bsnl);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.bsnl_validity_special))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_bsnl);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.vodafone))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_vodafone);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.uninor))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_uninor);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.mtnl_top_up))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_mtnl);
                                        } else if (serviceOperatorName.equalsIgnoreCase(getString(R.string.mtnl_validity_special))) {
                                            listOfMobileOperatorImages.add(R.drawable.ic_mtnl);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                closeProgressbar();
                break;

            case Constants.SERVICE_MOBILE_RECHARGES:
                //{"message":"Insufficient Balance.  ","errorCode":"ERROR051"}
                try {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    String message = jsonObject.getString("message");
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (rbPrepaid.isChecked()) {
            isPrepaid = true;
            tvViewPlans.setVisibility(View.VISIBLE);
            llSelectCircleName.setVisibility(View.VISIBLE);
            viewSeparator.setVisibility(View.VISIBLE);
            serviceCallForOperatorsList("true");
        } else {
            isPrepaid = false;
            tvViewPlans.setVisibility(View.GONE);
            llSelectCircleName.setVisibility(View.GONE);
            viewSeparator.setVisibility(View.GONE);
            serviceCallForOperatorsList("false");
        }
    }

    public void showBottomSheetDialog(LinkedList<MobileOperators> listOfMobileOperators) {
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_dialog, null);
        LinearLayout llContainer = view.findViewById(R.id.llContainer);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        for (int index = 0; index < listOfMobileOperators.size(); index++) {
            View customView = getLayoutInflater().inflate(R.layout.layout_operator_item, null);
            final TextView tvOperatorName = customView.findViewById(R.id.tvOperatorName);
            ImageView ivOperatorImage = customView.findViewById(R.id.ivOperatorImage);
            LinearLayout llOperator = customView.findViewById(R.id.llOperator);
            llOperator.setOnClickListener(this);
            String operatorName = listOfMobileOperators.get(index).getOperator();
            tvOperatorName.setText(operatorName);
            ivOperatorImage.setImageResource(listOfMobileOperatorImages.get(index));
            llOperator.setTag(operatorName);
            llContainer.addView(customView);
        }
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        ivClose.setOnClickListener(this);
        dialog.show();
    }

    private void showBottomSheetCirclesDialog(LinkedList<String> listOfCircles) {
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_dialog, null);
        LinearLayout llContainer = view.findViewById(R.id.llContainer);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        tvTitle.setText(getString(R.string.circles));
        for (int index = 0; index < listOfCircles.size(); index++) {
            View customView = getLayoutInflater().inflate(R.layout.layout_operator_item, null);
            final TextView tvOperatorName = customView.findViewById(R.id.tvOperatorName);
            ImageView ivOperatorImage = customView.findViewById(R.id.ivOperatorImage);
            ivOperatorImage.setVisibility(View.GONE);
            LinearLayout llOperator = customView.findViewById(R.id.llOperator);
            llOperator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String circleName = (String) view.getTag();
                    tvSelectCircle.setText(circleName);
                    if (dialog != null)
                        dialog.dismiss();
                }
            });
            String circleName = listOfCircles.get(index);
            tvOperatorName.setText(circleName);
            ivOperatorImage.setImageResource(R.mipmap.ic_launcher_round);
            llOperator.setTag(circleName);
            llContainer.addView(customView);
        }
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        ivClose.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llOperatorName:
                networkProvider();
             /*   if (listOfMobileOperators.size() != 0) {
                    showBottomSheetDialog(listOfMobileOperators);
                }*/
                break;
            case R.id.llOperator:
                String operatorName = (String) view.getTag();
                tvOperatorName.setText(operatorName);
                if (dialog != null)
                    dialog.dismiss();
                break;
            case R.id.ivClose:
                if (dialog != null)
                    dialog.dismiss();
                break;
            case R.id.llSelectCircleName:
                if (listOfCircles.size() != 0) {
                    showBottomSheetCirclesDialog(listOfCircles);
                }
                break;
            case R.id.ivChange:
                if (AppController.getInstance().getMapOfContactNameAndNumber() != null && AppController.getInstance().getMapOfContactNameAndNumber().size() != 0) {
                    showProgressBar();
                    goToContacts();
                } else {
                    showProgressBar();
                    // Since reading contacts takes more time, let's run it on a separate thread.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getContactList();
                        }
                    }).start();
                }
                break;

            case R.id.tvCreditCard:
                tvCreditCard.setBackgroundResource(R.color.white);
                tvCreditCard.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvDebitCard.setBackgroundResource(R.color.medium_light_grey);
                tvDebitCard.setTextColor(getResources().getColor(R.color.black));
                tvNetBanking.setBackgroundResource(R.color.medium_light_grey);
                tvNetBanking.setTextColor(getResources().getColor(R.color.black));

                fragment = new CreditCardFragment();
                FragmentManager creditCardFragmentManager = getSupportFragmentManager();
                creditCardFragmentManager.executePendingTransactions();
                FragmentTransaction creditCardFragmentTransaction = creditCardFragmentManager.beginTransaction();
                creditCardFragmentTransaction.replace(R.id.detailsFragment, fragment);
                creditCardFragmentTransaction.commitAllowingStateLoss();

                break;
            case R.id.tvDebitCard:
                tvDebitCard.setBackgroundResource(R.color.white);
                tvDebitCard.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvCreditCard.setBackgroundResource(R.color.medium_light_grey);
                tvCreditCard.setTextColor(getResources().getColor(R.color.black));
                tvNetBanking.setBackgroundResource(R.color.medium_light_grey);
                tvNetBanking.setTextColor(getResources().getColor(R.color.black));

                fragment = new DebitCardFragment();
                FragmentManager debitCardFragmentManager = getSupportFragmentManager();
                debitCardFragmentManager.executePendingTransactions();
                FragmentTransaction debitCardFragmentTransaction = debitCardFragmentManager.beginTransaction();
                debitCardFragmentTransaction.replace(R.id.detailsFragment, fragment);
                debitCardFragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.tvNetBanking:
                tvNetBanking.setBackgroundResource(R.color.white);
                tvNetBanking.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvDebitCard.setBackgroundResource(R.color.medium_light_grey);
                tvDebitCard.setTextColor(getResources().getColor(R.color.black));
                tvCreditCard.setBackgroundResource(R.color.medium_light_grey);
                tvCreditCard.setTextColor(getResources().getColor(R.color.black));

                fragment = new NetBankingFragment();
                FragmentManager netBankingFragmentManager = getSupportFragmentManager();
                netBankingFragmentManager.executePendingTransactions();
                FragmentTransaction netBankingFragmentTransaction = netBankingFragmentManager.beginTransaction();
                netBankingFragmentTransaction.replace(R.id.detailsFragment, fragment);
                netBankingFragmentTransaction.commitAllowingStateLoss();

                break;

            case R.id.tvRecharge:
                if (isPrepaid) {
                    String serviceProviderName = tvOperatorName.getText().toString();
                    String enteredAmount = etAmount.getText().toString();
                    String contactNumber = etContactNumber.getText().toString();
                    String circleName = tvSelectCircle.getText().toString();

                    if (!serviceProviderName.isEmpty()) {
                        if (!circleName.isEmpty()) {

                            if (!contactNumber.isEmpty())
                            {

                                if (isValidMobile(etContactNumber.getText().toString().trim()))
                                {

                                    if (!enteredAmount.isEmpty())
                                    {
                                        Intent viewPlansIntent = new Intent(this, RechargeOverview.class);
                                        viewPlansIntent.putExtra("serviceProviderName", serviceProviderName);
                                        viewPlansIntent.putExtra("enteredAmount", enteredAmount);
                                        viewPlansIntent.putExtra("serviceType", mobile);
                                        viewPlansIntent.putExtra("serviceProviderId", serviceProviderId);
                                        viewPlansIntent.putExtra("contactNumber", contactNumber);
                                        startActivity(viewPlansIntent);
                                    }
                                    else {
                                        Toast.makeText(this, "Please enter amount to recharge", Toast.LENGTH_SHORT).show();
                                    }
                                }else
                                {
                                    Toast.makeText(this, "Please enter valid number", Toast.LENGTH_SHORT).show();

                                }


                            }else {
                                Toast.makeText(this, "Please enter your contact number", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, getString(R.string.please_select_circle), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.please_select_service_operator), Toast.LENGTH_SHORT).show();
                    }



                }
                break;

            case R.id.tvViewPlans:
                String circleName = tvSelectCircle.getText().toString();
                String serviceProviderNames = tvOperatorName.getText().toString();
                if (!serviceProviderNames.isEmpty()) {
                    if (!circleName.isEmpty()) {
                        goToViewPlans(serviceProviderNames, circleName);
                    } else {
                        Toast.makeText(this, getString(R.string.please_select_circle), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.please_select_service_operator), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }

    private void goToViewPlans(String serviceProviderName, String circleName) {
        if (!serviceProviderName.isEmpty() && !circleName.isEmpty()) {
            Intent viewPlansIntent = new Intent(this, ViewPlansActivity.class);
            viewPlansIntent.putExtra(getString(R.string.circle_name), circleName);
            viewPlansIntent.putExtra(getString(R.string.service_provider_name), serviceProviderName);
            startActivity(viewPlansIntent);
        }
    }


    private void getContactList() {
        mapOfContactNameAndNumber = new LinkedHashMap<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE NOCASE ASC");
        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            while (cursor != null && cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    List<String> listOfPhoneNumbers = new ArrayList<>();
                    while (phoneCursor.moveToNext()) {
                        String phoneNo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (mapOfContactNameAndNumber.containsKey(name)) {
                            listOfPhoneNumbers = new ArrayList<>();
                            List<String> listOfTemp = Arrays.asList(mapOfContactNameAndNumber.get(name).split("\\s*,\\s*"));
                            listOfPhoneNumbers.addAll(listOfTemp);
                        }
                        if (phoneNo.startsWith("+91")) {
                            phoneNo = phoneNo.replace("+91", "");
                            phoneNo = phoneNo.replace(" ", "");
                            phoneNo = phoneNo.replace("-", "");
                            phoneNo = phoneNo.replace(" ", "");
                        }
                        phoneNo = phoneNo.replace("-", "");
                        phoneNo = phoneNo.replace(" ", "");
                        if (!listOfPhoneNumbers.contains(phoneNo))
                            listOfPhoneNumbers.add(phoneNo);
                        if (!name.equalsIgnoreCase("identified as spam")) {
                            mapOfContactNameAndNumber.put(name, TextUtils.join(",", listOfPhoneNumbers));
                        }
                    }
                    phoneCursor.close();
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        goToContacts();
    }

    private void goToContacts() {
        AppController.getInstance().setMapOfContactNameAndNumber(mapOfContactNameAndNumber);
        Intent contactsIntent = new Intent(this, ContactsActivity.class);
        startActivity(contactsIntent);
        closeProgressbar();
    }

    @Override
    public void selectedContacts(String contactName, String contactNumber) {
        if (contactName != null && !contactName.isEmpty()) {
            tvContactName.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) etContactNumber.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            tvContactName.setText(contactName);
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) etContactNumber.getLayoutParams();
            params.setMargins(0, 30, 0, 0);
            tvContactName.setVisibility(View.GONE);
        }
        if (contactNumber != null && !contactNumber.isEmpty()) {
            etContactNumber.setText(contactNumber);
            etContactNumber.setVisibility(View.VISIBLE);
        } else {
            etContactNumber.setVisibility(View.GONE);
        }
    }

    @Override
    public void rechargeAmount(String rechargeAmount) {
        if (rechargeAmount != null && !rechargeAmount.isEmpty()) {
            etAmount.setText(rechargeAmount);
            etAmount.setSelection(rechargeAmount.length());
        }
    }



    public void networkProvider()
    {
        List<String> item = new ArrayList<String>();
        item.add("AIRTEL");
        item.add("AIRCEL");
        item.add("BSNL");
        item.add("DOCOMO");
        item.add("IDEA");
        item.add("JIO");
        item.add("MTNL");
        item.add("MTS");
        item.add("RELIANCE");
        item.add("TATA INDICOM");
        item.add("T24");
        item.add("VIDEOCON");
        item.add("VODAFONE");
        item.add("UNINOR");


        final CharSequence[] networks = item.toArray(new String[item.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setItems(networks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selectedNetwork = networks[item].toString();
                if(selectedNetwork.equals("BSNL"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    List<String> serviceNetworkProvider = new ArrayList<String>();
                    serviceNetworkProvider.add("TOPUP");
                    serviceNetworkProvider.add("SPECIAL");
                    serviceProvider(serviceNetworkProvider);
                }
                if(selectedNetwork.equals("DOCOMO"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    List<String> serviceNetworkProvider = new ArrayList<String>();
                    serviceNetworkProvider.add("TOPUP");
                    serviceNetworkProvider.add("SPECIAL");
                    serviceProvider(serviceNetworkProvider);
                }if(selectedNetwork.equals("UNINOR"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    List<String> serviceNetworkProvider = new ArrayList<String>();
                    serviceNetworkProvider.add("TOPUP");
                    serviceNetworkProvider.add("SPECIAL");
                    serviceProvider(serviceNetworkProvider);
                }if(selectedNetwork.equals("VIDEOCON"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    List<String> serviceNetworkProvider = new ArrayList<String>();
                    serviceNetworkProvider.add("TOPUP");
                    serviceNetworkProvider.add("SPECIAL");
                    serviceProvider(serviceNetworkProvider);
                }if(selectedNetwork.equals("JIO"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    List<String> serviceNetworkProvider = new ArrayList<String>();
                    serviceNetworkProvider.add("TOPUP");
                    serviceNetworkProvider.add("SPECIAL");
                    serviceProvider(serviceNetworkProvider);
                }if(selectedNetwork.equals("T24"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    List<String> serviceNetworkProvider = new ArrayList<String>();
                    serviceNetworkProvider.add("TOPUP");
                    serviceNetworkProvider.add("SPECIAL");
                    serviceProvider(serviceNetworkProvider);
                }if(selectedNetwork.equals("MTNL"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    List<String> serviceNetworkProvider = new ArrayList<String>();
                    serviceNetworkProvider.add("DELHI");
                    serviceNetworkProvider.add("MUMBAI");
                    serviceProvider(serviceNetworkProvider);
                }if(selectedNetwork.equals("RELIANCE"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    List<String> serviceNetworkProvider = new ArrayList<String>();
                    serviceNetworkProvider.add("CDMA");
                    serviceNetworkProvider.add("GSM");
                    serviceProvider(serviceNetworkProvider);
                }
                if(selectedNetwork.equals("IDEA"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="MI";
                    Log.e("serviceProviderId",serviceProviderId);

                }  if(selectedNetwork.equals("AIRCEL"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="MAC";
                    Log.e("serviceProviderId",serviceProviderId);

                }if(selectedNetwork.equals("AIRTEL"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="MAT";
                    Log.e("serviceProviderId",serviceProviderId);
                }if(selectedNetwork.equals("MTS"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="MM";
                    Log.e("serviceProviderId",serviceProviderId);
                }if(selectedNetwork.equals("VODAFONE"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="MV";
                    Log.e("serviceProviderId",serviceProviderId);
                }if(selectedNetwork.equals("TATA INDICOM"))
                {
                    tvOperatorName.setText(selectedNetwork);
                    serviceProviderId="MTI";
                    Log.e("serviceProviderId",serviceProviderId);
                }

            }
        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

    public void serviceProvider(List<String> serviceNetworkProvider)
    {
        final CharSequence[] networks = serviceNetworkProvider.toArray(new String[serviceNetworkProvider.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setItems(networks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = networks[item].toString();
                if(selectedText.equals("TOPUP"))
                {
                    if (selectedNetwork.equals("BSNL"))
                    {
                        serviceProviderId="MBT";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("DOCOMO"))
                    {
                        serviceProviderId="MDT";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("UNINOR"))
                    {
                        serviceProviderId="MUT";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("VIDEOCON"))
                    {
                        serviceProviderId="MVDT";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("T24"))
                    {
                        serviceProviderId="MTT";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("JIO"))
                    {
                        serviceProviderId="MJIOT";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }

                }if(selectedText.equals("SPECIAL"))
                {
                    if (selectedNetwork.equals("BSNL"))
                    {
                        serviceProviderId="MBS";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("DOCOMO"))
                    {
                        serviceProviderId="MDS";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("UNINOR"))
                    {
                        serviceProviderId="MUS";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("VIDEOCON"))
                    {
                        serviceProviderId="MVDS";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("T24"))
                    {
                        serviceProviderId="MTTS";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }else if (selectedNetwork.equals("JIO"))
                    {
                        serviceProviderId="MJIOS";
                        tvOperatorName.setText(selectedNetwork);
                        Log.e("serviceProviderId",serviceProviderId);
                    }
                }

                if(selectedText.equals("DELHI"))
                {
                    serviceProviderId="MMD";
                    tvOperatorName.setText(selectedNetwork);
                    Log.e("serviceProviderId",serviceProviderId);
                }if(selectedText.equals("MUMBAI"))
                {
                    serviceProviderId="MMM";
                    tvOperatorName.setText(selectedNetwork);
                    Log.e("serviceProviderId",serviceProviderId);
                }if(selectedText.equals("CDMA"))
                {
                    serviceProviderId="MRC";
                    tvOperatorName.setText(selectedNetwork);
                    Log.e("serviceProviderId",serviceProviderId);
                }if(selectedText.equals("GSM"))
                {
                    serviceProviderId="MRG";
                    tvOperatorName.setText(selectedNetwork);
                    Log.e("serviceProviderId",serviceProviderId);
                }

            }


        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 10 || phone.length() > 10) {
                // if(phone.length() != 10) {
                check = false;
                // etMobileNumber.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }





}