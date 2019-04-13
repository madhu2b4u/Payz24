package com.payz24.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.http.BusSeatBookingHttpClient;
import com.payz24.http.ProfileHttpClient;
import com.payz24.http.SmsHttpClient;
import com.payz24.http.StoreBusDetailsHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.BusStoreDetailsDatabase.BusBookingFinalData;
import com.payz24.responseModels.BusStoreDetailsDatabase.BusData;
import com.payz24.responseModels.BusStoreDetailsDatabase.BusStoreDetailsDatabase;
import com.payz24.responseModels.BusStoreDetailsDatabase.FinalSeat;
import com.payz24.responseModels.BusStoreDetailsDatabase.SearchData;
import com.payz24.responseModels.BusStoreDetailsDatabase.UserData;
import com.payz24.responseModels.ProfileResponse.ProfileResponse;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.blockTicket.BlockSeatPaxDetail;
import com.payz24.responseModels.blockTicket.BlockTicket;
import com.payz24.responseModels.busLayout.Seat;
import com.payz24.responseModels.busSeatBookingResponse.BusSeatBookingResponse;
import com.payz24.responseModels.storeBusDetails.Result;
import com.payz24.responseModels.storeBusDetails.StoreBusDetails;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;
import com.payz24.utils.StringUtil;
import com.worldline.in.constant.Param;
import com.worldline.in.ipg.PaymentStandard;
import com.worldline.in.utility.Utility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by mahesh on 2/15/2018.
 */

public class BusPaymentDetails extends BaseActivity implements View.OnClickListener, HttpReqResCallBack, CompoundButton.OnCheckedChangeListener {

