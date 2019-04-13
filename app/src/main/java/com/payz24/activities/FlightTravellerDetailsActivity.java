package com.payz24.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.http.DomesticFlightBookingHttpClient;
import com.payz24.http.InternationalOneWayHttpClient;
import com.payz24.http.ProfileHttpClient;
import com.payz24.http.SmsHttpClient;
import com.payz24.interfaces.AdultPersonalDetailsCallBack;
import com.payz24.interfaces.ChildrenPersonalDetailsCallBack;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.interfaces.InfantPersonalDetailsCallBack;
import com.payz24.responseModels.ProfileResponse.ProfileResponse;
import com.payz24.responseModels.domesticFlightBookingRequest.DomesticFlightBooking;
import com.payz24.responseModels.domesticFlightBookingRequest.Onward;
import com.payz24.responseModels.domesticFlightBookingRequest.Opricing;
import com.payz24.responseModels.domesticFlightBookingRequest.Return;
import com.payz24.responseModels.domesticFlightBookingRequest.Rpricing;
import com.payz24.responseModels.flightSearchResults.AvailRequest;
import com.payz24.responseModels.flightSearchResults.FlightSegment;
import com.payz24.responseModels.flightSearchResults.OriginDestinationOption;
import com.payz24.responseModels.internationalOneWayRequest.Fare;
import com.payz24.responseModels.internationalOneWayRequest.FareBrk;
import com.payz24.responseModels.internationalOneWayRequest.FlightData;
import com.payz24.responseModels.internationalOneWayRequest.InternationalOneWay;
import com.payz24.responseModels.internationalOneWayRequest.Leg;
import com.payz24.responseModels.internationalOneWayRequest.PostData;
import com.payz24.responseModels.internationalOneWayRequest.Rleg;
import com.payz24.responseModels.internationalOneWayRequest.SearchData;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;
import com.worldline.in.constant.Param;
import com.worldline.in.ipg.PaymentStandard;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mahesh on 2/10/2018.
 */

public class FlightTravellerDetailsActivity extends BaseActivity implements View.OnClickListener, AdultPersonalDetailsCallBack, ChildrenPersonalDetailsCallBack, InfantPersonalDetailsCallBack, HttpReqResCallBack, CompoundButton.OnCheckedChangeListener {

    private LinearLayout llAdult, llChild, llInfant;
    //private View childView, infantView;
    private TextInputLayout inputLayoutMobileNumber, inputLayoutEmail;
    private EditText etMobileNumber, etEmailAddress;
    private TextView tvTotalAmount, tvMakePayment, tvWalletBalance;
    private NetworkDetection networkDetection;
    private ImageView ivInfo;
    private CheckBox walletCheckBox;
    private RadioButton rbPersonal, rbBusiness;
    private String sourceFullName = "";
    private String destinationFullName = "";
    private String from = "";
    private double totalAdultBaseFare = 0;
    private double totalAdultTax = 0;
    private double totalChildrenBaseFare = 0;
    private double totalChildrenTax = 0;
    private double totalInfantBaseFare = 0;
    private double totalInfantTax = 0;
    private LinkedList<String> listOfKeys;
    private LinkedList<String> listOfValues;
    private int numberOfAdults = 0;
    private int numberOfChildren = 0;
    private int numberOfInfants = 0;

    private List<String> listOfAdultTitle;
    private List<String> listOfChildrenTitle;
    private List<String> listOfInfantTitle;

    private List<String> listOfAdultFirstNames;
    private List<String> listOfChildrenFirstNames;
    private List<String> listOfInfantFirstNames;

    private List<String> listOfAdultLastNames;
    private List<String> listOfChildrenLastNames;
    private List<String> listOfInfantLastNames;

    private List<String> listOfAdultAge;
    private List<String> listOfChildrenAge;
    private List<String> listOfInfantAge;

    private List<String> listOfAdultPassportNumber;
    private List<String> listOfChildrenPassportNumber;
    private List<String> listOfInfantPassportNumber;

    private List<String> listOfAdultVisaType;
    private List<String> listOfChildrenVisaType;
    private List<String> listOfInfantVisaType;

    private List<String> listOfAdultPassportExpiryDate;
    private List<String> listOfChildrenPassportExpiryDate;
    private List<String> listOfInfantPassportExpiryDate;
    private String sourceCountryName = "";
    private String destinationCountryName = "";
    private String mobileNumber = "";
    private String emailAddress = "";
    private String airwayName = "";
    private ArrayList<Leg> listOfLegs;
    private String numStops;
    private ArrayList<Rleg> listOfRLegs;
    private String numReturnStops = "";
    private List<com.payz24.responseModels.domesticFlightBookingRequest.Leg> listOfDomesticLegs;
    private double grandTotal;
    private List<com.payz24.responseModels.domesticFlightBookingRequest.Leg> listOfToLegs;
    private List<com.payz24.responseModels.domesticFlightBookingRequest.Leg> listOfFromLegs;
    private String toActualBaseFare = "";
    private String toTax = "";
    private String toSTax = "";
    private String toSCharge = "";
    private String toTDiscount = "";
    private String toTPartnerCommission = "";
    private String toTCharge = "";
    private String toTMarkup = "";
    private String toTSdiscount = "";

