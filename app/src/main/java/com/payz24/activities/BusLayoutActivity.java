package com.payz24.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.adapter.BusLayoutAdapter;
import com.payz24.application.AppController;
import com.payz24.http.BusLayoutHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.busLayout.BusLayout;
import com.payz24.responseModels.busLayout.Seat;
import com.payz24.utils.Constants;
import com.payz24.utils.DialogUtils;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mahesh on 1/27/2018.
 */

public class BusLayoutActivity extends BaseActivity implements HttpReqResCallBack, View.OnClickListener, BusLayoutAdapter.ItemClickListener {

    private ImageView ivInfo, ivBackArrow;
    int totalFeeWithTax = 0;
    double gst=0.0;
    private TextView tvOperatorName;
    private RecyclerView rvBusLayout;
    private TextView tvError;
    private LinearLayout llButtons;
    private Button btnLower, btnUpper;
    private ApiAvailableBus apiAvailableBus;
    private NetworkDetection networkDetection;
    private String leavingFromStationName = "";
    private String goingToStationName = "";
    private String travellingDate = "";
    private boolean hasZAxis;
    private int numberOfRows = 0;
    private int numberOfColumns = 0;
    private List<Seat> listOfSeats;
    private int selectedRow = 0;
    private BottomSheetDialog dialog;
    private TextView tvSeatLegends, tvCancellationPolicy;
    private LinearLayout llSeat, llCancellation, llSeatUnSelected, llSeatSelected;
    private TextView tvSelectedSeatName, tvTotalAmount;
    private ImageView ivSeatSelectedArrow;
    private Button btnProceed;
    private String travelsName = "";
    private LinkedList<String> listOfSeatNames;
    private List<Double> listOfFares;
    private List<Float> listOfFaresWithOutTaxes;
    private List<Seat> listOfSeatStructure;
    private List<Seat> listOfSelectedSeat;
    private Double totalAmount = 0.0;
    private Float totalAmountWithOutTaxes = 0f;
    private TextView tvRule, tvGuidelines;
    private ImageView ivRulesArrow;
    private boolean isGuideLinesClicked;
    private int numberOfUpperRows = 0;
    private int numberOfUpperColumns = 0;
    private double markUpFee = 0.0;
    private double conventionalFee = 0.0;
    TextView gstFare;
    ArrayList<String> ladiesSeat=new ArrayList<String>();


    //Bus Fare