    private TextView tvNotes, tvCreditCard, tvDebitCard, tvNetBanking;
    private TextView tvLeavingFromValue, tvGoingToValue, tvOperatorName, tvBusTypeValue, tvJdate, tvBoardingPointValue, tvDepartureDateTime, tvNumberOfSeats;
    private LinearLayout llHeader, llJourneyDetails, llPassengerDetailsContainer, llPaymentDetails;
    private TextView tvPassengerSeatDetails, tvPassengerSeatNumber, tvContinue, tvNetPayableValue, tvWalletBalance;
    private NetworkDetection networkDetection;
    private ImageView ivMore;
    private CheckBox walletCheckBox;
    private LinkedList<String> listOfPaymentDetailsKeys;
    private LinkedList<String> listOfPaymentDetailsValues;
    private int inventoryType;
    private String blockTicketKey = "";
    private String blockTicketString = "";
    private Double totalFareWithTaxes = 0.0;
    private Double serviceTax = 0.0;
    private Double operatorServiceChargeAbsolute = 0.0;
    private Float totalFareWithOutTaxes = 0f;
    private boolean isHeaderClicked;
    private BlockTicket blockTicket;
    private List<String> listOfFirstName;
    private List<String> listOfGenders;
    private List<String> listOfAge;
    private List<String> listOfBusSeatsNames;
    private String userEmail = "";
    private String userPhoneCode = "";
    private String boardingPointId = "";
    private String dropPointId = "";
    private String userId = "";
    private String userType = "";
    private String userPhoneNumber = "";
    private String userEmergencyNumber = "";
    private String userCity = "";
    private String userState = "";
    private String userIdProof = "";
    private String userIdNumber = "";
    private String userAddress = "";
    private String userPincode = "";
    private String jType = "";
    private String fromCity = "";
    private String toCity = "";
    private String cin = "";
    private String ed = "";
    private String seatValues = "";
    private String busval = "";
    private List<FinalSeat> listOfFinalSeats;
    private Double commPCT;
    private String etsNumber = "";
    private String cancellationPolicy;
    private String bookingStatus;
    private String providerContact;
    private String paymentStatus;
    private String txnId;
    private String opPNR;
    private String routeScheduleId;
    private String serviceId;
    private String busDetails;
    private String statusDescription = "";
    private String transactionRefNo = "";
    private String orderId = "";
    private String customerName = "";
    private String operatorName = "";
    private String busType = "";
    private String boardingPoint = "";
    private String boardingPointTime = "";
    private String walletBalance;
    private String walletAmount = "";
    private String useWallet = "";
    private String remainingAmount = "";
    private double markUpFee = 0.0;
    private double conventionalFee = 0.0;
    private double totalFeeWithTax = 0.0;
    private int totalFeeWithConventionalTax = 0;
    private int tts = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_payment_details);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        preparePaymentDetailsList();
        showProgressBar();
        serviceCallForGetProfileDetails();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            inventoryType = bundle.getInt(getString(R.string.inventory_type), 0);
            blockTicketKey = bundle.getString(getString(R.string.block_ticket_key));
            blockTicketString = bundle.getString(getString(R.string.block_ticket_string));
        }
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.payment_details));
        setSupportActionBar(toolbar);


        tvNotes = findViewById(R.id.tvNote);
        tvJdate = findViewById(R.id.tvJdate);
        tvCreditCard = findViewById(R.id.tvCreditCard);
        tvDebitCard = findViewById(R.id.tvDebitCard);
        tvNetBanking = findViewById(R.id.tvNetBanking);
        tvLeavingFromValue = findViewById(R.id.tvLeavingFromValue);
        tvGoingToValue = findViewById(R.id.tvGoingToValue);
        tvOperatorName = findViewById(R.id.tvOperatorName);
        tvBusTypeValue = findViewById(R.id.tvBusTypeValue);
        tvBoardingPointValue = findViewById(R.id.tvBoardingPointValue);
        tvDepartureDateTime = findViewById(R.id.tvDepartureDateTime);
        tvNumberOfSeats = findViewById(R.id.tvNumberOfSeats);
        tvContinue = findViewById(R.id.tvContinue);
        tvNetPayableValue = findViewById(R.id.tvNetPayableValue);
        ivMore = findViewById(R.id.ivMore);
        llHeader = findViewById(R.id.llHeader);
        llJourneyDetails = findViewById(R.id.llJourneyDetails);
        llPassengerDetailsContainer = findViewById(R.id.llPassengerDetailsContainer);
        llPaymentDetails = findViewById(R.id.llPaymentDetails);
        tvWalletBalance = findViewById(R.id.tvWalletBalance);
        walletCheckBox = findViewById(R.id.walletCheckBox);
        networkDetection = new NetworkDetection();

        listOfPaymentDetailsKeys = new LinkedList<>();
        listOfPaymentDetailsValues = new LinkedList<>();
        listOfFirstName = new ArrayList<>();
        listOfAge = new ArrayList<>();
        listOfGenders = new ArrayList<>();
        listOfBusSeatsNames = new ArrayList<>();
    }

    private void initializeListeners() {
        llHeader.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        walletCheckBox.setOnCheckedChangeListener(this);
    }

    private void preparePaymentDetailsList() {
        blockTicket = new Gson().fromJson(blockTicketString, BlockTicket.class);
        userEmail = blockTicket.getCustomerEmail();
        userPhoneCode = "+91";
        boardingPointId = PreferenceConnector.readString(BusPaymentDetails.this,getString(R.string.bp),"");
      //  int s=blockTicket.getBoardingPoint().getId().indexOf(boardingPoint);
        Log.e("boardingpoint",boardingPoint);

        dropPointId = PreferenceConnector.readString(BusPaymentDetails.this,getString(R.string.dp),"");
        Log.e("dropPointId",dropPointId);
        userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        userType = PreferenceConnector.readString(this, getString(R.string.user_type), "");
        userPhoneNumber = blockTicket.getCustomerPhone();
        userEmergencyNumber = blockTicket.getCustomerPhone();
        customerName = blockTicket.getCustomerName();
        userCity = "";
        userState = "";
        userIdProof = "";
        userIdNumber = "";
        userAddress = "";
        userPincode = "";
        jType = "O";
        fromCity = blockTicket.getSourceCity();
        toCity = blockTicket.getDestinationCity();
        cin = blockTicket.getDoj();
        ed = blockTicket.getDoj();
        busval = "0";
        routeScheduleId = blockTicket.getRouteScheduleId();
        inventoryType = blockTicket.getInventoryType();

        tvLeavingFromValue.setText(blockTicket.getSourceCity());
        tvGoingToValue.setText(blockTicket.getDestinationCity());
        ApiAvailableBus apiAvailableBus = AppController.getInstance().getSelectedAvailableBus();
        operatorName = apiAvailableBus.getOperatorName();
        busType = apiAvailableBus.getBusType();
        tvOperatorName.setText(operatorName);
        tvBusTypeValue.setText(busType);
        tvJdate.setText(blockTicket.getDoj());
        boardingPoint = blockTicket.getBoardingPoint().getLocation();
        boardingPointTime = blockTicket.getBoardingPoint().getTime();
        tvBoardingPointValue.setText(boardingPoint);
        tvDepartureDateTime.setText(boardingPointTime);
        List<BlockSeatPaxDetail> listOfBlockSeatPaxDetails = blockTicket.getBlockSeatPaxDetails();
        tvNumberOfSeats.setText(String.valueOf(listOfBlockSeatPaxDetails.size()));
        int numberOfSeats = listOfBlockSeatPaxDetails.size();

        for (int index = 0; index < listOfBlockSeatPaxDetails.size(); index++) {
            BlockSeatPaxDetail blockSeatPaxDetail = listOfBlockSeatPaxDetails.get(index);
            Float fare = blockSeatPaxDetail.getFare();
            Double serviceTax = blockSeatPaxDetail.getServiceTaxAmount();
            Double operatorServiceChargeAbsolute = blockSeatPaxDetail.getOperatorServiceChargeAbsolute();
            Double totalFareWithTaxes = blockSeatPaxDetail.getTotalFareWithTaxes();

            this.totalFareWithOutTaxes = this.totalFareWithOutTaxes + fare;
            this.totalFareWithTaxes = this.totalFareWithTaxes + totalFareWithTaxes;
            this.serviceTax = this.serviceTax + serviceTax;
            this.operatorServiceChargeAbsolute = this.operatorServiceChargeAbsolute + operatorServiceChargeAbsolute;
            String name = blockSeatPaxDetail.getName() + "," + blockSeatPaxDetail.getSex() + "," + blockSeatPaxDetail.getAge() + " Years";
            String seatNumber = blockSeatPaxDetail.getSeatNbr();
            listOfFirstName.add(blockSeatPaxDetail.getName());
            listOfGenders.add(blockSeatPaxDetail.getSex());
            listOfAge.add(blockSeatPaxDetail.getAge());
            listOfBusSeatsNames.add(blockSeatPaxDetail.getSeatNbr());
            initializePassengerView(name, seatNumber);
        }



        int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.bus_mark_up), ""));
        int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.bus_conventional_fee), ""));
        String busMType = PreferenceConnector.readString(this, getString(R.string.bus_m_type), "");
        Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
        Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
        markUpFee = Math.round(totalFareWithOutTaxes * markUpPercentage);
        if (busMType.equalsIgnoreCase("M"))
            conventionalFee = Math.round((totalFareWithTaxes - markUpFee) * conventionalFeePercentage);
        else
            conventionalFee = Math.round((totalFareWithTaxes + markUpFee) * conventionalFeePercentage);


        String seatsFareKey = getString(R.string.seat_fare) + "(For" + String.valueOf(numberOfSeats) + " seats)";
        listOfPaymentDetailsKeys.add(seatsFareKey);

        listOfPaymentDetailsKeys.add(getString(R.string.gst_amount));
        listOfPaymentDetailsKeys.add(getString(R.string.service_charge));
        listOfPaymentDetailsKeys.add(getString(R.string.pgc));
        listOfPaymentDetailsKeys.add(getString(R.string.net_payable));



        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (totalFareWithTaxes - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (totalFareWithTaxes + markUpFee + conventionalFee);

        if (busMType.equalsIgnoreCase("M"))
            listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(totalFareWithOutTaxes-markUpFee));
        else
            listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(totalFareWithOutTaxes+markUpFee));

        listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(serviceTax));
        listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(0));
        listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(conventionalFee));
        listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(totalFeeWithTax));

        Log.e("conventionalFee",""+conventionalFee);
        Log.e("markUpFee",""+markUpFee);
        Log.e("totalFareWithTaxes",""+totalFareWithTaxes);
        Log.e("totalFeeWithTax",""+totalFeeWithTax);

        tvNetPayableValue.setText(getString(R.string.Rs) + "" + String.valueOf(totalFeeWithTax));
        for (int paymentIndex = 0; paymentIndex < listOfPaymentDetailsKeys.size(); paymentIndex++) {
            initializePaymentDetailsView(listOfPaymentDetailsKeys.get(paymentIndex), listOfPaymentDetailsValues.get(paymentIndex));
        }
    }

    @SuppressLint("LongLogTag")
    private void prepareDetailsToStoreInDatabase() {
        if (!bookingStatus.equalsIgnoreCase("failed")) {
            if (totalFareWithTaxes != 0.0) {
                if (walletCheckBox.isChecked()) {
                    useWallet = "1";
                    if (Double.parseDouble(walletBalance) >= totalFareWithTaxes) {
                        walletAmount = String.valueOf(totalFareWithTaxes);
                        remainingAmount = "0";
                    } else {
                        walletAmount = walletBalance;
                        remainingAmount = String.valueOf(totalFareWithTaxes - Double.parseDouble(walletBalance));
                    }
                } else {
                    useWallet = "0";
                    remainingAmount = "0";
                }
            } else {
                useWallet = "0";
                remainingAmount = "0";
            }
        } else {
            useWallet = "0";
            remainingAmount = "0";
            walletAmount = "0";
        }
        if (blockTicket != null) {
            Double totalFeeWithTaxs = 0.0;
            List<Seat> listOfSelectedSeat = AppController.getInstance().getListOfSelectedSeat();
            seatValues = TextUtils.join(";", listOfBusSeatsNames);
            Log.e("boardingpont",boardingPointId);
            Log.e("dropPointId",dropPointId);
            UserData userData = new UserData(listOfFirstName, null, listOfGenders, listOfAge, userEmail, userPhoneCode, listOfBusSeatsNames, boardingPointId, dropPointId, userId, userType, userPhoneNumber, userEmergencyNumber, userCity, userState, userIdProof, userIdNumber, userAddress, userPincode,
                    useWallet, walletAmount, remainingAmount);
            SearchData searchData = new SearchData(jType, fromCity, toCity, PreferenceConnector.readString(this, getString(R.string.leaving_from_station_id), ""), PreferenceConnector.readString(this, getString(R.string.going_to_station_id), ""), cin, ed);
            listOfFinalSeats = new ArrayList<>();
            Double totalFare = 0.0;
            Double tax = 0.0;
            ApiAvailableBus apiAvailableBus = AppController.getInstance().getSelectedAvailableBus();
            cancellationPolicy = apiAvailableBus.getCancellationPolicy();
            serviceId = apiAvailableBus.getServiceId();
            commPCT = apiAvailableBus.getCommPCT();
            for (int index = 0; index < listOfSelectedSeat.size(); index++) {
                Seat seat = listOfSelectedSeat.get(index);
                totalFare = totalFare + seat.getTotalFareWithTaxes();
                String busMType = PreferenceConnector.readString(this, getString(R.string.bus_m_type), "");

                if (busMType.equalsIgnoreCase("M"))
                    totalFeeWithTaxs = (double) (totalFare - markUpFee + conventionalFee);
                else
                    totalFeeWithTaxs = (double) (totalFare + markUpFee+ conventionalFee);
                tax = tax + seat.getServiceTaxPer();

                FinalSeat finalSeat = new FinalSeat(seat.getId(), seat.getRow(), seat.getColumn(), seat.getZIndex(),
                        seat.getLength(), seat.getWidth(), seat.getFare(), seat.getCommission(), seat.getAvailable(), seat.getLadiesSeat(),
                        seat.getBookedBy(), seat.getAc(), seat.getSleeper(), seat.getTotalFareWithTaxes(), seat.getServiceTaxAmount(),
                        seat.getServiceTaxPer(), seat.getChildFare(), seat.getOperatorServiceChargeAbsolute(), seat.getOperatorServiceChargePercent());
                listOfFinalSeats.add(finalSeat);
            }
            Log.e("conventionalFee",""+conventionalFee);
            Log.e("markUpFee",""+markUpFee);
            Log.e("totalFareWithTaxes",""+totalFareWithTaxes);
            Log.e("totalFeeWithTax",""+totalFeeWithTax);
            Log.e("totalFare",""+totalFare);



            String busMType = PreferenceConnector.readString(this, getString(R.string.bus_m_type), "");
            BusBookingFinalData busBookingFinalData = new BusBookingFinalData(totalFeeWithTaxs, totalFare, tax, commPCT, providerContact, bookingStatus, paymentStatus, txnId,
                    busMType, conventionalFee, markUpFee);
            BusData busData = new BusData(opPNR, etsNumber, inventoryType, routeScheduleId, serviceId, apiAvailableBus.getFare(), apiAvailableBus.getDepartureTime(),
                    apiAvailableBus.getArrivalTime(), apiAvailableBus.getAvailableSeats(), apiAvailableBus.getOperatorName(), cancellationPolicy, apiAvailableBus.getBoardingPoints(), apiAvailableBus.getDroppingPoints(),
                    apiAvailableBus.getBusType(), apiAvailableBus.getPartialCancellationAllowed(), apiAvailableBus.getIdProofRequired(), apiAvailableBus.getOperatorId(), apiAvailableBus.getCommPCT(),
                    apiAvailableBus.getMTicketAllowed(), apiAvailableBus.getIsRTC(), apiAvailableBus.getIsOpTicketTemplateRequired(), apiAvailableBus.getIsOpLogoRequired(), apiAvailableBus.getIsFareUpdateRequired(),
                    apiAvailableBus.getIsChildConcession(), apiAvailableBus.getIsGetLayoutByBPDP());

            BusStoreDetailsDatabase busStoreDetailsDatabase = new BusStoreDetailsDatabase(userData, searchData, seatValues, busval, listOfFinalSeats, busBookingFinalData, busData);
            busDetails = new Gson().toJson(busStoreDetailsDatabase);
        }
    }

    private void initializePaymentDetailsView(String key, String value) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_payment_details_items, null);
        TextView tvPaymentKey = view.findViewById(R.id.tvPaymentKey);
        TextView tvPaymentValue = view.findViewById(R.id.tvPaymentValue);
        tvPaymentKey.setText(key);
        tvPaymentValue.setText(value);
        llPaymentDetails.addView(view);
    }

    private void initializePassengerView(String name, String seatNumber) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_passenger_details_items, null);
        tvPassengerSeatDetails = view.findViewById(R.id.tvPassengerSeatDetails);
        tvPassengerSeatNumber = view.findViewById(R.id.tvPassengerSeatNumber);
        tvPassengerSeatDetails.setText(name);
        tvPassengerSeatNumber.setText(seatNumber);
        llPassengerDetailsContainer.addView(view);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llHeader:
                if (!isHeaderClicked) {
                    llJourneyDetails.setVisibility(View.VISIBLE);
                    tvNotes.setVisibility(View.GONE);
                    isHeaderClicked = true;
                } else {
                    llJourneyDetails.setVisibility(View.GONE);
                    tvNotes.setVisibility(View.VISIBLE);
                    isHeaderClicked = false;
                }
                break;
            case R.id.ivMore:
                if (!isHeaderClicked) {
                    llJourneyDetails.setVisibility(View.VISIBLE);
                    tvNotes.setVisibility(View.GONE);
                    isHeaderClicked = true;
                } else {
                    llJourneyDetails.setVisibility(View.GONE);
                    tvNotes.setVisibility(View.VISIBLE);
                    isHeaderClicked = false;
                }
                break;

            case R.id.tvContinue:
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
                if (totalPayableAmount != 0.0) {
                    Double totalFare = totalPayableAmount * 100.0;
                    Intent intent = new Intent(this, PaymentStandard.class);
                    intent.putExtra(Param.ORDER_ID, "" + getRandomOrderId());
                    intent.putExtra(Param.TRANSACTION_AMOUNT, String.valueOf(totalFare.intValue()));
                    intent.putExtra(Param.TRANSACTION_CURRENCY, "INR");
                    intent.putExtra(Param.TRANSACTION_DESCRIPTION, "Sock money");
                    intent.putExtra(Param.TRANSACTION_TYPE, "S");
                    intent.putExtra(Param.KEY, "6375b97b954b37f956966977e5753ee6");//*Optional
                    intent.putExtra(Param.MID, "WL0000000027698");//*Optional
                    startActivityForResult(intent, 1);
                } else {
                    showProgressBar();
                    serviceCallToBookTickets();
                }
                break;
            default:
                break;
        }
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
                    showProgressBar();
                    serviceCallToBookTickets();
                } else {
                    Toast.makeText(this, statusDescription, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void serviceCallToBookTickets() {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.BUS_SEAT_BOOKING_PARAM_BLOCK_TICKET_KEY, blockTicketKey);
        BusSeatBookingHttpClient busSeatBookingHttpClient = new BusSeatBookingHttpClient(this);
        busSeatBookingHttpClient.callBack = this;
        busSeatBookingHttpClient.getJsonForType(mapOfRequestParams);
    }

    private void serviceCallForStoreBusDetails() {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.BUS_DETAILS_STORE_PARAM_BODY, busDetails);
        StoreBusDetailsHttpClient storeBusDetailsHttpClient = new StoreBusDetailsHttpClient(this);
        storeBusDetailsHttpClient.callBack = this;
        storeBusDetailsHttpClient.getJsonForType(mapOfRequestParams);
    }

    private void goToBusActivity() {
        Intent busIntent = new Intent(this, BusActivity.class);
        startActivity(busIntent);
    }

    private void goToReviewActivity() {


        String message = "Dear Customer," + " " + "PNR" + "  " + opPNR + " " + fromCity + " to " + toCity + "." + " " + operatorName + " "
                + busType + "." + boardingPointTime + "." + " Seats :" + TextUtils.join(",", listOfBusSeatsNames) + " Boarding:" + boardingPoint + "," +
                " Report before 15m. From  https://payz24.com";

       // String message = "Dear Customer," + " " + "PNR" + "  " + opPNR + " " + fromCity + " to " + toCity + "." + " " + operatorName + " " + busType + "." + boardingPointTime + ":" + TextUtils.join(",", listOfBusSeatsNames) + " " + boardingPoint + "," + "More: Report before 15m.";
        Intent reviewIntent = new Intent(this, TicketInformationReviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.block_ticket_string), blockTicketString);
        bundle.putString(getString(R.string.message), message);
        bundle.putString(getString(R.string.bus_details), busDetails);
        bundle.putString(getString(R.string.order_id), orderId);
        bundle.putString(getString(R.string.mobile_number), userPhoneNumber);
        bundle.putString(getString(R.string.block_ticket_key), blockTicketKey);
        bundle.putString(getString(R.string.pnr), opPNR);
        bundle.putString("to", toCity);
        bundle.putString("from", fromCity);
        bundle.putString("conv", ""+conventionalFee);
        bundle.putString("mark", ""+markUpFee);
        bundle.putString("fare", ""+totalFareWithTaxes);

        reviewIntent.putExtras(bundle);
        startActivity(reviewIntent);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {

        switch (requestType) {

            case Constants.SERVICE_BUS_SEAT_BOOKING:
                if (jsonResponse != null) {

                    BusSeatBookingResponse busSeatBookingResponse = new Gson().fromJson(jsonResponse, BusSeatBookingResponse.class);
                    boolean status = busSeatBookingResponse.getApiStatus().getSuccess();
                    String message = busSeatBookingResponse.getApiStatus().getMessage();
                    if (status) {
                        //goToBusActivity();
                        commPCT = busSeatBookingResponse.getCommPCT();
                        etsNumber = busSeatBookingResponse.getEtstnumber();
                        if (message.equalsIgnoreCase("success"))
                            bookingStatus = "Confirmed";
                        else
                            bookingStatus = message;
                        providerContact = "Confirmed";
                        paymentStatus = busSeatBookingResponse.getApiStatus().getMessage();
                        txnId = transactionRefNo;
                        opPNR = busSeatBookingResponse.getOpPNR();
                        prepareDetailsToStoreInDatabase();
                        //serviceCallForStoreBusDetails();
                        if (etsNumber != null)
                            goToReviewActivity();
                       // Toast.makeText(this, getString(R.string.booking_successful), Toast.LENGTH_SHORT).show();
                    } else {
                        bookingStatus = "failed";
                        prepareDetailsToStoreInDatabase();
                        serviceCallForSMSGateWay("failed");
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            case Constants.SERVICE_STORE_BUS_DETAILS:

                if (jsonResponse != null) {
                    if (!jsonResponse.startsWith("<br />")) {

                        StoreBusDetails storeBusDetails = new Gson().fromJson(jsonResponse, StoreBusDetails.class);
                        String status = storeBusDetails.getStatus();
                        String message = storeBusDetails.getMessage();
                        Result result = storeBusDetails.getResult();
                        Log.e("Rsilt", String.valueOf(result.getMasterId()));
                        if (status.equalsIgnoreCase("success")) {
                           // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        } else {
                          //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                        if (bookingStatus.equalsIgnoreCase("Confirmed")) {
                            serviceCallForSMSGateWay("success");
                        }
                    } else {
                        if (bookingStatus.equalsIgnoreCase("Confirmed")) {
                            serviceCallForSMSGateWay("success");
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.SERVICE_SMS:
                if (jsonResponse != null) {
                    if (bookingStatus.equalsIgnoreCase("Confirmed"))
                        goToReviewActivity();
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

    private void serviceCallForSMSGateWay(String status) {
        String message = "Dear Customer," + " " + "PNR" + "  " + opPNR + " " + fromCity + " to " + toCity + "." + " " + operatorName + " " + busType
                + "." + boardingPointTime + ":" + TextUtils.join(",", listOfBusSeatsNames) + " " + boardingPoint + ","
                + "More: Report before 15m."+"\nFor More Details Visit\n" +
                "https://payz24.com\n";
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SMS_PARAM_PHONE, userPhoneNumber);
        if (status.equalsIgnoreCase("success")) {
            mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, message);
        } else {
            mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, "Thanks for using Bus facilities and your order id is" + " " + orderId + "payment got failed for amount" + String.valueOf(totalFeeWithTax));
        }
        SmsHttpClient smsHttpClient = new SmsHttpClient(this);
        smsHttpClient.callBack = this;
        smsHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String busMType = PreferenceConnector.readString(this, getString(R.string.bus_m_type), "M");
        Log.e("bustype",busMType);
        totalFeeWithConventionalTax = 0;
        if (busMType.equalsIgnoreCase("M"))

            totalFeeWithConventionalTax = (int) (totalFareWithTaxes - markUpFee + conventionalFee);
        else
            totalFeeWithConventionalTax = (int) (totalFareWithTaxes + markUpFee + conventionalFee);

        if (isChecked) {
            if (totalFareWithTaxes != 0.0) {
                if (Double.parseDouble(walletBalance) >= totalFeeWithConventionalTax) {
                    //tvNetPayableValue.setText("0");
                } else {
                    double totalPayable = totalFeeWithConventionalTax - Double.parseDouble(walletBalance);
                    tvNetPayableValue.setText(String.valueOf(totalPayable));
                }
            }
        } else {
            tvNetPayableValue.setText(String.valueOf(totalFeeWithConventionalTax));
        }
    }
}