    private String fromActualBaseFare = "";
    private String fromTax = "";
    private String fromSTax = "";
    private String fromSCharge = "";
    private String fromTDiscount = "";
    private String fromTPartnerCommission = "";
    private String fromTCharge = "";
    private String fromTMarkup = "";
    private String fromTSdiscount = "";
    private int totalOnwardPrice = 0;
    private int totalReturnPrice = 0;
    private String toFlightId = "";
    private String toKey = "";
    private String fromFlightId = "";
    private String fromKey = "";
    private String fromAirWayName = "";
    private String toAirWayName = "";
    private String fromAirwayCode = "";
    private String toOperatingAirwayCode = "";
    private String operatingAirwayCode = "";
    private String departureDateTime;
    private String arrivalDateTime;
    private String selectedUserId = "";
    private String orderId = "";
    private String transactionRefNo = "";
    private String statusDescription = "";
    private String status = "";
    private String transid = "";
    private String partnerreferenceID = "";
    private String error = "";
    private String walletBalance = "";
    private String useWallet = "0";
    private String remainingAmount = "0";
    private double markUpFee = 0.0;
    private double conventionalFee = 0.0;
    private double totalFeeWithTax = 0;
    private double airportTax = 0;
    CheckBox termsCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flight_traveller_details);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        prepareLayout();
        serviceCallForGetProfileDetails();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sourceFullName = bundle.getString(getString(R.string.source));
            destinationFullName = bundle.getString(getString(R.string.destination));
            totalAdultBaseFare = bundle.getDouble(getString(R.string.total_adult_base_fare));
            totalAdultTax = bundle.getDouble(getString(R.string.total_adult_tax));
            totalChildrenBaseFare = bundle.getDouble(getString(R.string.total_children_base_fare));
            totalChildrenTax = bundle.getDouble(getString(R.string.total_children_tax));
            totalInfantBaseFare = bundle.getDouble(getString(R.string.total_infant_base_fare));
            totalInfantTax = bundle.getDouble(getString(R.string.total_infant_tax));
            from = bundle.getString(getString(R.string.from));
            sourceCountryName = bundle.getString(getString(R.string.source_country));
            destinationCountryName = bundle.getString(getString(R.string.destination_country));
            numberOfAdults = bundle.getInt(getString(R.string.adult_count));
            numberOfChildren = bundle.getInt(getString(R.string.children_count));
            numberOfInfants = bundle.getInt(getString(R.string.infant_count));
            selectedUserId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
            airportTax = bundle.getDouble(getString(R.string.airport_tax));
            markUpFee = bundle.getDouble(getString(R.string.mark_up_fee));
            conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format(bundle.getDouble(getString(R.string.conventional_fee)))));


        }
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.traveller_details));
        setSupportActionBar(toolbar);

        llAdult = findViewById(R.id.llAdult);
        llChild = findViewById(R.id.llChild);
        llInfant = findViewById(R.id.llInfant);
        inputLayoutMobileNumber = findViewById(R.id.inputLayoutMobileNumber);
        inputLayoutEmail = findViewById(R.id.inputLayoutEmail);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvMakePayment = findViewById(R.id.tvMakePayment);
        ivInfo = findViewById(R.id.ivInfo);
        rbPersonal = findViewById(R.id.rbPersonal);
        rbBusiness = findViewById(R.id.rbBusiness);
        walletCheckBox = findViewById(R.id.walletCheckBox);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        tvWalletBalance = findViewById(R.id.tvWalletBalance);
        networkDetection = new NetworkDetection();

        String emailId = PreferenceConnector.readString(this, getString(R.string.user_email), "");
        String mobileNumber = PreferenceConnector.readString(this, getString(R.string.user_mobile_number), "");

        etMobileNumber.setText(mobileNumber);
        etEmailAddress.setText(emailId);

        listOfAdultTitle = new ArrayList<>();
        listOfChildrenTitle = new ArrayList<>();
        listOfInfantTitle = new ArrayList<>();

        listOfAdultFirstNames = new ArrayList<>();
        listOfChildrenFirstNames = new ArrayList<>();
        listOfInfantFirstNames = new ArrayList<>();

        listOfAdultLastNames = new ArrayList<>();
        listOfChildrenLastNames = new ArrayList<>();
        listOfInfantLastNames = new ArrayList<>();

        listOfAdultAge = new ArrayList<>();
        listOfChildrenAge = new ArrayList<>();
        listOfInfantAge = new ArrayList<>();

        listOfAdultPassportNumber = new ArrayList<>();
        listOfChildrenPassportNumber = new ArrayList<>();
        listOfInfantPassportNumber = new ArrayList<>();

        listOfAdultVisaType = new ArrayList<>();
        listOfChildrenVisaType = new ArrayList<>();
        listOfInfantVisaType = new ArrayList<>();

        listOfAdultPassportExpiryDate = new ArrayList<>();
        listOfChildrenPassportExpiryDate = new ArrayList<>();
        listOfInfantPassportExpiryDate = new ArrayList<>();

        AppController.getInstance().setFlightTravellerDetailsContext(this);
        String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

        grandTotal = Math.round(totalAdultBaseFare + totalAdultTax + totalChildrenBaseFare + totalChildrenTax + totalInfantBaseFare + totalInfantTax);

        Log.e("markup",""+markUpFee);
        Log.e("grandTotal",""+grandTotal);
        Log.e("conventionalFee",""+conventionalFee);
        Log.e("airportTax",""+airportTax);

        grandTotal = totalAdultBaseFare + totalAdultTax + totalChildrenBaseFare + totalChildrenTax + totalInfantBaseFare + totalInfantTax;

       double totalAmountWithOutTaxes = totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare;
       int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
       int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));
       Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
       Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
        markUpFee = totalAmountWithOutTaxes * markUpPercentage;

        if (busMType.equalsIgnoreCase("M"))
        {
            conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((grandTotal-markUpFee) * conventionalFeePercentage)));
        } else
        {
            conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((grandTotal+markUpFee) * conventionalFeePercentage)));
        }




        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (grandTotal - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (grandTotal + markUpFee + conventionalFee);

        tvTotalAmount.setText(getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));
    }

    private void initializeListeners() {
        llAdult.setOnClickListener(this);
        llChild.setOnClickListener(this);
        llInfant.setOnClickListener(this);
        ivInfo.setOnClickListener(this);
        tvTotalAmount.setOnClickListener(this);
        tvMakePayment.setOnClickListener(this);
        walletCheckBox.setOnCheckedChangeListener(this);
        termsCheckBox.setOnCheckedChangeListener(this);
    }

    private void prepareLayout() {
        if (numberOfAdults != 0) {
            for (int adultsIndex = 0; adultsIndex < numberOfAdults; adultsIndex++) {
                initializeAdultUi(adultsIndex);
            }
        }
        if (numberOfChildren != 0) {
            for (int childrenIndex = 0; childrenIndex < numberOfChildren; childrenIndex++) {
                initializeChildrenUi(childrenIndex);
            }
        }
        if (numberOfInfants != 0) {
            for (int infantIndex = 0; infantIndex < numberOfInfants; infantIndex++) {
                initializeInfantUi(infantIndex);
            }
        }
        if (totalChildrenBaseFare != 0) {
            llChild.setVisibility(View.VISIBLE);
            //childView.setVisibility(View.VISIBLE);
        } else {
            llChild.setVisibility(View.GONE);
            //childView.setVisibility(View.GONE);
        }

        if (totalInfantBaseFare != 0) {
            llInfant.setVisibility(View.VISIBLE);
            //infantView.setVisibility(View.VISIBLE);
        } else {
            llInfant.setVisibility(View.GONE);
            //infantView.setVisibility(View.GONE);
        }
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

    private void initializeAdultUi(int adultsIndex) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_adult_header, null);
        LinearLayout llAdultContainer = view.findViewById(R.id.llAdultContainer);
        llAdultContainer.setTag(adultsIndex);
        llAdultContainer.setOnClickListener(this);
        llAdult.addView(view);
    }

    private void initializeChildrenUi(int childrenIndex) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_child_header, null);
        LinearLayout llChildContainer = view.findViewById(R.id.llChildContainer);
        llChildContainer.setTag(childrenIndex);
        llChildContainer.setOnClickListener(this);
        llChild.addView(view);
    }

    private void initializeInfantUi(int infantIndex) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_infant_header, null);
        LinearLayout llInfantContainer = view.findViewById(R.id.llInfantContainer);
        llInfantContainer.setTag(infantIndex);
        llInfantContainer.setOnClickListener(this);
        llInfant.addView(view);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llAdultContainer:
                int position = (int) view.getTag();
                Intent adultIntentDetails = new Intent(this, AdultDetailsActivity.class);
                adultIntentDetails.putExtra(getString(R.string.position), position);
                startActivity(adultIntentDetails);
                break;
            case R.id.llChildContainer:
                int childPosition = (int) view.getTag();
                Intent childIntentDetails = new Intent(this, ChildrenDetailsActivity.class);
                childIntentDetails.putExtra(getString(R.string.position), childPosition);
                startActivity(childIntentDetails);
                break;
            case R.id.llInfantContainer:
                int infantPosition = (int) view.getTag();
                Intent infantIntentDetails = new Intent(this, InfantDetailsActivity.class);
                infantIntentDetails.putExtra(getString(R.string.position), infantPosition);
                startActivity(infantIntentDetails);
                break;
            case R.id.ivInfo:
                showInformationAlertDialog();
                break;
            case R.id.tvTotalAmount:

                Intent fareBreakUpIntent = new Intent(this, FareBreakUpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.source), sourceFullName);
                bundle.putString(getString(R.string.destination), destinationFullName);
                bundle.putDouble(getString(R.string.total_adult_base_fare), totalAdultBaseFare);
                bundle.putDouble(getString(R.string.total_adult_tax), totalAdultTax);
                bundle.putDouble(getString(R.string.total_children_base_fare), totalChildrenBaseFare);
                bundle.putDouble(getString(R.string.total_children_tax), totalChildrenTax);
                bundle.putDouble(getString(R.string.total_infant_base_fare), totalInfantBaseFare);
                bundle.putDouble(getString(R.string.total_infant_tax), totalInfantTax);
                bundle.putDouble(getString(R.string.mark_up_fee), markUpFee);
                bundle.putDouble(getString(R.string.conventional_fee), conventionalFee);
                bundle.putDouble(getString(R.string.airport_tax), airportTax);
                fareBreakUpIntent.putExtras(bundle);
                startActivity(fareBreakUpIntent);



                break;
            case R.id.tvMakePayment:
                
                if (termsCheckBox.isChecked())
                {

                    if (isValidMobile(etMobileNumber.getText().toString().trim()))
                    {

                        if (isEditTextContainEmail(etEmailAddress.getText().toString().trim()))
                        {
                            bookingDetails(view);
                        }

                        else
                        {
                            inputLayoutEmail.setError("Please enter valid email");
                            inputLayoutEmail.setErrorEnabled(true);
                        }


                    }
                    else
                    {
                        inputLayoutMobileNumber.setError("Please enter 10 digit number");
                        inputLayoutMobileNumber.setErrorEnabled(true);
                    }



                }else 
                {
                    Toast.makeText(this, "Please check Terms and Conditions", Toast.LENGTH_SHORT).show();
                }
                
                
                break;
            default:
                break;
        }
    }

    private void paymentGateway(Double totalPayableAmount) {
        Double totalFare = totalPayableAmount * 100.0;
        Log.e("totalFare",""+totalFare);
        Intent intent = new Intent(this, PaymentStandard.class);
        intent.putExtra(Param.ORDER_ID, "" + getRandomOrderId());
        intent.putExtra(Param.TRANSACTION_AMOUNT, String.valueOf(totalFare.intValue()));
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
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
                    if (from.equalsIgnoreCase(getString(R.string.international_one_way))) {
                        showProgressBar();
                        internationalOneWayObjects();
                    } else if (from.equalsIgnoreCase(getString(R.string.international_round_trip))) {
                        showProgressBar();
                        internationalRoundTripObject();
                    } else if (from.equalsIgnoreCase(getString(R.string.domestic_one_way))) {
                        showProgressBar();
                        domesticOneWayObject();
                    } else if (from.equalsIgnoreCase(getString(R.string.domestic_round_trip))) {
                        showProgressBar();
                        domesticRoundTripObject();
                    }
                   // closeProgressbar();
                } else {
                    Toast.makeText(this, statusDescription, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void bookingDetails(View view) {
       try {
           Double totalPayableAmount = 0.0;
           if (totalFeeWithTax != 0.0) {
               if (walletCheckBox.isChecked()) {
                   if (Double.parseDouble(walletBalance) >= totalFeeWithTax) {
                       totalPayableAmount = 0.0;
                   } else {
                       totalPayableAmount = totalFeeWithTax - Double.parseDouble(walletBalance);
                   }
               } else {
                   totalPayableAmount = totalFeeWithTax;
               }
           }
           mobileNumber = etMobileNumber.getText().toString();
           emailAddress = etEmailAddress.getText().toString();
           if (!mobileNumber.isEmpty()) {
               inputLayoutMobileNumber.setErrorEnabled(false);
               inputLayoutMobileNumber.setError("");
               if (!emailAddress.isEmpty()) {
                   inputLayoutEmail.setErrorEnabled(false);
                   inputLayoutEmail.setError("");
                   if (from.equalsIgnoreCase(getString(R.string.international_one_way))) {
                       if (numberOfAdults == listOfAdultFirstNames.size() && numberOfChildren == listOfChildrenFirstNames.size() && numberOfInfants == listOfInfantFirstNames.size()) {
                           if (totalPayableAmount == 0.0) {
                               showProgressBar();
                               internationalOneWayObjects();
                           } else {
                               paymentGateway(totalPayableAmount);
                           }
                       } else {
                           Toast.makeText(this, getString(R.string.please_enter_personal_details), Toast.LENGTH_SHORT).show();
                           int position = (int) view.getTag();
                           Intent adultIntentDetails = new Intent(this, AdultDetailsActivity.class);
                           adultIntentDetails.putExtra(getString(R.string.position), position);
                           startActivity(adultIntentDetails);
                       }
                   } else if (from.equalsIgnoreCase(getString(R.string.international_round_trip))) {
                       if (numberOfAdults == listOfAdultFirstNames.size() && numberOfChildren == listOfChildrenFirstNames.size() && numberOfInfants == listOfInfantFirstNames.size()) {
                           //paymentGateway();
                           if (totalPayableAmount == 0.0) {
                               showProgressBar();
                               internationalRoundTripObject();
                           } else {
                               paymentGateway(totalPayableAmount);
                           }
                       } else {
                           Toast.makeText(this, getString(R.string.please_enter_personal_details), Toast.LENGTH_SHORT).show();
                           int position = (int) view.getTag();
                           Intent adultIntentDetails = new Intent(this, AdultDetailsActivity.class);
                           adultIntentDetails.putExtra(getString(R.string.position), position);
                           startActivity(adultIntentDetails);
                       }
                   } else if (from.equalsIgnoreCase(getString(R.string.domestic_one_way))) {
                       if (numberOfAdults == listOfAdultFirstNames.size() && numberOfChildren == listOfChildrenFirstNames.size() && numberOfInfants == listOfInfantFirstNames.size()) {
                           //paymentGateway();
                           if (totalPayableAmount == 0.0) {
                               showProgressBar();
                               domesticOneWayObject();
                           } else {
                               paymentGateway(totalPayableAmount);
                           }
                       } else {
                           Toast.makeText(this, getString(R.string.please_enter_personal_details), Toast.LENGTH_SHORT).show();
                           int position = (int) view.getTag();
                           Intent adultIntentDetails = new Intent(this, AdultDetailsActivity.class);
                           adultIntentDetails.putExtra(getString(R.string.position), position);
                           startActivity(adultIntentDetails);

                       }
                   } else if (from.equalsIgnoreCase(getString(R.string.domestic_round_trip))) {
                       if (numberOfAdults == listOfAdultFirstNames.size() && numberOfChildren == listOfChildrenFirstNames.size() && numberOfInfants == listOfInfantFirstNames.size()) {
                           //paymentGateway();
                           if (totalPayableAmount == 0.0) {
                               showProgressBar();
                               domesticRoundTripObject();
                           } else {
                               paymentGateway(totalPayableAmount);
                           }
                       } else {
                           int position = (int) view.getTag();
                           Toast.makeText(this, getString(R.string.please_enter_personal_details), Toast.LENGTH_SHORT).show();

                           Intent adultIntentDetails = new Intent(this, AdultDetailsActivity.class);
                           adultIntentDetails.putExtra(getString(R.string.position), position);
                           startActivity(adultIntentDetails);
                       }
                   }
               } else {
                   inputLayoutEmail.setErrorEnabled(true);
                   inputLayoutEmail.setError(getString(R.string.please_enter_email_address));
               }
           } else {
               inputLayoutMobileNumber.setErrorEnabled(true);
               inputLayoutMobileNumber.setError(getString(R.string.please_enter_mobile_number));
           }
       }catch (Exception e)
       {
           e.printStackTrace();
       }
    }

    private void domesticRoundTripObject() {
        Double walletAmount = 0.0;
        if (grandTotal != 0.0) {
            if (walletCheckBox.isChecked()) {
                useWallet = "1";
                if (Double.parseDouble(walletBalance) >= grandTotal) {
                    walletAmount = grandTotal;
                    remainingAmount = "0";
                } else {
                    walletAmount = Double.parseDouble(walletBalance);
                    remainingAmount = String.valueOf(grandTotal - Double.parseDouble(walletBalance));
                }
            } else {
                useWallet = "0";
                remainingAmount = "0";
            }
        } else {
            useWallet = "0";
            remainingAmount = "0";
        }
        com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption toOriginDestinationOption = AppController.getInstance().getSelectedToOriginDestinationOption();
        com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption fromOriginDestinationOption = AppController.getInstance().getSelectedFromOriginDestinationOption();
        com.payz24.responseModels.DomesticFlightsSearchRoundTrip.Result result = AppController.getInstance().getResult();
        String from = sourceFullName;
        String to = destinationFullName;
        String fromCode = result.getRequest().getOrigin();
        String toCode = result.getRequest().getDestination();
        String sd = result.getRequest().getDepartDate();
        String ed = result.getRequest().getReturnDate();
        String adult = result.getRequest().getAdultPax();
        String child = result.getRequest().getChildPax();
        String infant = result.getRequest().getInfantPax();
        String travelClass = "";
        if (result.getRequest().getPreferredclass().equalsIgnoreCase("E")) {
            travelClass = "Economy";
        } else {
            travelClass = "Business";
        }
        Integer nonStopOnly = 0;
        String journeyType = "R";
        String fromCountry = AppController.getInstance().getSourceCountry();
        String toCountry = AppController.getInstance().getDestinationCountry();
        String fromAirport = sourceFullName;
        String toAirport = destinationFullName;
        String fromCity = sourceFullName;
        String toCity = destinationFullName;
        String searchType = "owDom";


        String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        int totalFeeWithTax = 0;
        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (grandTotal - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (grandTotal + markUpFee+ conventionalFee);


/*        Log.e("RoundTrip Domestc","RoundTrip Domestc");
        Log.e("totalFeeWithTax",""+totalFeeWithTax);
        Log.e("totalFeeWithTax",""+totalFeeWithTax);
        Log.e("grandTotal",""+grandTotal);
        Log.e("cv",""+conventionalFee);
        Log.e("markUpFee",""+markUpFee);*/


        String userEmail = emailAddress;
        String countryCode = "91";
        String userPhone = mobileNumber;
        String userid = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        String type = PreferenceConnector.readString(this, getString(R.string.user_type), "");
        String newsletter = "yes";
        String tandc = "yes";
        String flightMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        com.payz24.responseModels.domesticFlightBookingRequest.SearchData searchData = new com.payz24.responseModels.domesticFlightBookingRequest.SearchData(from, to, fromCode, toCode, sd, ed, adult, child, infant, travelClass, nonStopOnly, journeyType, fromCountry, toCountry, fromAirport, toAirport, fromCity, toCity, searchType, sourceFullName, destinationFullName);
        com.payz24.responseModels.domesticFlightBookingRequest.PostData postData = new com.payz24.responseModels.domesticFlightBookingRequest.PostData
                (userEmail, String.valueOf(walletAmount), countryCode, userPhone, userid, type, String.valueOf(totalFeeWithTax), String.valueOf(totalFeeWithTax), listOfAdultTitle, listOfAdultFirstNames, listOfAdultLastNames, listOfAdultAge, listOfChildrenTitle, listOfChildrenFirstNames, listOfChildrenLastNames, listOfChildrenAge, listOfInfantTitle, listOfInfantFirstNames, listOfInfantLastNames, listOfInfantAge, fromCountry, toCountry, newsletter, tandc, useWallet, remainingAmount,
                flightMType, conventionalFee, markUpFee, (totalAdultTax + totalChildrenTax + totalInfantTax), (totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare));
        listOfToLegs = new ArrayList<>();
        listOfFromLegs = new ArrayList<>();
        toOriginResults(toOriginDestinationOption);
        fromOriginResult(fromOriginDestinationOption);
        Double totFirstPrice = grandTotal;
        com.payz24.responseModels.domesticFlightBookingRequest.Fare toFare = new com.payz24.responseModels.domesticFlightBookingRequest.Fare(toActualBaseFare, toTax, toSTax, toSCharge, toTDiscount, toTPartnerCommission, toTCharge, toTMarkup, toTSdiscount);
        com.payz24.responseModels.domesticFlightBookingRequest.Fare fromFare = new com.payz24.responseModels.domesticFlightBookingRequest.Fare(fromActualBaseFare, fromTax, fromSTax, fromSCharge, fromTDiscount, fromTPartnerCommission, fromTCharge, fromTMarkup, fromTSdiscount);
        Onward onward = new Onward(toFlightId, toKey, -1, -1, toFare, listOfToLegs);
        Return returnTrip = new Return(fromFlightId, fromKey, fromFare, listOfFromLegs);
        com.payz24.responseModels.domesticFlightBookingRequest.FlightData flightData = new com.payz24.responseModels.domesticFlightBookingRequest.FlightData(totFirstPrice.intValue(), totalOnwardPrice, totalReturnPrice, onward, totalOnwardPrice, totalReturnPrice, returnTrip);
        Opricing opricing = new Opricing(toFare);
        Rpricing rpricing = new Rpricing(fromFare);
        DomesticFlightBooking domesticFlightBooking = new DomesticFlightBooking(searchData, postData, flightData, opricing, rpricing);
        String domesticFlightBookingString = new Gson().toJson(domesticFlightBooking);
        serviceCallForDomesticFlightBooking(domesticFlightBookingString);
    }

    private void fromOriginResult(com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption fromOriginDestinationOption) {
        fromActualBaseFare = fromOriginDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
        fromTax = fromOriginDestinationOption.getFareDetails().getChargeableFares().getTax();
        fromSTax = fromOriginDestinationOption.getFareDetails().getChargeableFares().getSTax();
        fromSCharge = fromOriginDestinationOption.getFareDetails().getChargeableFares().getSCharge();
        fromTDiscount = fromOriginDestinationOption.getFareDetails().getChargeableFares().getTDiscount();
        fromTPartnerCommission = fromOriginDestinationOption.getFareDetails().getChargeableFares().getTPartnerCommission();

        totalReturnPrice = Integer.parseInt(fromActualBaseFare) + Integer.parseInt(fromTax) + Integer.parseInt(fromSTax) + Integer.parseInt(fromSCharge) + Integer.parseInt(fromTDiscount)
                + Integer.parseInt(fromTPartnerCommission);

        fromTCharge = fromOriginDestinationOption.getFareDetails().getNonchargeableFares().getTCharge();
        fromTMarkup = fromOriginDestinationOption.getFareDetails().getNonchargeableFares().getTMarkup();
        fromTSdiscount = fromOriginDestinationOption.getFareDetails().getNonchargeableFares().getTSdiscount();

        fromFlightId = fromOriginDestinationOption.getId();
        fromKey = fromOriginDestinationOption.getKey();

        JsonObject toOriginDestinationOptionJsonObject = fromOriginDestinationOption.getFlightSegments().getAsJsonObject();
        try {
            JSONObject flightSegmentJsonObject = new JSONObject(toOriginDestinationOptionJsonObject.toString());
            Object object = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
            com.payz24.responseModels.domesticFlightBookingRequest.Leg domesticLegs;
            if (object instanceof JSONObject) {
                com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment.class);

                String airEquipType = flightSegment.getAirEquipType();
                String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                String arrivalDateTime = flightSegment.getArrivalDateTime();
                String departureAirportCode = flightSegment.getDepartureAirportCode();
                departureDateTime = flightSegment.getDepartureDateTime();
                String flightNumber = flightSegment.getFlightNumber();
                String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                String rPH = "";
                if (flightSegment.getRPH() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getRPH().toString();
                    fromAirWayName = airwayName;
                    fromAirwayCode = operatingAirlineCode;
                }
                String airLineName = flightSegment.getAirLineName();
                String airportTax = flightSegment.getAirportTax();
                String imageFileName = flightSegment.getImageFileName();
                String viaFlight = "";
                String stopQuantity = flightSegment.getStopQuantity();
                String availability = flightSegment.getBookingClass().getAvailability();
                String resBookDesigCode = flightSegment.getBookingClass().getResBookDesigCode();
                String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                String classType = flightSegment.getBookingClassFare().getClassType();
                String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                String rule = flightSegment.getBookingClassFare().getRule();
                String infantfare = "0";
                String childFare = "0";
                if (flightSegment.getBookingClassFare().getInfantfare() != null)
                    infantfare = flightSegment.getBookingClassFare().getInfantfare();
                else
                    infantfare = "0";
                if (flightSegment.getBookingClassFare().getChildFare() != null) {
                    childFare = flightSegment.getBookingClassFare().getChildFare();
                } else {
                    childFare = "0";
                }
                String adultCommission = flightSegment.getBookingClassFare().getAdultCommission();
                String childCommission = flightSegment.getBookingClassFare().getChildCommission();
                String commissionOnTCharge = flightSegment.getBookingClassFare().getCommissionOnTCharge();
                String discount = flightSegment.getDiscount();
                String airportTaxChild = flightSegment.getAirportTaxChild();
                String airportTaxInfant = flightSegment.getAirportTaxInfant();
                String adultTaxBreakup = flightSegment.getAdultTaxBreakup();
                String childTaxBreakup = flightSegment.getChildTaxBreakup();
                String infantTaxBreakup = flightSegment.getInfantTaxBreakup();
                String octax = flightSegment.getOctax();
                domesticLegs = new com.payz24.responseModels.domesticFlightBookingRequest.Leg(airEquipType, arrivalAirportCode, arrivalDateTime, departureAirportCode, departureDateTime,
                        flightNumber, operatingAirlineCode, operatingAirlineFlightNumber, rPH, airLineName, airportTax, imageFileName, viaFlight, stopQuantity, availability, resBookDesigCode,
                        adultFare, bookingclass, classType, farebasiscode, rule, infantfare, childFare, adultCommission, childCommission, commissionOnTCharge, discount, airportTaxChild, airportTaxInfant,
                        adultTaxBreakup, childTaxBreakup, infantTaxBreakup, octax);
                listOfFromLegs.add(domesticLegs);
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    String response = jsonArray.getString(index);
                    com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment flightSegment = new Gson().fromJson(response, com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment.class);
                    String airEquipType = flightSegment.getAirEquipType();
                    String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                    arrivalDateTime = flightSegment.getArrivalDateTime();
                    String departureAirportCode = flightSegment.getDepartureAirportCode();
                    departureDateTime = flightSegment.getDepartureDateTime();
                    String flightNumber = flightSegment.getFlightNumber();
                    String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                    String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                    String rPH = "";
                    if (flightSegment.getRPH() instanceof JSONArray) {

                    } else {
                        airwayName = flightSegment.getRPH().toString();
                        fromAirWayName = airwayName;
                        fromAirwayCode = operatingAirlineCode;
                    }
                    String airLineName = flightSegment.getAirLineName();
                    String airportTax = flightSegment.getAirportTax();
                    String imageFileName = flightSegment.getImageFileName();
                    String viaFlight = "";
                    String stopQuantity = flightSegment.getStopQuantity();
                    String availability = flightSegment.getBookingClass().getAvailability();
                    String resBookDesigCode = flightSegment.getBookingClass().getResBookDesigCode();
                    String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                    String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                    String classType = flightSegment.getBookingClassFare().getClassType();
                    String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                    String rule = flightSegment.getBookingClassFare().getRule();
                    String infantfare = "0";
                    String childFare = "0";
                    if (flightSegment.getBookingClassFare().getInfantfare() != null)
                        infantfare = flightSegment.getBookingClassFare().getInfantfare();
                    else
                        infantfare = "0";
                    if (flightSegment.getBookingClassFare().getChildFare() != null) {
                        childFare = flightSegment.getBookingClassFare().getChildFare();
                    } else {
                        childFare = "0";
                    }
                    String adultCommission = flightSegment.getBookingClassFare().getAdultCommission();
                    String childCommission = flightSegment.getBookingClassFare().getChildCommission();
                    String commissionOnTCharge = flightSegment.getBookingClassFare().getCommissionOnTCharge();
                    String discount = flightSegment.getDiscount();
                    String airportTaxChild = flightSegment.getAirportTaxChild();
                    String airportTaxInfant = flightSegment.getAirportTaxInfant();
                    String adultTaxBreakup = flightSegment.getAdultTaxBreakup();
                    String childTaxBreakup = flightSegment.getChildTaxBreakup();
                    String infantTaxBreakup = flightSegment.getInfantTaxBreakup();
                    String octax = flightSegment.getOctax();
                    domesticLegs = new com.payz24.responseModels.domesticFlightBookingRequest.Leg(airEquipType, arrivalAirportCode, arrivalDateTime, departureAirportCode, departureDateTime,
                            flightNumber, operatingAirlineCode, operatingAirlineFlightNumber, rPH, airLineName, airportTax, imageFileName, viaFlight, stopQuantity, availability, resBookDesigCode,
                            adultFare, bookingclass, classType, farebasiscode, rule, infantfare, childFare, adultCommission, childCommission, commissionOnTCharge, discount, airportTaxChild, airportTaxInfant,
                            adultTaxBreakup, childTaxBreakup, infantTaxBreakup, octax);
                    listOfFromLegs.add(domesticLegs);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void toOriginResults(com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption toOriginDestinationOption) {
        toActualBaseFare = toOriginDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
        toTax = toOriginDestinationOption.getFareDetails().getChargeableFares().getTax();
        toSTax = toOriginDestinationOption.getFareDetails().getChargeableFares().getSTax();
        toSCharge = toOriginDestinationOption.getFareDetails().getChargeableFares().getSCharge();
        toTDiscount = toOriginDestinationOption.getFareDetails().getChargeableFares().getTDiscount();
        toTPartnerCommission = toOriginDestinationOption.getFareDetails().getChargeableFares().getTPartnerCommission();

        totalOnwardPrice = Integer.parseInt(toActualBaseFare) + Integer.parseInt(toTax) + Integer.parseInt(toSTax) + Integer.parseInt(toSCharge) + Integer.parseInt(toTDiscount)
                + Integer.parseInt(toTPartnerCommission);

        toTCharge = toOriginDestinationOption.getFareDetails().getNonchargeableFares().getTCharge();
        toTMarkup = toOriginDestinationOption.getFareDetails().getNonchargeableFares().getTMarkup();
        toTSdiscount = toOriginDestinationOption.getFareDetails().getNonchargeableFares().getTSdiscount();

        toFlightId = toOriginDestinationOption.getId();
        toKey = toOriginDestinationOption.getKey();

        JsonObject toOriginDestinationOptionJsonObject = toOriginDestinationOption.getFlightSegments().getAsJsonObject();
        try {
            JSONObject flightSegmentJsonObject = new JSONObject(toOriginDestinationOptionJsonObject.toString());
            Object object = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
            com.payz24.responseModels.domesticFlightBookingRequest.Leg domesticLegs;
            if (object instanceof JSONObject) {
                com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment.class);

                String airEquipType = flightSegment.getAirEquipType();
                String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                arrivalDateTime = flightSegment.getArrivalDateTime();
                String departureAirportCode = flightSegment.getDepartureAirportCode();
                departureDateTime = flightSegment.getDepartureDateTime();
                String flightNumber = flightSegment.getFlightNumber();
                String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                String rPH = "";
                if (flightSegment.getRPH() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getRPH().toString();
                    toAirWayName = airwayName;
                    toOperatingAirwayCode = operatingAirlineCode;
                }
                String airLineName = flightSegment.getAirLineName();
                String airportTax = flightSegment.getAirportTax();
                String imageFileName = flightSegment.getImageFileName();
                String viaFlight = "";
                String stopQuantity = flightSegment.getStopQuantity();
                String availability = flightSegment.getBookingClass().getAvailability();
                String resBookDesigCode = flightSegment.getBookingClass().getResBookDesigCode();
                String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                String classType = flightSegment.getBookingClassFare().getClassType();
                String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                String rule = flightSegment.getBookingClassFare().getRule();
                String infantfare = "0";
                String childFare = "0";
                if (flightSegment.getBookingClassFare().getInfantfare() != null)
                    infantfare = flightSegment.getBookingClassFare().getInfantfare();
                else
                    infantfare = "0";
                if (flightSegment.getBookingClassFare().getChildFare() != null) {
                    childFare = flightSegment.getBookingClassFare().getChildFare();
                } else {
                    childFare = "0";
                }
                String adultCommission = flightSegment.getBookingClassFare().getAdultCommission();
                String childCommission = flightSegment.getBookingClassFare().getChildCommission();
                String commissionOnTCharge = flightSegment.getBookingClassFare().getCommissionOnTCharge();
                String discount = flightSegment.getDiscount();
                String airportTaxChild = flightSegment.getAirportTaxChild();
                String airportTaxInfant = flightSegment.getAirportTaxInfant();
                String adultTaxBreakup = flightSegment.getAdultTaxBreakup();
                String childTaxBreakup = flightSegment.getChildTaxBreakup();
                String infantTaxBreakup = flightSegment.getInfantTaxBreakup();
                String octax = flightSegment.getOctax();
                domesticLegs = new com.payz24.responseModels.domesticFlightBookingRequest.Leg(airEquipType, arrivalAirportCode, arrivalDateTime, departureAirportCode, departureDateTime,
                        flightNumber, operatingAirlineCode, operatingAirlineFlightNumber, rPH, airLineName, airportTax, imageFileName, viaFlight, stopQuantity, availability, resBookDesigCode,
                        adultFare, bookingclass, classType, farebasiscode, rule, infantfare, childFare, adultCommission, childCommission, commissionOnTCharge, discount, airportTaxChild, airportTaxInfant,
                        adultTaxBreakup, childTaxBreakup, infantTaxBreakup, octax);
                listOfToLegs.add(domesticLegs);
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    String response = jsonArray.getString(index);
                    com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment flightSegment = new Gson().fromJson(response, com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment.class);
                    String airEquipType = flightSegment.getAirEquipType();
                    String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                    arrivalDateTime = flightSegment.getArrivalDateTime();
                    String departureAirportCode = flightSegment.getDepartureAirportCode();
                    departureDateTime = flightSegment.getDepartureDateTime();
                    String flightNumber = flightSegment.getFlightNumber();
                    String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                    String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                    String rPH = "";
                    if (flightSegment.getRPH() instanceof JSONArray) {

                    } else {
                        airwayName = flightSegment.getRPH().toString();
                        toAirWayName = airwayName;
                        toOperatingAirwayCode = operatingAirlineCode;
                    }
                    String airLineName = flightSegment.getAirLineName();
                    String airportTax = flightSegment.getAirportTax();
                    String imageFileName = flightSegment.getImageFileName();
                    String viaFlight = "";
                    String stopQuantity = flightSegment.getStopQuantity();
                    String availability = flightSegment.getBookingClass().getAvailability();
                    String resBookDesigCode = flightSegment.getBookingClass().getResBookDesigCode();
                    String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                    String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                    String classType = flightSegment.getBookingClassFare().getClassType();
                    String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                    String rule = flightSegment.getBookingClassFare().getRule();
                    String infantfare = "0";
                    String childFare = "0";
                    if (flightSegment.getBookingClassFare().getInfantfare() != null)
                        infantfare = flightSegment.getBookingClassFare().getInfantfare();
                    else
                        infantfare = "0";
                    if (flightSegment.getBookingClassFare().getChildFare() != null) {
                        childFare = flightSegment.getBookingClassFare().getChildFare();
                    } else {
                        childFare = "0";
                    }
                    String adultCommission = flightSegment.getBookingClassFare().getAdultCommission();
                    String childCommission = flightSegment.getBookingClassFare().getChildCommission();
                    String commissionOnTCharge = flightSegment.getBookingClassFare().getCommissionOnTCharge();
                    String discount = flightSegment.getDiscount();
                    String airportTaxChild = flightSegment.getAirportTaxChild();
                    String airportTaxInfant = flightSegment.getAirportTaxInfant();
                    String adultTaxBreakup = flightSegment.getAdultTaxBreakup();
                    String childTaxBreakup = flightSegment.getChildTaxBreakup();
                    String infantTaxBreakup = flightSegment.getInfantTaxBreakup();
                    String octax = flightSegment.getOctax();
                    domesticLegs = new com.payz24.responseModels.domesticFlightBookingRequest.Leg(airEquipType, arrivalAirportCode, arrivalDateTime, departureAirportCode, departureDateTime,
                            flightNumber, operatingAirlineCode, operatingAirlineFlightNumber, rPH, airLineName, airportTax, imageFileName, viaFlight, stopQuantity, availability, resBookDesigCode,
                            adultFare, bookingclass, classType, farebasiscode, rule, infantfare, childFare, adultCommission, childCommission, commissionOnTCharge, discount, airportTaxChild, airportTaxInfant,
                            adultTaxBreakup, childTaxBreakup, infantTaxBreakup, octax);
                    listOfToLegs.add(domesticLegs);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void domesticOneWayObject() {
        Double walletAmount = 0.0;
        if (grandTotal != 0.0) {
            if (walletCheckBox.isChecked()) {
                useWallet = "1";
                if (Double.parseDouble(walletBalance) >= grandTotal) {
                    walletAmount = grandTotal;
                    remainingAmount = "0";
                } else {
                    walletAmount = Double.parseDouble(walletBalance);
                    remainingAmount = String.valueOf(grandTotal - Double.parseDouble(walletBalance));
                }
            } else {
                useWallet = "0";
                remainingAmount = "0";
            }
        } else {
            useWallet = "0";
            remainingAmount = "0";
        }
        com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption originDestinationOption = AppController.getInstance().getSelectedToOriginDestinationOption();
        com.payz24.responseModels.DomesticFlightsSearchRoundTrip.Result result = AppController.getInstance().getResult();
        String from = sourceFullName;
        String to = destinationFullName;
        String fromCode = result.getRequest().getOrigin();
        String toCode = result.getRequest().getDestination();
        String sd = result.getRequest().getDepartDate();
        String ed = result.getRequest().getReturnDate();
        String adult = result.getRequest().getAdultPax();
        String child = result.getRequest().getChildPax();
        String infant = result.getRequest().getInfantPax();
        String travelClass = "";
        if (result.getRequest().getPreferredclass().equalsIgnoreCase("E")) {
            travelClass = "Economy";
        } else {
            travelClass = "Business";
        }
        Integer nonStopOnly = 0;
        String journeyType = "O";
        String fromCountry = AppController.getInstance().getSourceCountry();
        String toCountry = AppController.getInstance().getDestinationCountry();
        String fromAirport = sourceFullName;
        String toAirport = destinationFullName;
        String fromCity = sourceFullName;
        String toCity = destinationFullName;
        String searchType = "owDom";


        String userEmail = emailAddress;
        String countryCode = "91";
        String userPhone = mobileNumber;
        String userid = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        String type = PreferenceConnector.readString(this, getString(R.string.user_type), "");
        int grandTotal = Integer.parseInt(originDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare()) + Integer.parseInt(originDestinationOption.getFareDetails().getChargeableFares().getTax());

        String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        int totalFeeWithTax = 0;
        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (grandTotal - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (grandTotal + markUpFee+ conventionalFee);
        Log.e("totalfee",""+totalFeeWithTax);


        Double s= Double.valueOf(totalFeeWithTax);
        String totalamountTosend = String.valueOf(s);
        String totalamountForpayment = String.valueOf(s);
        String newsletter = "yes";
        String tandc = "yes";

        String flightId = originDestinationOption.getId();
        String key = originDestinationOption.getKey();
        Integer totFirstPrice = Integer.parseInt(""+totalFeeWithTax);
        Integer totSecPrice =Integer.parseInt(""+totalFeeWithTax);


        String actualBaseFare = originDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
        String tax = originDestinationOption.getFareDetails().getChargeableFares().getTax();
        String sTax = originDestinationOption.getFareDetails().getChargeableFares().getSTax();
        String sCharge = originDestinationOption.getFareDetails().getChargeableFares().getSCharge();
        String tDiscount = originDestinationOption.getFareDetails().getChargeableFares().getTDiscount();
        String tPartnerCommission = originDestinationOption.getFareDetails().getChargeableFares().getTPartnerCommission();
        String tCharge = originDestinationOption.getFareDetails().getNonchargeableFares().getTCharge();
        String tMarkup = originDestinationOption.getFareDetails().getNonchargeableFares().getTMarkup();
        String tSdiscount = originDestinationOption.getFareDetails().getNonchargeableFares().getTSdiscount();

        Log.e("totalamountTosend",totalamountTosend);
        Log.e("totalamountForpayment",totalamountForpayment);
        Log.e("grandTotal",""+grandTotal);
        Log.e("s",""+s);
        Log.e("tMarkup",""+tMarkup);
        Log.e("tCharge",""+tCharge);
        Log.e("sCharge",""+sCharge);
        Log.e("cv",""+conventionalFee);
        Log.e("markUpFee",""+markUpFee);


        listOfDomesticLegs = new ArrayList<>();
        domesticLegsJsonObject(originDestinationOption);

        com.payz24.responseModels.domesticFlightBookingRequest.Fare fare = new com.payz24.responseModels.domesticFlightBookingRequest.Fare(actualBaseFare, tax, sTax, sCharge, tDiscount, tPartnerCommission, tCharge, tMarkup, tSdiscount);
        Opricing Opricing = new Opricing(fare);
        Onward onward = new Onward(flightId, key, totFirstPrice, totSecPrice, fare, listOfDomesticLegs);
        String flightMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        com.payz24.responseModels.domesticFlightBookingRequest.FlightData flightData = new com.payz24.responseModels.domesticFlightBookingRequest.FlightData(-1, -1, -1, onward, -1, -1, null);
        com.payz24.responseModels.domesticFlightBookingRequest.PostData postData = new com.payz24.responseModels.domesticFlightBookingRequest.PostData(userEmail, String.valueOf(walletAmount), countryCode, userPhone, userid, type, totalamountTosend, totalamountForpayment, listOfAdultTitle, listOfAdultFirstNames, listOfAdultLastNames, listOfAdultAge, listOfChildrenTitle, listOfChildrenFirstNames, listOfChildrenLastNames, listOfChildrenAge, listOfInfantTitle, listOfInfantFirstNames, listOfInfantLastNames, listOfInfantAge, fromCountry, toCountry, newsletter, tandc,
                useWallet, remainingAmount, flightMType, conventionalFee, markUpFee, (totalAdultTax + totalChildrenTax + totalInfantTax), (totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare));
        com.payz24.responseModels.domesticFlightBookingRequest.SearchData searchData = new com.payz24.responseModels.domesticFlightBookingRequest.SearchData(from, to, fromCode, toCode, sd, "1970-01-01", adult, child, infant, "E", nonStopOnly, journeyType, fromCountry, toCountry, fromAirport, toAirport, fromCity, toCity, searchType, sourceFullName, destinationFullName);
        DomesticFlightBooking domesticFlightBooking = new DomesticFlightBooking(searchData, postData, flightData, Opricing, null);
        String domesticFlightBookingString = new Gson().toJson(domesticFlightBooking);
        serviceCallForDomesticFlightBooking(domesticFlightBookingString);
    }

    private void domesticLegsJsonObject(com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption originDestinationOption) {
        JsonObject toOriginDestinationOptionJsonObject = originDestinationOption.getFlightSegments().getAsJsonObject();
        try {
            JSONObject flightSegmentJsonObject = new JSONObject(toOriginDestinationOptionJsonObject.toString());
            Object object = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
            com.payz24.responseModels.domesticFlightBookingRequest.Leg domesticLegs;
            if (object instanceof JSONObject) {
                com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment.class);

                String airEquipType = flightSegment.getAirEquipType();
                String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                arrivalDateTime = flightSegment.getArrivalDateTime();
                String departureAirportCode = flightSegment.getDepartureAirportCode();
                departureDateTime = flightSegment.getDepartureDateTime();
                String flightNumber = flightSegment.getFlightNumber();
                String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                String rPH = "";
                if (flightSegment.getRPH() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getRPH().toString();
                    operatingAirwayCode = operatingAirlineCode;
                }
                String airLineName = flightSegment.getAirLineName();
                String airportTax = flightSegment.getAirportTax();
                String imageFileName = flightSegment.getImageFileName();
                String viaFlight = "";
                String stopQuantity = flightSegment.getStopQuantity();
                String availability = flightSegment.getBookingClass().getAvailability();
                String resBookDesigCode = flightSegment.getBookingClass().getResBookDesigCode();
                String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                String classType = flightSegment.getBookingClassFare().getClassType();
                String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                String rule = flightSegment.getBookingClassFare().getRule();
                String infantfare;
                String childFare;
                if (flightSegment.getBookingClassFare().getInfantfare() != null)
                    infantfare = flightSegment.getBookingClassFare().getInfantfare();
                else
                    infantfare = "0";
                if (flightSegment.getBookingClassFare().getChildFare() != null) {
                    childFare = flightSegment.getBookingClassFare().getChildFare();
                } else {
                    childFare = "0";
                }
                String adultCommission = flightSegment.getBookingClassFare().getAdultCommission();
                String childCommission = flightSegment.getBookingClassFare().getChildCommission();
                String commissionOnTCharge = flightSegment.getBookingClassFare().getCommissionOnTCharge();
                String discount = flightSegment.getDiscount();
                String airportTaxChild = flightSegment.getAirportTaxChild();
                String airportTaxInfant = flightSegment.getAirportTaxInfant();
                String adultTaxBreakup = flightSegment.getAdultTaxBreakup();
                String childTaxBreakup = flightSegment.getChildTaxBreakup();
                String infantTaxBreakup = flightSegment.getInfantTaxBreakup();
                String octax = flightSegment.getOctax();
                domesticLegs = new com.payz24.responseModels.domesticFlightBookingRequest.Leg(airEquipType, arrivalAirportCode, arrivalDateTime, departureAirportCode, departureDateTime,
                        flightNumber, operatingAirlineCode, operatingAirlineFlightNumber, rPH, airLineName, airportTax, imageFileName, viaFlight, stopQuantity, availability, resBookDesigCode,
                        adultFare, bookingclass, classType, farebasiscode, rule, infantfare, childFare, adultCommission, childCommission, commissionOnTCharge, discount, airportTaxChild, airportTaxInfant,
                        adultTaxBreakup, childTaxBreakup, infantTaxBreakup, octax);
                listOfDomesticLegs.add(domesticLegs);
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    String response = jsonArray.getString(index);
                    com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment flightSegment = new Gson().fromJson(response, com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment.class);
                    String airEquipType = flightSegment.getAirEquipType();
                    String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                    arrivalDateTime = flightSegment.getArrivalDateTime();
                    String departureAirportCode = flightSegment.getDepartureAirportCode();
                    departureDateTime = flightSegment.getDepartureDateTime();
                    String flightNumber = flightSegment.getFlightNumber();
                    String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                    String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                    String rPH = "";
                    if (flightSegment.getRPH() instanceof JSONArray) {

                    } else {
                        airwayName = flightSegment.getRPH().toString();
                        operatingAirwayCode = operatingAirlineCode;
                    }
                    String airLineName = flightSegment.getAirLineName();
                    String airportTax = flightSegment.getAirportTax();
                    String imageFileName = flightSegment.getImageFileName();
                    String viaFlight = "";
                    String stopQuantity = flightSegment.getStopQuantity();
                    String availability = flightSegment.getBookingClass().getAvailability();
                    String resBookDesigCode = flightSegment.getBookingClass().getResBookDesigCode();
                    String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                    String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                    String classType = flightSegment.getBookingClassFare().getClassType();
                    String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                    String rule = flightSegment.getBookingClassFare().getRule();
                    String infantfare = "0";
                    String childFare = "0";
                    if (flightSegment.getBookingClassFare().getInfantfare() != null)
                        infantfare = flightSegment.getBookingClassFare().getInfantfare();
                    else
                        infantfare = "0";
                    if (flightSegment.getBookingClassFare().getChildFare() != null) {
                        childFare = flightSegment.getBookingClassFare().getChildFare();
                    } else {
                        childFare = "0";
                    }
                    String adultCommission = flightSegment.getBookingClassFare().getAdultCommission();
                    String childCommission = flightSegment.getBookingClassFare().getChildCommission();
                    String commissionOnTCharge = flightSegment.getBookingClassFare().getCommissionOnTCharge();
                    String discount = flightSegment.getDiscount();
                    String airportTaxChild = flightSegment.getAirportTaxChild();
                    String airportTaxInfant = flightSegment.getAirportTaxInfant();
                    String adultTaxBreakup = flightSegment.getAdultTaxBreakup();
                    String childTaxBreakup = flightSegment.getChildTaxBreakup();
                    String infantTaxBreakup = flightSegment.getInfantTaxBreakup();
                    String octax = flightSegment.getOctax();

                    domesticLegs = new com.payz24.responseModels.domesticFlightBookingRequest.Leg(airEquipType, arrivalAirportCode, arrivalDateTime, departureAirportCode, departureDateTime,
                            flightNumber, operatingAirlineCode, operatingAirlineFlightNumber, rPH, airLineName, airportTax, imageFileName, viaFlight, stopQuantity, availability, resBookDesigCode,
                            adultFare, bookingclass, classType, farebasiscode, rule, infantfare, childFare, adultCommission, childCommission, commissionOnTCharge, discount, airportTaxChild, airportTaxInfant,
                            adultTaxBreakup, childTaxBreakup, infantTaxBreakup, octax);
                    listOfDomesticLegs.add(domesticLegs);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void internationalRoundTripObject() {
        Double walletAmount = 0.0;
        if (grandTotal != 0.0) {
            if (walletCheckBox.isChecked()) {
                useWallet = "1";
                if (Double.parseDouble(walletBalance) >= grandTotal) {
                    walletAmount = grandTotal;
                    remainingAmount = "0";
                } else {
                    walletAmount = Double.parseDouble(walletBalance);
                    remainingAmount = String.valueOf(grandTotal - Double.parseDouble(walletBalance));
                }
            } else {
                useWallet = "0";
                remainingAmount = "0";
            }
        } else {
            useWallet = "0";
            remainingAmount = "0";
        }
        AvailRequest availRequest = AppController.getInstance().getAvailRequest();
        OriginDestinationOption originDestinationOption = AppController.getInstance().getSelectedOriginDestinationOption();
        String from = sourceFullName;
        String to = destinationFullName;
        String fromCode = availRequest.getOrigin();
        String toCode = availRequest.getDestination();
        String sd = availRequest.getDepartDate();
        String ed = availRequest.getReturnDate();
        String adult = availRequest.getAdultPax();
        String child = availRequest.getChildPax();
        String infant = availRequest.getInfantPax();
        String travelClass = "";
        if (availRequest.getPreferredClass().equalsIgnoreCase("E")) {
            travelClass = "Economy";
        } else {
            travelClass = "Business";
        }
        Integer nonStopOnly = 0;
        String journeyType = "R";
        String fromCountry = AppController.getInstance().getSourceCountry();
        String toCountry = AppController.getInstance().getDestinationCountry();
        String fromAirport = sourceFullName;
        String toAirport = destinationFullName;
        String fromCity = sourceFullName;
        String toCity = destinationFullName;
        String searchType = "owDom";
        String email = emailAddress;
        String countryCode = "91";
        String userPhone = mobileNumber;
        String userId = selectedUserId;
        String type = PreferenceConnector.readString(this, getString(R.string.user_type), "");




        Double grandTotal = totalAdultBaseFare + totalAdultTax + totalChildrenBaseFare + totalChildrenTax + totalInfantBaseFare + totalInfantTax;
        String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        int totalFeeWithTax = 0;
        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (grandTotal - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (grandTotal + markUpFee+ conventionalFee);
        Log.e("totalfee",""+totalFeeWithTax);


        String totalamountTosend = String.valueOf(totalFeeWithTax);
        String totalamountForpayment = String.valueOf(totalFeeWithTax);
        String newsletter = "yes";
        String tandc = "yes";

        Log.e("RoundTrip Domestc","RoundTrip Domestc");
        Log.e("totalamountForpayment",""+totalFeeWithTax);
        Log.e("totalamountTosend",""+totalFeeWithTax);
        Log.e("grandTotal",""+grandTotal);
        Log.e("cv",""+conventionalFee);
        Log.e("markUpFee",""+markUpFee);

        Integer id = 0;
        String flightId = originDestinationOption.getId();
        String key = originDestinationOption.getKey();
        Integer totFirstPrice = Integer.parseInt(""+totalFeeWithTax);
        Integer totSecPrice =Integer.parseInt(""+totalFeeWithTax);

        String actualBaseFare = originDestinationOption.getFareDetails().getActualBaseFare();
        String tax = originDestinationOption.getFareDetails().getTax();
        String sTax = originDestinationOption.getFareDetails().getSTax();
        String tCharge = originDestinationOption.getFareDetails().getTCharge();
        String sCharge = originDestinationOption.getFareDetails().getSCharge();
        String tDiscount = originDestinationOption.getFareDetails().getTDiscount();
        String tMarkup = originDestinationOption.getFareDetails().getTMarkup();
        String tPartnerCommission = originDestinationOption.getFareDetails().getTPartnerCommission();
        String tSdiscount = originDestinationOption.getFareDetails().getTSdiscount();
        String ocTax = originDestinationOption.getFareDetails().getOcTax();

        FareBrk fareBrk = null;
        List<FareBrk> listOfFareBrk = new ArrayList<>();
        String psgrType;
        JsonObject fareAryJsonObject = originDestinationOption.getFareDetails().getFareBreakup().getFareAry().getAsJsonObject();
        JSONObject fareAryJson = null;
        try {
            fareAryJson = new JSONObject(fareAryJsonObject.toString());
            Object object = new JSONTokener(fareAryJson.getString("Fare")).nextValue();
            if (object instanceof JSONObject) {
                com.payz24.responseModels.flightSearchResults.Fare fare = new Gson().fromJson(fareAryJson.getString("Fare"), com.payz24.responseModels.flightSearchResults.Fare.class);
                psgrType = fare.getPsgrType();
                int baseFare = Integer.parseInt(fare.getBaseFare());
                int fareTax = Integer.parseInt(fare.getTax());
                Integer totalFareWithTax = baseFare + fareTax;
                if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                    fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                    fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                    fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                }
                listOfFareBrk.add(fareBrk);
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(fareAryJson.getString("Fare"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    com.payz24.responseModels.flightSearchResults.Fare fare = new Gson().fromJson(jsonArray.getString(index), com.payz24.responseModels.flightSearchResults.Fare.class);
                    psgrType = fare.getPsgrType();
                    int baseFare = Integer.parseInt(fare.getBaseFare());
                    int fareTax = Integer.parseInt(fare.getTax());
                    int totalFareWithTax = baseFare + fareTax;
                    if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                        fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                        fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                        fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                    }
                    listOfFareBrk.add(fareBrk);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listOfLegs = new ArrayList<>();
        listOfRLegs = new ArrayList<>();
        legsObject(originDestinationOption);
        RlegsObject(originDestinationOption);
        String flightMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        SearchData searchData = new SearchData(from, to, fromCode, toCode, sd, ed, adult, child, infant, travelClass, nonStopOnly, journeyType, fromCountry, toCountry, fromAirport, toAirport, fromCity, toCity, searchType);
        PostData postData = new PostData(email, String.valueOf(walletAmount), countryCode, userPhone, userId, type, totalamountTosend, totalamountForpayment, listOfAdultTitle, listOfAdultFirstNames, listOfAdultLastNames, listOfAdultAge, listOfAdultPassportNumber, new ArrayList<String>(), listOfAdultPassportExpiryDate, new ArrayList<String>(), listOfAdultVisaType, newsletter, tandc, useWallet, remainingAmount,
                flightMType, conventionalFee, markUpFee, (totalAdultTax + totalChildrenTax + totalInfantTax), (totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare));
        Fare fare = new Fare(actualBaseFare, tax, sTax, tCharge, sCharge, tDiscount, tMarkup, tPartnerCommission, tSdiscount, ocTax);

        FlightData flightData = new FlightData(id, flightId, key, totFirstPrice, totSecPrice, fare, listOfFareBrk, listOfLegs, listOfRLegs, numStops, numReturnStops);
        InternationalOneWay internationalOneWay = new InternationalOneWay(searchData, postData, flightData);
        String internationalOneWayString = new Gson().toJson(internationalOneWay);
        serviceCallForInternationalFlightBooking(internationalOneWayString);
    }

    private void RlegsObject(OriginDestinationOption originDestinationOption) {
        Rleg rleg;
        listOfRLegs = new ArrayList<>();
        String returnJsonObject = "";
        if (originDestinationOption.getReturnJsonObject() instanceof JSONArray) {

        } else {
            Gson gson = new Gson();
            returnJsonObject = gson.toJson(originDestinationOption.getReturnJsonObject());
        }
        try {
            JSONObject returnJsonObj = new JSONObject(returnJsonObject);
            JSONObject flightsSegmentsJsonObject = new JSONObject(returnJsonObj.getString("FlightSegments"));
            Object jsonReturn = new JSONTokener(flightsSegmentsJsonObject.getString("FlightSegment")).nextValue();
            if (jsonReturn instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightsSegmentsJsonObject.getString("FlightSegment"), FlightSegment.class);
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {
                    airwayName = "N/A";
                } else {
                    airwayName = flightSegment.getOperatingAirlineName().toString();
                    fromAirWayName = airwayName;
                    fromAirwayCode = flightSegment.getOperatingAirlineCode();
                }
                String airEquipType = flightSegment.getAirEquipType();
                String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                String arrivalAirportName = flightSegment.getArrivalAirportName();
                arrivalDateTime = flightSegment.getArrivalDateTime();
                String departureAirportCode = flightSegment.getDepartureAirportCode();
                String departureAirportName = flightSegment.getDepartureAirportName();
                departureDateTime = flightSegment.getDepartureDateTime();
                String flightNumber = flightSegment.getFlightNumber();
                String marketingAirlineCode = flightSegment.getMarketingAirlineCode();
                String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                String operatingAirlineName = airwayName;
                String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                numReturnStops = flightSegment.getNumStops();
                String linkSellAgrmnt = flightSegment.getLinkSellAgrmnt();
                String conx = flightSegment.getConx();
                String airpChg = flightSegment.getAirpChg();
                String insideAvailOption = flightSegment.getInsideAvailOption();
                String genTrafRestriction = flightSegment.getGenTrafRestriction();
                String daysOperates = flightSegment.getDaysOperates();
                String jrnyTm = flightSegment.getJrnyTm();
                String endDt = flightSegment.getEndDt();
                String startTerminal = flightSegment.getStartTerminal();
                String endTerminal = flightSegment.getEndTerminal();
                String fltTm = flightSegment.getFltTm();
                String lSAInd = flightSegment.getLSAInd();
                String mile = flightSegment.getMile();
                String availability = flightSegment.getBookingClass().getAvailability();
                String bIC = flightSegment.getBookingClass().getBIC();
                String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                String classType = flightSegment.getBookingClassFare().getClassType();
                String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                rleg = new Rleg(airEquipType, arrivalAirportCode, arrivalAirportName, arrivalDateTime, departureAirportCode, departureAirportName, departureDateTime, flightNumber, marketingAirlineCode,
                        operatingAirlineCode, operatingAirlineName, operatingAirlineFlightNumber, numStops, linkSellAgrmnt, conx, airpChg, insideAvailOption, genTrafRestriction, daysOperates, jrnyTm,
                        endDt, startTerminal, endTerminal, fltTm, lSAInd, mile, availability, bIC, bookingclass, classType, farebasiscode);
                listOfRLegs.add(rleg);
            } else if (jsonReturn instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightsSegmentsJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    FlightSegment flightSegment = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class);
                    if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                    } else {
                        airwayName = flightSegment.getOperatingAirlineName().toString();
                        fromAirWayName = airwayName;
                        fromAirwayCode = flightSegment.getOperatingAirlineCode();
                    }
                    String airEquipType = flightSegment.getAirEquipType();
                    String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                    String arrivalAirportName = flightSegment.getArrivalAirportName();
                    arrivalDateTime = flightSegment.getArrivalDateTime();
                    String departureAirportCode = flightSegment.getDepartureAirportCode();
                    String departureAirportName = flightSegment.getDepartureAirportName();
                    departureDateTime = flightSegment.getDepartureDateTime();
                    String flightNumber = flightSegment.getFlightNumber();
                    String marketingAirlineCode = flightSegment.getMarketingAirlineCode();
                    String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                    String operatingAirlineName = airwayName;
                    String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                    numReturnStops = flightSegment.getNumStops();
                    String linkSellAgrmnt = flightSegment.getLinkSellAgrmnt();
                    String conx = flightSegment.getConx();
                    String airpChg = flightSegment.getAirpChg();
                    String insideAvailOption = flightSegment.getInsideAvailOption();
                    String genTrafRestriction = flightSegment.getGenTrafRestriction();
                    String daysOperates = flightSegment.getDaysOperates();
                    String jrnyTm = flightSegment.getJrnyTm();
                    String endDt = flightSegment.getEndDt();
                    String startTerminal = flightSegment.getStartTerminal();
                    String endTerminal = flightSegment.getEndTerminal();
                    String fltTm = flightSegment.getFltTm();
                    String lSAInd = flightSegment.getLSAInd();
                    String mile = flightSegment.getMile();
                    String availability = flightSegment.getBookingClass().getAvailability();
                    String bIC = flightSegment.getBookingClass().getBIC();
                    String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                    String classType = flightSegment.getBookingClassFare().getClassType();
                    String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                    rleg = new Rleg(airEquipType, arrivalAirportCode, arrivalAirportName, arrivalDateTime, departureAirportCode, departureAirportName, departureDateTime, flightNumber, marketingAirlineCode,
                            operatingAirlineCode, operatingAirlineName, operatingAirlineFlightNumber, numStops, linkSellAgrmnt, conx, airpChg, insideAvailOption, genTrafRestriction, daysOperates, jrnyTm,
                            endDt, startTerminal, endTerminal, fltTm, lSAInd, mile, availability, bIC, bookingclass, classType, farebasiscode);
                    listOfRLegs.add(rleg);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void internationalOneWayObjects() {
        Double walletAmount = 0.0;
        if (grandTotal != 0.0) {
            if (walletCheckBox.isChecked()) {
                useWallet = "1";
                if (Double.parseDouble(walletBalance) >= grandTotal) {
                    walletAmount = grandTotal;
                    remainingAmount = "0";
                } else {
                    walletAmount = Double.parseDouble(walletBalance);
                    remainingAmount = String.valueOf(grandTotal - Double.parseDouble(walletBalance));
                }
            } else {
                useWallet = "0";
                remainingAmount = "0";
            }
        } else {
            useWallet = "0";
            remainingAmount = "0";
        }
        AvailRequest availRequest = AppController.getInstance().getAvailRequest();
        OriginDestinationOption originDestinationOption = AppController.getInstance().getSelectedOriginDestinationOption();

        String from = sourceFullName;
        String to = destinationFullName;
        String fromCode = availRequest.getOrigin();
        String toCode = availRequest.getDestination();
        String sd = availRequest.getDepartDate();
        String ed = availRequest.getReturnDate();
        String adult = availRequest.getAdultPax();
        String child = availRequest.getChildPax();
        String infant = availRequest.getInfantPax();
        String travelClass = "";
        if (availRequest.getPreferredClass().equalsIgnoreCase("E")) {
            travelClass = "Economy";
        } else {
            travelClass = "Business";
        }
        Integer nonStopOnly = 0;
        String journeyType = "O";
        String fromCountry = AppController.getInstance().getSourceCountry();
        String toCountry = AppController.getInstance().getDestinationCountry();
        String fromAirport = sourceFullName;
        String toAirport = destinationFullName;
        String fromCity = sourceFullName;
        String toCity = destinationFullName;
        String searchType = "owDom";

        String email = emailAddress;
        String countryCode = "91";
        String userPhone = mobileNumber;
        String userId = selectedUserId;
        String type = PreferenceConnector.readString(this, getString(R.string.user_type), "");

        Double grandTotal = totalAdultBaseFare + totalAdultTax + totalChildrenBaseFare + totalChildrenTax + totalInfantBaseFare + totalInfantTax;

        String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        int totalFeeWithTax = 0;
        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (grandTotal - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (grandTotal + markUpFee+ conventionalFee);
        Log.e("totalfee",""+totalFeeWithTax);
        Log.e("conventionalFee",""+conventionalFee);
        Log.e("grandTotal",""+grandTotal);
        Log.e("markUpFee",""+markUpFee);


        String totalamountTosend = String.valueOf(totalFeeWithTax);
        String totalamountForpayment = String.valueOf(totalFeeWithTax);
        String newsletter = "yes";
        String tandc = "yes";


        Integer id = 0;
        String flightId = originDestinationOption.getId();
        String key = originDestinationOption.getKey();
        Integer totFirstPrice = Integer.parseInt(""+totalFeeWithTax);;
        Integer totSecPrice = Integer.parseInt(""+totalFeeWithTax);;

        String actualBaseFare = originDestinationOption.getFareDetails().getActualBaseFare();
        String tax = originDestinationOption.getFareDetails().getTax();
        String sTax = originDestinationOption.getFareDetails().getSTax();
        String tCharge = originDestinationOption.getFareDetails().getTCharge();
        String sCharge = originDestinationOption.getFareDetails().getSCharge();
        String tDiscount = originDestinationOption.getFareDetails().getTDiscount();
        String tMarkup = originDestinationOption.getFareDetails().getTMarkup();
        String tPartnerCommission = originDestinationOption.getFareDetails().getTPartnerCommission();
        String tSdiscount = originDestinationOption.getFareDetails().getTSdiscount();
        String ocTax = originDestinationOption.getFareDetails().getOcTax();


        Log.e("totalamountTosend",totalamountTosend);
        Log.e("totalamountForpayment",totalamountForpayment);
        Log.e("grandTotal",""+grandTotal);
        Log.e("tMarkup",""+tMarkup);
        Log.e("tCharge",""+tCharge);
        Log.e("sCharge",""+sCharge);
        Log.e("cv",""+conventionalFee);
        Log.e("markUpFee",""+markUpFee);

        FareBrk fareBrk = null;
        List<FareBrk> listOfFareBrk = new ArrayList<>();
        String psgrType;
        JsonObject fareAryJsonObject = originDestinationOption.getFareDetails().getFareBreakup().getFareAry().getAsJsonObject();
        JSONObject fareAryJson = null;
        try {
            fareAryJson = new JSONObject(fareAryJsonObject.toString());
            Object object = new JSONTokener(fareAryJson.getString("Fare")).nextValue();
            if (object instanceof JSONObject) {
                com.payz24.responseModels.flightSearchResults.Fare fare = new Gson().fromJson(fareAryJson.getString("Fare"), com.payz24.responseModels.flightSearchResults.Fare.class);
                psgrType = fare.getPsgrType();
                int baseFare = Integer.parseInt(fare.getBaseFare());
                int fareTax = Integer.parseInt(fare.getTax());
                Integer totalFareWithTax = baseFare + fareTax;
                if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                    fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                    fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                    fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                }
                listOfFareBrk.add(fareBrk);
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(fareAryJson.getString("Fare"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    com.payz24.responseModels.flightSearchResults.Fare fare = new Gson().fromJson(jsonArray.getString(index), com.payz24.responseModels.flightSearchResults.Fare.class);
                    psgrType = fare.getPsgrType();
                    int baseFare = Integer.parseInt(fare.getBaseFare());
                    int fareTax = Integer.parseInt(fare.getTax());
                    int totalFareWithTax = baseFare + fareTax;
                    if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                        fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                        fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                        fareBrk = new FareBrk(psgrType, String.valueOf(baseFare), String.valueOf(fareTax), totalFareWithTax);
                    }
                    listOfFareBrk.add(fareBrk);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listOfLegs = new ArrayList<>();
        legsObject(originDestinationOption);
        String flightMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        SearchData searchData = new SearchData(from, to, fromCode, toCode, sd, ed, adult, child, infant, travelClass, nonStopOnly, journeyType, fromCountry, toCountry, fromAirport, toAirport, fromCity, toCity, searchType);
        PostData postData = new PostData(email, String.valueOf(walletAmount), countryCode, userPhone, userId, type, totalamountTosend, totalamountForpayment, listOfAdultTitle, listOfAdultFirstNames, listOfAdultLastNames, listOfAdultAge, listOfAdultPassportNumber, new ArrayList<String>(), listOfAdultPassportExpiryDate, new ArrayList<String>(), listOfAdultVisaType, newsletter, tandc,
                useWallet, remainingAmount, flightMType, conventionalFee, markUpFee, (totalAdultTax + totalChildrenTax + totalInfantTax), (totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare));
        Fare fare = new Fare(actualBaseFare, tax, sTax, tCharge, sCharge, tDiscount, tMarkup, tPartnerCommission, tSdiscount, ocTax);

        FlightData flightData = new FlightData(id, flightId, key, totFirstPrice, totSecPrice, fare, listOfFareBrk, listOfLegs, new ArrayList<Rleg>(), numStops, "");
        InternationalOneWay internationalOneWay = new InternationalOneWay(searchData, postData, flightData);
        String internationalOneWayString = new Gson().toJson(internationalOneWay);
        serviceCallForInternationalFlightBooking(internationalOneWayString);
    }

    private void legsObject(OriginDestinationOption originDestinationOption) {
        Leg leg;
        listOfLegs = new ArrayList<>();
        JsonObject jsonObject = originDestinationOption.getOnward().flightSegments.getAsJsonObject();
        try {
            JSONObject flightSegmentsJsonObject = new JSONObject(jsonObject.toString());
            Object json = new JSONTokener(flightSegmentsJsonObject.getString("FlightSegment")).nextValue();
            if (json instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightSegmentsJsonObject.getString("FlightSegment"), FlightSegment.class);
                //String totalJourneyTime = flightSegment.getJrnyTm();
                // String[] departureDateTime = flightSegment.getDepartureDateTime().split("T");
                // String[] arrivalDateTime = flightSegment.getArrivalDateTime().split("T");
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getOperatingAirlineName().toString();
                    toAirWayName = airwayName;
                    toOperatingAirwayCode = flightSegment.getOperatingAirlineCode();
                    operatingAirwayCode = toOperatingAirwayCode;
                }
                String airEquipType = flightSegment.getAirEquipType();
                String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                String arrivalAirportName = flightSegment.getArrivalAirportName();
                arrivalDateTime = flightSegment.getArrivalDateTime();
                String departureAirportCode = flightSegment.getDepartureAirportCode();
                String departureAirportName = flightSegment.getDepartureAirportName();
                departureDateTime = flightSegment.getDepartureDateTime();
                String flightNumber = flightSegment.getFlightNumber();
                String marketingAirlineCode = flightSegment.getMarketingAirlineCode();
                String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                String operatingAirlineName = airwayName;
                String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                numStops = flightSegment.getNumStops();
                String linkSellAgrmnt = flightSegment.getLinkSellAgrmnt();
                String conx = flightSegment.getConx();
                String airpChg = flightSegment.getAirpChg();
                String insideAvailOption = flightSegment.getInsideAvailOption();
                String genTrafRestriction = flightSegment.getGenTrafRestriction();
                String daysOperates = flightSegment.getDaysOperates();
                String jrnyTm = flightSegment.getJrnyTm();
                String endDt = flightSegment.getEndDt();
                String startTerminal = flightSegment.getStartTerminal();
                String endTerminal = flightSegment.getEndTerminal();
                String fltTm = flightSegment.getFltTm();
                String lSAInd = flightSegment.getLSAInd();
                String mile = flightSegment.getMile();
                String availability = flightSegment.getBookingClass().getAvailability();
                String bIC = flightSegment.getBookingClass().getBIC();
                String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                String classType = flightSegment.getBookingClassFare().getClassType();
                String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                leg = new Leg(airEquipType, arrivalAirportCode, arrivalAirportName, arrivalDateTime, departureAirportCode, departureAirportName, departureDateTime, flightNumber, marketingAirlineCode,
                        operatingAirlineCode, operatingAirlineName, operatingAirlineFlightNumber, numStops, linkSellAgrmnt, conx, airpChg, insideAvailOption, genTrafRestriction, daysOperates, jrnyTm,
                        endDt, startTerminal, endTerminal, fltTm, lSAInd, mile, availability, bIC, bookingclass, classType, farebasiscode);
                listOfLegs.add(leg);
            } else if (json instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentsJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    FlightSegment flightSegment = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class);
                    if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                    } else {
                        airwayName = flightSegment.getOperatingAirlineName().toString();
                        toAirWayName = airwayName;
                        toOperatingAirwayCode = flightSegment.getOperatingAirlineCode();
                        operatingAirwayCode = toOperatingAirwayCode;
                    }
                    String airEquipType = flightSegment.getAirEquipType();
                    String arrivalAirportCode = flightSegment.getArrivalAirportCode();
                    String arrivalAirportName = flightSegment.getArrivalAirportName();
                    arrivalDateTime = flightSegment.getArrivalDateTime();
                    String departureAirportCode = flightSegment.getDepartureAirportCode();
                    String departureAirportName = flightSegment.getDepartureAirportName();
                    departureDateTime = flightSegment.getDepartureDateTime();
                    String flightNumber = flightSegment.getFlightNumber();
                    String marketingAirlineCode = flightSegment.getMarketingAirlineCode();
                    String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
                    String operatingAirlineName = airwayName;
                    String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
                    numStops = flightSegment.getNumStops();
                    String linkSellAgrmnt = flightSegment.getLinkSellAgrmnt();
                    String conx = flightSegment.getConx();
                    String airpChg = flightSegment.getAirpChg();
                    String insideAvailOption = flightSegment.getInsideAvailOption();
                    String genTrafRestriction = flightSegment.getGenTrafRestriction();
                    String daysOperates = flightSegment.getDaysOperates();
                    String jrnyTm = flightSegment.getJrnyTm();
                    String endDt = flightSegment.getEndDt();
                    String startTerminal = flightSegment.getStartTerminal();
                    String endTerminal = flightSegment.getEndTerminal();
                    String fltTm = flightSegment.getFltTm();
                    String lSAInd = flightSegment.getLSAInd();
                    String mile = flightSegment.getMile();
                    String availability = flightSegment.getBookingClass().getAvailability();
                    String bIC = flightSegment.getBookingClass().getBIC();
                    String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
                    String classType = flightSegment.getBookingClassFare().getClassType();
                    String farebasiscode = StringEscapeUtils.escapeJava(flightSegment.getBookingClassFare().getFarebasiscode());
                    leg = new Leg(airEquipType, arrivalAirportCode, arrivalAirportName, arrivalDateTime, departureAirportCode, departureAirportName, departureDateTime, flightNumber, marketingAirlineCode,
                            operatingAirlineCode, operatingAirlineName, operatingAirlineFlightNumber, numStops, linkSellAgrmnt, conx, airpChg, insideAvailOption, genTrafRestriction, daysOperates, jrnyTm,
                            endDt, startTerminal, endTerminal, fltTm, lSAInd, mile, availability, bIC, bookingclass, classType, farebasiscode);
                    listOfLegs.add(leg);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void serviceCallForInternationalFlightBooking(String internationalOneWayString) {
        if (networkDetection.isMobileNetworkAvailable(this) || networkDetection.isWifiAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.FLIGHT_TICKET_BOOKING_DETAILS, internationalOneWayString);
            InternationalOneWayHttpClient internationalOneWayHttpClient = new InternationalOneWayHttpClient(this);
            internationalOneWayHttpClient.callBack = this;
            internationalOneWayHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }


    private void serviceCallForDomesticFlightBooking(String domesticFlightBookingString) {
        if (networkDetection.isMobileNetworkAvailable(this) || networkDetection.isWifiAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.FLIGHT_TICKET_BOOKING_DETAILS, domesticFlightBookingString);
            DomesticFlightBookingHttpClient domesticFlightBookingHttpClient = new DomesticFlightBookingHttpClient(this);
            domesticFlightBookingHttpClient.callBack = this;
            domesticFlightBookingHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void showInformationAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alertDialogBuilder.setTitle(getResources().getString(R.string.information));
        alertDialogBuilder.setMessage(getResources().getString(R.string.flight_info));
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void adultPersonalDetails(int position, String selectedRadioButtonText, String name, String middleName, String lastName, String dateOfBirth, String passport, String expiryDate, String visaType) {
        if (listOfAdultFirstNames.size() != position) {
            listOfAdultTitle.remove(position);
            listOfAdultFirstNames.remove(position);
            listOfAdultLastNames.remove(position);
            listOfAdultAge.remove(position);
            listOfAdultPassportNumber.remove(position);
            listOfAdultVisaType.remove(position);
            listOfAdultPassportExpiryDate.remove(position);
        }
        listOfAdultTitle.add(selectedRadioButtonText);
        listOfAdultFirstNames.add(name);
        listOfAdultLastNames.add(lastName);
        listOfAdultAge.add(dateOfBirth);
        listOfAdultPassportNumber.add(passport);
        listOfAdultVisaType.add(visaType);
        listOfAdultPassportExpiryDate.add(expiryDate);
    }

    @Override
    public void childrenPersonalDetails(int position, String selectedRadioButtonText, String name, String middleName, String lastName, String dateOfBirth, String passport, String expiryDate, String visaType) {
        if (listOfChildrenTitle.size() != position) {
            listOfChildrenTitle.remove(position);
            listOfChildrenFirstNames.remove(position);
            listOfChildrenLastNames.remove(position);
            listOfChildrenAge.remove(position);
            listOfChildrenPassportNumber.remove(position);
            listOfChildrenVisaType.remove(position);
            listOfChildrenPassportExpiryDate.remove(position);
        }
        listOfChildrenTitle.add(selectedRadioButtonText);
        listOfChildrenFirstNames.add(name);
        listOfChildrenLastNames.add(lastName);
        listOfChildrenAge.add(dateOfBirth);
        listOfChildrenPassportNumber.add(passport);
        listOfChildrenVisaType.add(visaType);
        listOfChildrenPassportExpiryDate.add(expiryDate);
    }

    @Override
    public void infantPersonalDetails(int position, String selectedRadioButtonText, String name, String middleName, String lastName, String dateOfBirth, String passport, String expiryDate, String visaType) {
        if (listOfInfantTitle.size() != position) {
            listOfInfantTitle.remove(position);
            listOfInfantFirstNames.remove(position);
            listOfInfantLastNames.remove(position);
            listOfInfantAge.remove(position);
            listOfInfantPassportNumber.remove(position);
            listOfInfantVisaType.remove(position);
            listOfInfantPassportExpiryDate.remove(position);
        }
        listOfInfantTitle.add(selectedRadioButtonText);
        listOfInfantFirstNames.add(name);
        listOfInfantLastNames.add(lastName);
        listOfInfantAge.add(dateOfBirth);
        listOfInfantPassportNumber.add(passport);
        listOfInfantVisaType.add(visaType);
        listOfInfantPassportExpiryDate.add(expiryDate);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_INTERNATIONAL_FLIGHTS_ONE_WAY:
                if (jsonResponse != null) {
                    if (statusCode == 200) {
                        //Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            JSONObject resultJsonObject = new JSONObject(jsonObject.getString("result"));
                            if (resultJsonObject.has("status")) {
                                status = resultJsonObject.getString("status");
                            }
                            if (resultJsonObject.has("transid")) {
                                transid = resultJsonObject.getString("transid");
                            }
                            if (resultJsonObject.has("PartnerreferenceID")) {
                                partnerreferenceID = resultJsonObject.getString("PartnerreferenceID");
                            }
                            if (!status.equalsIgnoreCase("fail"))
                                serviceCallForSMSGateWay(status, transid, partnerreferenceID);
                            else {
                                Intent dashBoardIntent = new Intent(this, DashboardActivity.class);
                                dashBoardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle bundle = new Bundle();
                                bundle.putString(getString(R.string.to), getString(R.string.flights));
                                dashBoardIntent.putExtras(bundle);
                                startActivity(dashBoardIntent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            closeProgressbar();
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.SERVICE_DOMESTIC_FLIGHT_BOOKING:
                if (jsonResponse != null) {
                    if (statusCode == 200) {
                        //Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                        Log.e("jsonRespnse",jsonResponse);
                        try {
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            JSONObject resultJsonObject = new JSONObject(jsonObject.getString("result"));
                            if (resultJsonObject.has("status")) {
                                status = resultJsonObject.getString("status");
                            }
                            if (resultJsonObject.has("transid")) {
                                transid = resultJsonObject.getString("transid");
                            }
                            if (resultJsonObject.has("partnerRefId")) {
                                partnerreferenceID = resultJsonObject.getString("partnerRefId");
                            }
                            if (resultJsonObject.has("error")) {
                                try {
                                    error = resultJsonObject.getString("error");
                                } catch (Exception exception) {
                                    error = "";
                                    exception.printStackTrace();
                                }

                            }
                            if (!jsonObject.getString("status").equalsIgnoreCase("failed")) {
                                serviceCallForSMSGateWay(status, transid, partnerreferenceID);
                            } else {
                                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                                Intent dashBoardIntent = new Intent(this, DashboardActivity.class);
                                dashBoardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle bundle = new Bundle();
                                bundle.putString(getString(R.string.to), getString(R.string.flights));
                                dashBoardIntent.putExtras(bundle);
                                startActivity(dashBoardIntent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.SERVICE_SMS:
                if (jsonResponse != null) {
                    if (status.equalsIgnoreCase("success")) {
                        goToFlightConformationTicketScreen(transid, partnerreferenceID);
                        Toast.makeText(this, getString(R.string.ticket_booked_successfully), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getString(R.string.ticket_booking_failed), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            case Constants.SERVICE_PROFILE:
                if (jsonResponse != null) {
                    ProfileResponse profileResponse = new Gson().fromJson(jsonResponse, ProfileResponse.class);
                    if (profileResponse != null) {
                        if (profileResponse.getResult().getWalletAmt() != null)
                            walletBalance = profileResponse.getResult().getWalletAmt();
                        tvWalletBalance.setText(walletBalance);
                        walletCheckBox.setChecked(true);
                    }
                }
                closeProgressbar();
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void serviceCallForSMSGateWay(String status, String transid, String partnerreferenceID) {
        int numberOfPassengers = numberOfAdults + numberOfChildren + numberOfInfants;
        String[] animalsArray = departureDateTime.split("T");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format1.parse(animalsArray[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(format2.format(date));

        String s=format2.format(date)+" "+animalsArray[1];

        String message = "Dear Customer PNR " + " " + transid + " (Check e-ticket for Airlines PNR) Flight" + airwayName + " Source " + " " + sourceFullName + " Destination " + " " + destinationFullName +
                ". Journey date" + " " + s + ". No of passenger " + " " + String.valueOf(numberOfPassengers) + " " + ". Happy Journey From https://payz24.com";

      /*  String message = "Dear Customer PNR " + " " + transid + " Flight " + airwayName + "\nSource " + " " + sourceFullName + " Destination\n " + " " + destinationFullName +
                " Journey date" + " " + departureDateTime + "\n No of passenger" + " "
                + String.valueOf(numberOfPassengers) + " " + "\nHappy Journey "+"\nFor More Details Visit\n" +
                "https://payz24.com\n";*/
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SMS_PARAM_PHONE, etMobileNumber.getText().toString());
        if (status.equalsIgnoreCase("success")) {
            mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, message);
        } else {
            mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, "Thanks for using Flight facilities and your order id is" + " " + transid + " payment got failed for amount " + String.valueOf(grandTotal));
        }
        SmsHttpClient smsHttpClient = new SmsHttpClient(this);
        smsHttpClient.callBack = this;
        smsHttpClient.getJsonForType(mapOfRequestParams);
    }

    private void goToFlightConformationTicketScreen(String transid, String partnerreferenceID) {
        Intent conformationIntent = new Intent(FlightTravellerDetailsActivity.this, FlightConformationTicketScreen.class);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.source), sourceFullName);
        bundle.putString(getString(R.string.destination), destinationFullName);
        bundle.putDouble(getString(R.string.total_adult_base_fare), totalAdultBaseFare);
        bundle.putDouble(getString(R.string.total_adult_tax), totalAdultTax);
        bundle.putDouble(getString(R.string.total_children_base_fare), totalChildrenBaseFare);
        bundle.putDouble(getString(R.string.total_children_tax), totalChildrenTax);
        bundle.putDouble(getString(R.string.total_infant_base_fare), totalInfantBaseFare);
        bundle.putDouble(getString(R.string.total_infant_tax), totalInfantTax);
        bundle.putString(getString(R.string.source_country), sourceCountryName);
        bundle.putString(getString(R.string.destination_country), destinationCountryName);
        bundle.putInt(getString(R.string.adult_count), numberOfAdults);
        bundle.putInt(getString(R.string.children_count), numberOfChildren);
        bundle.putInt(getString(R.string.infant_count), numberOfInfants);
        if (from.equalsIgnoreCase(getString(R.string.international_round_trip)) || from.equalsIgnoreCase(getString(R.string.domestic_round_trip))) {
            bundle.putString(getString(R.string.toAirwayName), toAirWayName);
            bundle.putString(getString(R.string.toAirwayCode), toOperatingAirwayCode);
            bundle.putString(getString(R.string.fromAirwayName), fromAirWayName);
            bundle.putString(getString(R.string.fromAirwayCode), fromAirwayCode);
        } else {
            bundle.putString(getString(R.string.airway_name), airwayName);
            bundle.putString(getString(R.string.airway_code), operatingAirwayCode);
        }

       Log.e(getString(R.string.toAirwayName), toAirWayName);
       Log.e(getString(R.string.toAirwayCode), toOperatingAirwayCode);
       Log.e(getString(R.string.fromAirwayName), fromAirWayName);
       Log.e(getString(R.string.fromAirwayCode), fromAirwayCode);
       Log.e(getString(R.string.airway_name), airwayName);
       Log.e(getString(R.string.airway_code), operatingAirwayCode);



        bundle.putString(getString(R.string.transid), transid);
        bundle.putString(getString(R.string.partnerreferenceID), partnerreferenceID);
        conformationIntent.putExtras(bundle);
        startActivity(conformationIntent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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

    public static boolean isEditTextContainEmail(String argEditText) {

        try {

            Pattern pattern = Pattern.compile("[a-zA-Z0-9.\\-_]{2,32}@[a-zA-Z0-9.\\-_]{2,32}\\.[A-Za-z]{2,4}");
            Matcher matcher = pattern.matcher(argEditText);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
