package com.payz24.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.http.BlockBusTicketHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.availableBuses.BoardingPoint;
import com.payz24.responseModels.blockTicket.BlockSeatPaxDetail;
import com.payz24.responseModels.blockTicket.BlockTicket;
import com.payz24.responseModels.blockTicket.DroppingPoint;
import com.payz24.responseModels.blockTicketResponse.BlockTicketResponse;
import com.payz24.responseModels.busLayout.Seat;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mahesh on 1/30/2018.
 */

public class PassengerDetails extends BaseActivity implements View.OnClickListener, HttpReqResCallBack {
    String isMaleSelected = "false",isFemaleSelected="false";

    private TextInputLayout inputLayoutEmail, inputLayoutMobileNumber, inputLayoutFullName, inputLayoutAge,inputLayoutCouponCode;
    private EditText etEmailAddress, etMobileNumber, etFullName, etAge,etCouponCode;
    TextView tvNetPayableValue,tvWalletBalance;
    private ImageView ivMale, ivFemale;
    private LinearLayout llContainer;
    private TextView tvContinue;
    private NetworkDetection networkDetection;
    private String arrivalTime = "";
    private String goingToStationName = "";
    private String leavingFromStationName = "";
    private String travellingDate = "";
    private String travelsName = "";
    private String fullName = "";
    private String emailAddress = "";
    private String mobileNumber = "";
    private String age = "";
    private String seatName = "";
    private List<String> listOfSeatNames;
    private LinkedList<TextInputLayout> listOfTextInputLayoutFullNames;
    private LinkedList<TextInputLayout> listOfTextInputLayoutAge;
    private LinkedList<EditText> listOfEditTextNames;
    private LinkedList<EditText> listOfEditTextAge;
    private LinkedList<ImageView> listOfImageViewMale;
    private LinkedList<ImageView> listOfImageViewFemale;
    private LinkedHashMap<String, String> mapOfSeatNameFullName;
    private LinkedHashMap<String, String> mapOfSeatNameAge;
    private boolean isAllFieldsFilled;
    private LinkedHashMap<String, String> mapOfSeatNameGender;
    private BlockTicket blockTicket;
    private String blockTicketString = "";
    private CheckBox walletCheckBox;
    private int totalFeeWithConventionalTax = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_passenger_details);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        prepareNames();
    }

    private void getDataFromIntent() {
        if (getIntent() != null) {
            arrivalTime = getIntent().getStringExtra(getString(R.string.arrival_time));
            leavingFromStationName = getIntent().getStringExtra(getString(R.string.leaving_from));
            goingToStationName = getIntent().getStringExtra(getString(R.string.going_to));
            travellingDate = getIntent().getStringExtra(getString(R.string.doj));
            travelsName = getIntent().getStringExtra(getString(R.string.travels_names));
            if (getIntent().hasExtra(getString(R.string.seat_names))) {
                seatName = getIntent().getStringExtra(getString(R.string.seat_names));
                listOfSeatNames = Arrays.asList(seatName.split("\\s*,\\s*"));
            } else {
                seatName = "";
            }
        }
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.passenger_details));
        setSupportActionBar(toolbar);

        inputLayoutEmail = findViewById(R.id.inputLayoutEmail);
        inputLayoutMobileNumber = findViewById(R.id.inputLayoutMobileNumber);
        inputLayoutCouponCode = findViewById(R.id.inputLayoutCouponCode);
        etEmailAddress = findViewById(R.id.etEmailAddress);

        etMobileNumber = findViewById(R.id.etMobileNumber);
        etCouponCode = findViewById(R.id.etCouponCode);
        tvContinue = findViewById(R.id.tvContinue);
        llContainer = findViewById(R.id.llContainer);


        networkDetection = new NetworkDetection();

        String emailId = PreferenceConnector.readString(this,getString(R.string.user_email),"");
        String mobileNumber = PreferenceConnector.readString(this,getString(R.string.user_mobile_number),"");
        etEmailAddress.setText(emailId);
        etMobileNumber.setText(mobileNumber);
    }

    private void initializeListeners() {
        tvContinue.setOnClickListener(this);

    }

    private void prepareNames() {
        listOfImageViewMale = new LinkedList<>();
        listOfImageViewFemale = new LinkedList<>();
        listOfTextInputLayoutAge = new LinkedList<>();
        listOfTextInputLayoutFullNames = new LinkedList<>();
        listOfEditTextNames = new LinkedList<>();
        listOfEditTextAge = new LinkedList<>();
        isAllFieldsFilled = false;

        List<Seat> listOfSelectedSeatNames = AppController.getInstance().getListOfSelectedSeat();
        if(listOfSelectedSeatNames != null && listOfSelectedSeatNames.size() != 0) {
            for (int index = 0; index < listOfSelectedSeatNames.size(); index++) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.layout_passanger_details_names, null);

                inputLayoutFullName = view.findViewById(R.id.inputLayoutFullName);
                inputLayoutAge = view.findViewById(R.id.inputLayoutAge);
                etFullName = view.findViewById(R.id.etFullName);
                etAge = view.findViewById(R.id.etAge);
                ivMale = view.findViewById(R.id.ivMale);
                ivFemale = view.findViewById(R.id.ivFemale);
                etFullName.setTag(listOfSelectedSeatNames.get(index).getId());


                if (listOfSelectedSeatNames.get(index).getLadiesSeat()==true)
                {
                    ivMale.setVisibility(View.GONE);
                }else
                {
                    ivMale.setVisibility(View.VISIBLE);
                    ivFemale.setVisibility(View.VISIBLE);
                }
                listOfTextInputLayoutFullNames.add(inputLayoutFullName);
                listOfTextInputLayoutAge.add(inputLayoutAge);
                listOfEditTextNames.add(etFullName);
                listOfEditTextAge.add(etAge);
                listOfImageViewMale.add(ivMale);
                listOfImageViewFemale.add(ivFemale);

                ivMale.setTag(index + "&&" + "");
                ivFemale.setTag(index + "&&" + "");
                ivMale.setOnClickListener(this);
                ivFemale.setOnClickListener(this);
                llContainer.addView(view);
            }
        }
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivMale:
                String[] malePositionName = view.getTag().toString().split("&&");
                ImageView ivMale = listOfImageViewMale.get(Integer.parseInt(malePositionName[0]));
                ImageView ivFemale = listOfImageViewFemale.get(Integer.parseInt(malePositionName[0]));
                ivMale.setTag(malePositionName[0] + "&&" + "true");
                ivFemale.setTag(malePositionName[0] + "&&" + "false");
                ivMale.setImageResource(R.drawable.ic_boy_selected);
                ivFemale.setImageResource(R.drawable.ic_girl_unselected);
                break;
            case R.id.ivFemale:
                String[] femalePositionName = view.getTag().toString().split("&&");
                ImageView ivMaleSel = listOfImageViewMale.get(Integer.parseInt(femalePositionName[0]));
                ImageView ivFemaleSel = listOfImageViewFemale.get(Integer.parseInt(femalePositionName[0]));
                ivFemaleSel.setTag(femalePositionName[0] + "&&" + "true");
                ivMaleSel.setTag(femalePositionName[0] + "&&" + "false");
                ivMaleSel.setImageResource(R.drawable.ic_boy_unselected);
                ivFemaleSel.setImageResource(R.drawable.ic_girl_selected);
                break;
            case R.id.tvContinue:
               try {
                   emailAddress = etEmailAddress.getText().toString();
                   mobileNumber = etMobileNumber.getText().toString();
                   isAllFieldsFilled = false;
                   if (!emailAddress.isEmpty()) {
                       inputLayoutEmail.setError("");
                       inputLayoutEmail.setErrorEnabled(false);
                       if (!mobileNumber.isEmpty()) {

                           if (isValidMobile(etMobileNumber.getText().toString().trim()))
                           {


                               if (isEditTextContainEmail(etEmailAddress.getText().toString().trim()))
                               {
                                   inputLayoutMobileNumber.setError("");
                                   inputLayoutMobileNumber.setErrorEnabled(true);
                                   mapOfSeatNameFullName = new LinkedHashMap<>();
                                   mapOfSeatNameAge = new LinkedHashMap<>();
                                   mapOfSeatNameGender = new LinkedHashMap<>();
                                   for (int index = 0; index < listOfTextInputLayoutFullNames.size(); index++) {
                                       TextInputLayout inputLayoutFullName = listOfTextInputLayoutFullNames.get(index);
                                       TextInputLayout inputLayoutAge = listOfTextInputLayoutAge.get(index);
                                       EditText etFullName = listOfEditTextNames.get(index);
                                       EditText etAge = listOfEditTextAge.get(index);
                                       ImageView ivBoy = listOfImageViewMale.get(index);
                                       ImageView ivGirl = listOfImageViewFemale.get(index);
                                       fullName = etFullName.getText().toString();
                                       age = etAge.getText().toString();
                                       String seatName = (String) etFullName.getTag();
                                       mapOfSeatNameFullName.put(seatName, fullName);
                                       mapOfSeatNameAge.put(seatName, age);
                                       if (!fullName.isEmpty()) {
                                           inputLayoutFullName.setError("");
                                           inputLayoutFullName.setErrorEnabled(false);
                                           if (!age.isEmpty()) {
                                               inputLayoutAge.setError("");
                                               inputLayoutAge.setErrorEnabled(false);
                                               String[] maleSelectedName = ivBoy.getTag().toString().split("&&");
                                               String[] femaleSelectedName = ivGirl.getTag().toString().split("&&");
                                               isMaleSelected = maleSelectedName[1];
                                               isFemaleSelected = femaleSelectedName[1];
                                               if (isMaleSelected.equalsIgnoreCase("true"))
                                                   mapOfSeatNameGender.put(seatName, "M");
                                               else
                                                   mapOfSeatNameGender.put(seatName, "F");
                                               if (isMaleSelected.equalsIgnoreCase("true") || isFemaleSelected.equalsIgnoreCase("true")) {
                                                   isAllFieldsFilled = true;
                                               } else if (isMaleSelected.equalsIgnoreCase("false") || isFemaleSelected.equalsIgnoreCase("false")) {
                                                   Toast.makeText(this, getString(R.string.please_select_gender), Toast.LENGTH_SHORT).show();
                                               }
                                           } else {
                                               inputLayoutAge.setError(getString(R.string.please_enter_age));
                                               inputLayoutAge.setErrorEnabled(true);
                                           }
                                       } else {
                                           inputLayoutFullName.setError(getString(R.string.please_enter_full_name));
                                           inputLayoutFullName.setErrorEnabled(true);
                                       }
                                   }

                               }

                               else
                               {
                                   inputLayoutEmail.setError("Please enter valid email");
                                   inputLayoutEmail.setErrorEnabled(true);
                               }



                           }else
                           {
                               inputLayoutMobileNumber.setError("Please enter 10 digit number");
                               inputLayoutMobileNumber.setErrorEnabled(true);
                           }

                       } else {
                           inputLayoutMobileNumber.setError(getString(R.string.please_enter_mobile_number));
                           inputLayoutMobileNumber.setErrorEnabled(true);
                       }
                   } else {
                       inputLayoutEmail.setError(getString(R.string.please_enter_email_id));
                       inputLayoutEmail.setErrorEnabled(true);
                   }
                   if (isAllFieldsFilled) {
                       showProgressBar();
                       serviceCallForBusBooking();
                   }
               }catch (Exception e)
               {
                   e.printStackTrace();
                   isMaleSelected="false";
                   isFemaleSelected="false";
                   Toast.makeText(this, getString(R.string.please_select_gender), Toast.LENGTH_SHORT).show();
               }
                break;
            default:
                break;
        }
    }

    private void serviceCallForBusBooking() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            prepareJsonObject();
            AppController.getInstance().setBlockTicket(blockTicket);
            Map<String, String> mapOfRequestParams = new HashMap<>();
            blockTicketString = new Gson().toJson(blockTicket);
            mapOfRequestParams.put(Constants.BLOCK_TICKET_PARAM_API_Block_TICKET_REQUEST, blockTicketString);
            BlockBusTicketHttpClient blockBusTicketHttpClient = new BlockBusTicketHttpClient(this);
            blockBusTicketHttpClient.callBack = this;
            blockBusTicketHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            closeProgressbar();
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void prepareJsonObject() {
        ApiAvailableBus apiAvailableBus = AppController.getInstance().getSelectedAvailableBus();
        BoardingPoint selectedBoardingPoint = AppController.getInstance().getSelectedBoardingPoint();
        String sourceCity = leavingFromStationName;
        String destinationCity = goingToStationName;
        String dateOfJourney = travellingDate;
        String routeScheduleId = apiAvailableBus.getRouteScheduleId();

        String boardingPointId = selectedBoardingPoint.getId();
        String boardingPointLocation = selectedBoardingPoint.getLocation();
        String boardingTime = selectedBoardingPoint.getTime();

        String droppingPointId = "1";
        String droppingPointLocation = goingToStationName;
        String droppingTime = apiAvailableBus.getArrivalTime();

        String customerName = fullName;
        String customerLastName = "";
        String customerEmail = emailAddress;
        String customerPhone = mobileNumber;
        String emergencyPhNumber = mobileNumber;
        String customerAddress = "";
        int inventoryType = apiAvailableBus.getInventoryType();
        List<BlockSeatPaxDetail> listOfBlockSeatPaxDetails = new ArrayList<>();
        List<Seat> listOfSelectedSeatNames = AppController.getInstance().getListOfSelectedSeat();
        for (int selectedSeatIndex = 0; selectedSeatIndex < listOfSelectedSeatNames.size(); selectedSeatIndex++) {
            String age = mapOfSeatNameAge.get(listOfSelectedSeatNames.get(selectedSeatIndex).getId());
            String name = mapOfSeatNameFullName.get(listOfSelectedSeatNames.get(selectedSeatIndex).getId());
            String seatNbr = listOfSelectedSeatNames.get(selectedSeatIndex).getId();
            String sex = mapOfSeatNameGender.get(listOfSelectedSeatNames.get(selectedSeatIndex).getId());
            float fare = listOfSelectedSeatNames.get(selectedSeatIndex).getFare();
            Double serviceTaxAmount = listOfSelectedSeatNames.get(selectedSeatIndex).getServiceTaxAmount();
            Double operatorServiceChargeAbsolute = listOfSelectedSeatNames.get(selectedSeatIndex).getOperatorServiceChargeAbsolute();
            Double totalFareWithTaxes = listOfSelectedSeatNames.get(selectedSeatIndex).getTotalFareWithTaxes();
            Boolean ladiesSeat = listOfSelectedSeatNames.get(selectedSeatIndex).getLadiesSeat();
            String lastName = "";
            String mobile = mobileNumber;
            String title = "";
            if (sex.equalsIgnoreCase("M")) {
                title = "Mr";
            } else if (sex.equalsIgnoreCase("F")) {
                title = "Miss";
            }
            String email = emailAddress;
            String idType = "";
            String idNumber = "";
            String nameOnId = mapOfSeatNameFullName.get(listOfSelectedSeatNames.get(selectedSeatIndex).getId());
            Boolean primary;
            if (selectedSeatIndex == 0)
                primary = true;
            else
                primary = false;
            Boolean ac = listOfSelectedSeatNames.get(selectedSeatIndex).getAc();
            Boolean sleeper = listOfSelectedSeatNames.get(selectedSeatIndex).getSleeper();
            BlockSeatPaxDetail blockSeatPaxDetail = new BlockSeatPaxDetail(age, name, seatNbr, sex, fare, serviceTaxAmount, operatorServiceChargeAbsolute, totalFareWithTaxes, ladiesSeat, lastName, mobile, title,
                    email, idType, idNumber, nameOnId, primary, ac, sleeper);
            listOfBlockSeatPaxDetails.add(blockSeatPaxDetail);
        }
        com.payz24.responseModels.blockTicket.BoardingPoint boardingPoint = new com.payz24.responseModels.blockTicket.BoardingPoint(boardingPointId, boardingPointLocation, boardingTime);
        DroppingPoint droppingPoint = new DroppingPoint(droppingPointId, droppingPointLocation, droppingTime);
        blockTicket = new BlockTicket(sourceCity, destinationCity, dateOfJourney, routeScheduleId, boardingPoint, droppingPoint, customerName, customerLastName, customerEmail,
                customerPhone, emergencyPhNumber, customerAddress, listOfBlockSeatPaxDetails, inventoryType);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_BLOCK_BUS_TICKET:
                if (statusCode == Constants.STATUS_CODE_SUCCESS) {
                    if (jsonResponse != null) {
                        BlockTicketResponse blockTicketResponse = new Gson().fromJson(jsonResponse, BlockTicketResponse.class);
                        boolean status = blockTicketResponse.getApiStatus().getSuccess();
                        String message = blockTicketResponse.getApiStatus().getMessage();
                        if (status) {
                            int inventoryType = blockTicketResponse.getInventoryType();
                            String blockTicketKey = blockTicketResponse.getBlockTicketKey();
                            goToPaymentDetails(inventoryType,blockTicketKey);
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
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

    private void goToPaymentDetails(int inventoryType, String blockTicketKey) {
        Intent intent = new Intent(this,BusPaymentDetails.class);
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.inventory_type),inventoryType);
        bundle.putString(getString(R.string.block_ticket_key),blockTicketKey);
        bundle.putString(getString(R.string.block_ticket_string),blockTicketString);
        intent.putExtras(bundle);
        startActivity(intent);
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
