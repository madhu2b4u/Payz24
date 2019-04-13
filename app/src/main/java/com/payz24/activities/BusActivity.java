package com.payz24.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.adapter.AvailableBusesAdapter;
import com.payz24.application.AppController;
import com.payz24.http.AvailableBusesHttpClient;
import com.payz24.http.BusStationsHttpClient;
import com.payz24.interfaces.BusStationsCallBack;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.availableBuses.AvailableBuses;
import com.payz24.responseModels.busStations.BusStations;
import com.payz24.responseModels.busStations.StationList;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mahesh on 1/23/2018.
 */

public class BusActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack, BusStationsCallBack, DatePickerDialog.OnDateSetListener {

    private TextView tvLeavingFrom, tvGoingTo, tvSelectedDay, tvSelectedMonth, tvSelectedName, tvSelectedDate;
    private ImageView ivChange;
    private LinearLayout llDateSelection, llSearch;
    private List<StationList> listOfBusStations;
    private NetworkDetection networkDetection;
    private String leavingFromStation = "";
    private int leavingFromStationId;
    private String goingToStationName = "";
    private int goingToStationId;
    private String selectedDate = "";
    private List<ApiAvailableBus> listOfAvailableBuses;
    String toCity,fromCity;
    String dateSelected="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_activity);
        initializeUi();
        getValuesFromIntent();