    TextView btvSource,btvDestination,busFare,btax,conFee,btotalPrice,btvClose;
    double fareWithOutTaxess=0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_layout);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListener();
        serviceCallToGetBusLayout();
    }

    private void getDataFromIntent() {
        apiAvailableBus = AppController.getInstance().getSelectedAvailableBus();
        if (getIntent() != null) {
            leavingFromStationName = getIntent().getStringExtra(getString(R.string.leaving_from));
            goingToStationName = getIntent().getStringExtra(getString(R.string.going_to));
            travellingDate = getIntent().getStringExtra(getString(R.string.doj));
            travelsName = getIntent().getStringExtra(getString(R.string.travels_names));
        }
    }

    private void initializeUi() {
        ivInfo = findViewById(R.id.ivInfo);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        tvOperatorName = findViewById(R.id.tvOperatorName);
        rvBusLayout = findViewById(R.id.rvBusLayout);
        tvError = findViewById(R.id.tvError);
        llButtons = findViewById(R.id.llButtons);
        btnLower = findViewById(R.id.btnLower);
        btnUpper = findViewById(R.id.btnUpper);
        llSeatUnSelected = findViewById(R.id.llSeatUnSelected);
        llSeatSelected = findViewById(R.id.llSeatSelected);
        tvSelectedSeatName = findViewById(R.id.tvSelectedSeatName);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        ivSeatSelectedArrow = findViewById(R.id.ivSeatSelectedArrow);
        btnProceed = findViewById(R.id.btnProceed);
        tvRule = findViewById(R.id.tvRule);
        tvGuidelines = findViewById(R.id.tvGuidelines);
        ivRulesArrow = findViewById(R.id.ivRulesArrow);

        networkDetection = new NetworkDetection();
        tvOperatorName.setText(travelsName);

        listOfSeatNames = new LinkedList<>();
        listOfFares = new LinkedList<>();
        listOfFaresWithOutTaxes = new LinkedList<>();
        listOfSelectedSeat = new ArrayList<>();
    }

    private void initializeListener() {
        ivInfo.setOnClickListener(this);
        ivBackArrow.setOnClickListener(this);
        btnLower.setOnClickListener(this);
        btnUpper.setOnClickListener(this);
        llSeatUnSelected.setOnClickListener(this);
        btnProceed.setOnClickListener(this);
        ivSeatSelectedArrow.setOnClickListener(this);
        llSeatSelected.setOnClickListener(this);
    }

    private void serviceCallToGetBusLayout() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.BUS_LAYOUT_PARAM_SOURCE_CITY, leavingFromStationName);
            mapOfRequestParams.put(Constants.BUS_LAYOUT_PARAM_DESTINATION_CITY, goingToStationName);
            mapOfRequestParams.put(Constants.BUS_LAYOUT_PARAM_DOJ, travellingDate);
            mapOfRequestParams.put(Constants.BUS_LAYOUT_PARAM_INVENTORY_TYPE, String.valueOf(apiAvailableBus.getInventoryType()));
            mapOfRequestParams.put(Constants.BUS_LAYOUT_PARAM_ROUTE_SCHEDULE_ID, String.valueOf(apiAvailableBus.getRouteScheduleId()));

            BusLayoutHttpClient busLayoutHttpClient = new BusLayoutHttpClient(this);
            busLayoutHttpClient.callBack = this;
            busLayoutHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_BUS_LAYOUT:
                if (jsonResponse != null) {
                    Log.e("jsonResponsefor BusLayout",jsonResponse.trim());
                    BusLayout busLayout = new Gson().fromJson(jsonResponse, BusLayout.class);
                    if (busLayout != null) {
                        boolean status = busLayout.getApiStatus().getSuccess();
                        String message = busLayout.getApiStatus().getMessage();
                        if (status) {
                            listOfSeats = busLayout.getSeats();
                            for (int index = 0; index < listOfSeats.size(); index++) {
                                int ZAxis = listOfSeats.get(index).getZIndex();
                                int row = listOfSeats.get(index).getRow();
                                int column = listOfSeats.get(index).getColumn();

                                if (ZAxis == 1) {
                                    hasZAxis = true;
                                    llButtons.setVisibility(View.VISIBLE);
                                    if (row > numberOfUpperRows) {
                                        numberOfUpperRows = row;
                                    }
                                    if (column > numberOfUpperColumns) {
                                        numberOfUpperColumns = column;
                                    }
                                } else {
                                    llButtons.setVisibility(View.GONE);
                                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) rvBusLayout.getLayoutParams();
                                    marginLayoutParams.setMargins(100, 40, 100, 40);
                                    rvBusLayout.setLayoutParams(marginLayoutParams);
                                }
                                if (row > numberOfRows) {
                                    numberOfRows = row;
                                }
                                if (column > numberOfColumns) {
                                    numberOfColumns = column;
                                }
                            }
                            numberOfRows = numberOfRows + 1;
                            numberOfColumns = numberOfColumns + 1;

                            numberOfUpperRows = numberOfUpperRows + 1;
                            numberOfUpperColumns = numberOfUpperColumns + 1;
                            listOfSeatStructure = new ArrayList<>();
                            if (!hasZAxis) {
                                for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
                                    List<Seat> listOfSeatNames = new ArrayList<>(Collections.nCopies(numberOfRows, new Seat()));
                                    for (int seatIndex = 0; seatIndex < listOfSeats.size(); seatIndex++) {
                                        int column = listOfSeats.get(seatIndex).getColumn();
                                        int row = listOfSeats.get(seatIndex).getRow();

                                        if (columnIndex == column) {
                                            listOfSeatNames.set(row, listOfSeats.get(seatIndex));
                                        }
                                    }
                                    Collections.reverse(listOfSeatNames);
                                    listOfSeatStructure.addAll(listOfSeatNames);
                                }
                            } else {
                                btnLower.performClick();
                            }
                            if (listOfSeatStructure.size() != 0) {
                                initializeAdapter(listOfSeatStructure, numberOfRows);
                            }
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void initializeAdapter(List<Seat> listOfSeatStructure, int numberOfRows) {
        BusLayoutAdapter busLayoutAdapter = new BusLayoutAdapter(this, listOfSeatStructure, selectedRow);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfRows);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0) ? BusLayoutActivity.this.numberOfRows : 1;
            }
        });
        rvBusLayout.setLayoutManager(gridLayoutManager);
        rvBusLayout.setItemAnimator(new DefaultItemAnimator());
        busLayoutAdapter.setClickListener(this);
        rvBusLayout.setAdapter(busLayoutAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivInfo:
                showBottomDialog();
                break;
            case R.id.ivBackArrow:
                onBackPressed();
                break;
            case R.id.btnLower:
                btnLower.setBackgroundResource(R.color.colorPrimary);
                btnLower.setTextColor(getResources().getColor(R.color.white));
                btnUpper.setBackgroundResource(android.R.color.transparent);
                btnUpper.setTextColor(getResources().getColor(R.color.colorPrimary));
                selectedRow = 0;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) rvBusLayout.getLayoutParams();
                marginLayoutParams.setMargins(100, 40, 100, 40);

               /* if (apiAvailableBus.getBusType().contains("1+1")) {
                    marginLayoutParams.setMargins(320, 0, 320, 0);
                } else {
                    marginLayoutParams.setMargins(280, 0, 280, 0);
                }*/

                rvBusLayout.setLayoutParams(marginLayoutParams);
                seatSelection(selectedRow, numberOfColumns, numberOfRows);
                break;
            case R.id.btnUpper:
                btnUpper.setBackgroundResource(R.color.colorPrimary);
                btnUpper.setTextColor(getResources().getColor(R.color.white));
                btnLower.setBackgroundResource(android.R.color.transparent);
                btnLower.setTextColor(getResources().getColor(R.color.colorPrimary));
                ViewGroup.MarginLayoutParams marginLayoutParams1 = (ViewGroup.MarginLayoutParams) rvBusLayout.getLayoutParams();
                if (apiAvailableBus.getBusType().contains("1+1")) {
                    marginLayoutParams1.setMargins(320, 0, 320, 0);
                } else {
                    marginLayoutParams1.setMargins(280, 0, 280, 0);
                }
                rvBusLayout.setLayoutParams(marginLayoutParams1);
                selectedRow = 1;
                seatSelection(selectedRow, numberOfUpperColumns, numberOfUpperRows);
                break;
            case R.id.tvClose:
                dialog.dismiss();
                break;
            case R.id.btvClose:
                dialog.dismiss();
                break;
            case R.id.tvSeatLegends:
                tvSeatLegends.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvCancellationPolicy.setTextColor(getResources().getColor(R.color.black));
                llSeat.setVisibility(View.VISIBLE);
                llCancellation.setVisibility(View.GONE);
                break;
            case R.id.tvCancellationPolicy:
                tvSeatLegends.setTextColor(getResources().getColor(R.color.black));
                tvCancellationPolicy.setTextColor(getResources().getColor(R.color.colorPrimary));
                llCancellation.setVisibility(View.VISIBLE);
                llSeat.setVisibility(View.GONE);
                break;
            case R.id.llSeatUnSelected:
                if (!isGuideLinesClicked) {
                    isGuideLinesClicked = true;
                    llSeatUnSelected.setBackgroundResource(R.color.medium_grey);
                    tvGuidelines.setVisibility(View.GONE);
                    tvRule.setVisibility(View.VISIBLE);
                    ivRulesArrow.setImageResource(R.drawable.ic_down_arrow);
                } else {
                    isGuideLinesClicked = false;
                    llSeatUnSelected.setBackgroundResource(R.color.white);
                    tvGuidelines.setVisibility(View.VISIBLE);
                    tvRule.setVisibility(View.GONE);
                    ivRulesArrow.setImageResource(R.drawable.ic_up_arrow);
                }
                break;
            case R.id.llSeatSelected:


                showPrice();




                break;
            case R.id.btnProceed:
                AppController.getInstance().setListOfSelectedSeat(listOfSelectedSeat);
                Intent boardingPointIntent = new Intent(this, BoardingPointActivity.class);
                boardingPointIntent.putExtra(getString(R.string.leaving_from), leavingFromStationName);
                boardingPointIntent.putExtra(getString(R.string.going_to), goingToStationName);
                boardingPointIntent.putExtra(getString(R.string.doj), travellingDate);
                boardingPointIntent.putExtra(getString(R.string.travels_names), travelsName);
                if (listOfSeatNames != null) {
                    boardingPointIntent.putExtra(getString(R.string.seat_names), TextUtils.join(",", listOfSeatNames));
                }
                startActivity(boardingPointIntent);
                break;
            default:
                break;
        }
    }

    private void showBottomDialog() {
        View view = getLayoutInflater().inflate(R.layout.layout_bus_info, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        TextView tvClose = view.findViewById(R.id.tvClose);
        tvSeatLegends = view.findViewById(R.id.tvSeatLegends);
        tvCancellationPolicy = view.findViewById(R.id.tvCancellationPolicy);
        llSeat = view.findViewById(R.id.llSeat);
        llCancellation = view.findViewById(R.id.llCancellation);
        tvClose.setOnClickListener(this);
        tvSeatLegends.setOnClickListener(this);
        tvCancellationPolicy.setOnClickListener(this);
        tvSeatLegends.performClick();
        dialog.show();
    }




    private void showPrice() {
        View view = getLayoutInflater().inflate(R.layout.layout_bus_breakup, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        btvClose = view.findViewById(R.id.btvClose);
        btvClose.setOnClickListener(this);
        btvSource = view.findViewById(R.id.btvSource);
        btvSource.setText(leavingFromStationName);
        btvDestination = view.findViewById(R.id.btvDestination);
        btvDestination.setText(goingToStationName);
        busFare = view.findViewById(R.id.busFare);
        btax = view.findViewById(R.id.btax);
        btotalPrice = view.findViewById(R.id.btotalPrice);
        gstFare = view.findViewById(R.id.gstFare);
        conFee = view.findViewById(R.id.conFee);
        btotalPrice.setText(String.valueOf(totalFeeWithTax));

        int totalFeeWithTax = 0;
        String busMType = PreferenceConnector.readString(this, getString(R.string.bus_m_type), "");

        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (fareWithOutTaxess - markUpFee );
        else
            totalFeeWithTax = (int) (fareWithOutTaxess + markUpFee);
        Log.e("totalfee",""+totalFeeWithTax);

        gstFare.setText(String.valueOf(gst));
        busFare.setText(String.valueOf((int) (totalFeeWithTax)));
        conFee.setText(String.valueOf((int) (conventionalFee)));
        dialog.show();
    }



    private void seatSelection(int selectedZAxis, int numberOfColumns, int numberOfRows) {
        listOfSeatStructure = new ArrayList<>();
        for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
            List<Seat> listOfSeatNames = new ArrayList<>(Collections.nCopies(numberOfRows, new Seat()));
            for (int seatIndex = 0; seatIndex < listOfSeats.size(); seatIndex++) {
                int column = listOfSeats.get(seatIndex).getColumn();
                int row = listOfSeats.get(seatIndex).getRow();
                int zAxis = listOfSeats.get(seatIndex).getZIndex();
                if (columnIndex == column && zAxis == selectedZAxis) {
                    listOfSeatNames.set(row, listOfSeats.get(seatIndex));
                }
            }
            Collections.reverse(listOfSeatNames);
            listOfSeatStructure.addAll(listOfSeatNames);
        }

        if (listOfSeatStructure.size() != 0) {
            initializeAdapter(listOfSeatStructure, numberOfRows);
        }
    }

    @Override
    public void onSeatClicked(View view, int position, boolean isSelected) {


        if (position == -1) {
            DialogUtils.getDialogUtilsInstance().alertDialogOk(this, getString(R.string.sorry_maximum_six_seats));
        } else if (isSelected) {
            Float fareWithOutTaxes = listOfSeatStructure.get(position).getFare();

            ladiesSeat.add(listOfSeatStructure.get(position).getId()+","+listOfSeatStructure.get(position).getLadiesSeat());
            listOfFares.add(listOfSeatStructure.get(position).getTotalFareWithTaxes());
            listOfFaresWithOutTaxes.add(fareWithOutTaxes);
            listOfSeatNames.add(listOfSeatStructure.get(position).getId());
            //listOfSeatNames.add(""+listOfSeatStructure.get(position).getLadiesSeat());
            listOfSelectedSeat.add(listOfSeatStructure.get(position));
            gst=listOfSeatStructure.get(position).getServiceTaxAmount();
            fareWithOutTaxess=listOfSeatStructure.get(position).getFare();

        } else if (!isSelected) {
            int clickedIndex = listOfSeatNames.indexOf(listOfSeatStructure.get(position).getId());
            int index = listOfFares.indexOf(listOfSeatStructure.get(position).getTotalFareWithTaxes());
            listOfFares.remove(clickedIndex);
            listOfFaresWithOutTaxes.remove(clickedIndex);
            listOfSeatNames.remove(clickedIndex);
            listOfSelectedSeat.remove(clickedIndex);
        }

        if (listOfFares.size() != 0) {
            llSeatUnSelected.setVisibility(View.GONE);
            llSeatSelected.setVisibility(View.VISIBLE);
        } else {
            llSeatUnSelected.setVisibility(View.VISIBLE);
            llSeatSelected.setVisibility(View.GONE);
        }
        if (listOfSeatNames.size() != 0 && listOfFares.size() != 0) {
            tvSelectedSeatName.setText(android.text.TextUtils.join(",", listOfSeatNames));
            totalAmount = 0.0;
            totalAmountWithOutTaxes = 0f;
            for (int amountIndex = 0; amountIndex < listOfFares.size(); amountIndex++) {
                totalAmount = totalAmount + listOfFares.get(amountIndex);
                totalAmountWithOutTaxes = totalAmountWithOutTaxes + listOfFaresWithOutTaxes.get(amountIndex);
            }

            try
            {
                int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.bus_mark_up), "0"));
                int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.bus_conventional_fee), "0"));

                Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp))/100;
                Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee))/100;
                markUpFee = totalAmountWithOutTaxes * markUpPercentage;
                String busMType = PreferenceConnector.readString(this, getString(R.string.bus_m_type), "");
                if (busMType.equalsIgnoreCase("M"))
                    conventionalFee = (totalAmount - markUpFee) * conventionalFeePercentage;
                else
                    conventionalFee = (totalAmount + markUpFee) * conventionalFeePercentage;


            }catch (Exception e)
            {
                e.printStackTrace();
            }


         /*   Log.e("totalAmount",""+totalAmount);
            Log.e("markUpFee",""+markUpFee);
            Log.e("conventionalFee",""+conventionalFee);
            Log.e("fareConventionalFee",""+fareConventionalFee);
            Log.e("fareConventionalFee",""+conventionalFeePercentage);
            Log.e("M",""+busMType);*/
        }

        String busMType = PreferenceConnector.readString(this, getString(R.string.bus_m_type), "");

        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (totalAmount - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (totalAmount + markUpFee + conventionalFee);
        tvTotalAmount.setText(String.valueOf(totalFeeWithTax));

    }
}