        enableActionBar(true);
        initializeListeners();
        selectCurrentDate();
        showProgressBar();
        String busStationsResponse = PreferenceConnector.readString(this, getString(R.string.bus_stations_response), "");
        if (busStationsResponse.isEmpty()) {
            showProgressBar();
            serviceCallForBusStations();
        } else {
            showProgressBar();
            jsonResponseReceived(busStationsResponse, 200, Constants.SERVICE_BUS_STATIONS);
        }
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.search_for_bus_tickets));
        setSupportActionBar(toolbar);

        tvLeavingFrom = findViewById(R.id.tvLeavingFrom);
        tvGoingTo = findViewById(R.id.tvGoingTo);
        tvSelectedDay = findViewById(R.id.tvSelectedDay);
        tvSelectedMonth = findViewById(R.id.tvSelectedMonth);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedName = findViewById(R.id.tvSelectedName);
        ivChange = findViewById(R.id.ivChange);
        llDateSelection = findViewById(R.id.llDateSelection);
        llSearch = findViewById(R.id.llSearch);
        networkDetection = new NetworkDetection();


        AppController.getInstance().setBusActivityContext(this);
    }

    private void initializeListeners() {
        tvLeavingFrom.setOnClickListener(this);
        tvGoingTo.setOnClickListener(this);
        ivChange.setOnClickListener(this);
        llDateSelection.setOnClickListener(this);
        llSearch.setOnClickListener(this);
    }

    private void getValuesFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            toCity = intent.getStringExtra("to");
            fromCity = intent.getStringExtra("from");
           String returns = intent.getStringExtra("return");
           if (returns.equals("return"))
           {
               tvLeavingFrom.setText(fromCity);
               tvGoingTo.setText(toCity);
           }
        }
    }

    private void selectCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy-EEE", Locale.getDefault());
        SimpleDateFormat selectedDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDate = selectedDateFormat.format(c.getTime());

        String formattedDate = df.format(c.getTime());
        String[] dateMonthDay = formattedDate.split("-");
        tvSelectedName.setText(getString(R.string.today));
        tvSelectedMonth.setText(dateMonthDay[1]);
        tvSelectedDay.setText(dateMonthDay[3]);
        tvSelectedDate.setText(dateMonthDay[0]);
    }

    private void serviceCallForBusStations() {
        if (networkDetection.isMobileNetworkAvailable(this) || networkDetection.isWifiAvailable(this)) {
            BusStationsHttpClient busStationsHttpClient = new BusStationsHttpClient(this);
            busStationsHttpClient.callBack = this;
            busStationsHttpClient.getJsonForType();
        } else {
            closeProgressbar();
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker() {
        Calendar calender = Calendar.getInstance();

        Log.e("dateSelected2",dateSelected);

        if (dateSelected.equals(""))
        {
            Log.e("dateSelected",dateSelected);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DateAndTimePicker, this, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
            if (!datePickerDialog.isShowing())
                datePickerDialog.show();
            else
                datePickerDialog.dismiss();

            //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }else
        {
            Log.e("dateSelected3",dateSelected);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date date = null;
            try {
                date = formatter.parse(dateSelected);
                calender.setTime(date);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DateAndTimePicker, this, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
                if (!datePickerDialog.isShowing())
                    datePickerDialog.show();
                else
                    datePickerDialog.dismiss();

              //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }






    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvLeavingFrom:
                goToBusStations(getString(R.string.leaving_from));
                break;
            case R.id.tvGoingTo:
                goToBusStations(getString(R.string.going_to));
                break;
            case R.id.ivChange:
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
                ivChange.startAnimation(animation);

                String tempLeavingStationName = goingToStationName;
                String tempGoingStationName = leavingFromStation;

                goingToStationName = tempGoingStationName;
                leavingFromStation = tempLeavingStationName;

                tvLeavingFrom.setText(leavingFromStation);
                tvGoingTo.setText(goingToStationName);
                break;
            case R.id.llDateSelection:
                showDatePicker();
                break;
            case R.id.llSearch:

                goingToStationName = tvGoingTo.getText().toString().trim();
                leavingFromStation = tvLeavingFrom.getText().toString().trim();

                if (!leavingFromStation.isEmpty()) {
                    if (!goingToStationName.isEmpty()) {
                        if (!leavingFromStation.equalsIgnoreCase(goingToStationName) || !goingToStationName.equalsIgnoreCase(leavingFromStation)) {
                            showProgressBar();
                            serviceCallForAvailableBusStations();
                        } else {
                            Toast.makeText(this, getString(R.string.source_and_destination_should_not_be_same), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.please_select_going_to_station), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.please_select_leaving_station), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void serviceCallForAvailableBusStations() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.AVAILABLE_BUS_PARAM_SOURCE, leavingFromStation);
            mapOfRequestParams.put(Constants.AVAILABLE_BUS_PARAM_DESTINATION, goingToStationName);
            mapOfRequestParams.put(Constants.AVAILABLE_BUS_PARAM_DOJ, selectedDate);

            AvailableBusesHttpClient availableBusesHttpClient = new AvailableBusesHttpClient(this);
            availableBusesHttpClient.callBack = this;
            availableBusesHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            closeProgressbar();
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_BUS_STATIONS:
                if (jsonResponse != null) {
                    BusStations busStations = new Gson().fromJson(jsonResponse, BusStations.class);
                    if (busStations != null) {
                        boolean status = busStations.getApiStatus().getSuccess();
                        if (status) {
                            listOfBusStations = busStations.getStationList();
                            if (listOfBusStations != null) {
                                PreferenceConnector.writeString(this, getString(R.string.bus_stations_response), jsonResponse);
                                AppController.getInstance().setListOfBusStations(listOfBusStations);
                            } else {
                                Toast.makeText(this, getString(R.string.no_routes_available), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.no_stations_found), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            case Constants.SERVICE_GET_AVAILABLE_BUS:

                try
                {
                    if (jsonResponse != null) {
                        Log.e("json",jsonResponse);
                        AvailableBuses availableBuses = new Gson().fromJson(jsonResponse, AvailableBuses.class);
                        if (availableBuses != null) {
                            boolean status = availableBuses.getApiStatus().getSuccess();
                            String message = availableBuses.getApiStatus().getMessage();
                            if (status) {
                                listOfAvailableBuses = availableBuses.getApiAvailableBuses();
                                if (listOfAvailableBuses != null && listOfAvailableBuses.size() != 0) {
                                    AppController.getInstance().setListOfAvailableBuses(listOfAvailableBuses);
                                    goToAvailableBusesActivity();
                                }
                            } else {
                                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "No Route Available", Toast.LENGTH_SHORT).show();
                }

                closeProgressbar();
                break;
            default:
                break;
        }
    }

    private void goToAvailableBusesActivity() {
        Intent intent = new Intent(this, AvailableBusesActivity.class);
        intent.putExtra(getString(R.string.leaving_from), leavingFromStation);
        intent.putExtra(getString(R.string.going_to), goingToStationName);
        intent.putExtra(getString(R.string.doj), selectedDate);
        startActivity(intent);
    }

    private void goToBusStations(String clickedOn) {
        Intent busStationsIntent = new Intent(this, BusStationsActivity.class);
        busStationsIntent.putExtra(getString(R.string.clicked_on), clickedOn);
        startActivity(busStationsIntent);
    }


    @Override
    public void selectedBusStation(StationList stationList, String clickedOn) {
        if (clickedOn.equalsIgnoreCase(getString(R.string.leaving_from))) {
            leavingFromStation = stationList.getStationName();
            leavingFromStationId = stationList.getStationId();
            PreferenceConnector.writeString(this, getString(R.string.leaving_from_station_id), String.valueOf(leavingFromStationId));
            tvLeavingFrom.setText(leavingFromStation);
        } else {
            goingToStationName = stationList.getStationName();
            goingToStationId = stationList.getStationId();
            PreferenceConnector.writeString(this, getString(R.string.going_to_station_id), String.valueOf(goingToStationId));
            tvGoingTo.setText(goingToStationName);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int currentMonth = month + 1;
        String monthString = String.valueOf(currentMonth);
        if (monthString.length() == 1) {
            monthString = "0" + "" + monthString;
        }
        selectedDate = year + "-" + monthString + "-" + dayOfMonth;
        dateSelected = year + "/" + monthString + "/" + dayOfMonth;
        Log.e("selectedDate",dateSelected);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE", Locale.getDefault());
        Date date = new Date(year, month, dayOfMonth - 1);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
        String formattedDate = df.format(c.getTime());

        String monthName = getMonth(month);
        String dayOfWeek = simpledateformat.format(date);

        if (dayOfMonth == Integer.parseInt(formattedDate)) {
            tvSelectedName.setVisibility(View.VISIBLE);
            tvSelectedName.setText(getString(R.string.today));
        } else if (dayOfMonth == (Integer.parseInt(formattedDate) + 1)) {
            tvSelectedName.setVisibility(View.VISIBLE);
            tvSelectedName.setText(getString(R.string.tomorrow));
        } else {
            tvSelectedName.setVisibility(View.GONE);
        }
        tvSelectedMonth.setText(monthName);
        tvSelectedDay.setText(dayOfWeek);
        tvSelectedDate.setText(String.valueOf(dayOfMonth));
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent intent = new Intent(this, HomeActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(intent);
    }


}
