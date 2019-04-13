package com.payz24.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.http.FlightsStationsListHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.interfaces.SelectedFlightsStationsCallBack;
import com.payz24.responseModels.AdultDetailsRequest.AdultDetailsRequest;
import com.payz24.responseModels.flightStations.FlightStations;
import com.payz24.responseModels.flightStations.Station;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mahesh on 2/3/2018.
 */

public class FlightsActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, SelectedFlightsStationsCallBack, DatePickerDialog.OnDateSetListener, HttpReqResCallBack {

    private RadioButton rbOneWay, rbRoundTrip;
    private LinearLayout llSourceLocation, llDestinationLocation, llDepart, llReturn, llPassengersDetails, llClass;
    private TextView tvSourceShortName, tvSourceFullName, tvDestinationShortName, tvDestinationFullName, tvSelectedDate, tvWeekDay, tvReturnDate, tvReturnWeekDay, tvPassengerDetails, tvClassDetails;
    private Button btnGetSetGo;
    private String selectedDate;
    private ImageView ivFlightsExchange;
    private boolean isDepartDateSelected;
    private List<Station> listOfFlightStations;
    private NetworkDetection networkDetection;
    private BottomSheetDialog dialog;
    private ImageView ivAdultsMinus, ivAdultsPlus, ivChildrenMinus, ivChildrenPlus, ivInfantsMinus, ivInfantsPlus;
    private TextView tvAdultsCount, tvChildrenCount, tvInfantsCount, tvCancel, tvDone;
    private TextView tvClassCancel, tvEconomy, tvBusiness;
    private int adultCount = 1;
    private int childrenCount = 0;
    private int infantCount = 0;
    private BottomSheetDialog classDialog;
    private String sourceShortCode = "";
    private String sourceFullName = "";
    private String destinationShortCode = "";
    private String destinationFullName = "";
    private String selectedDepartDate = "";
    private String selectedReturnDate = "";
    private String selectedClass = "E";
    private String departSelectedDate = "";
    private String returnSelectedDate = "";
    private String sourceCountry = "";
    private String destinationCountry = "";
    private String flightStationsResponse = "";
    String dateSelected="";
    boolean mak=true;
    boolean compare=true;
    String d1,d2;

    String mTo,mFrom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flights_activity);
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        setCurrentDate();
        rbOneWay.setChecked(true);
        flightStationsResponse = PreferenceConnector.readString(this, getString(R.string.stations_response), "");
        showProgressBar();
        if (!flightStationsResponse.isEmpty()) {
            jsonResponseReceived(flightStationsResponse, 200, Constants.SERVICE_FLIGHT_STATIONS);
        } else {
            serviceCallForFlightStations();
        }
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.flights));
        setSupportActionBar(toolbar);

        rbOneWay = findViewById(R.id.rbOneWay);
        rbRoundTrip = findViewById(R.id.rbRoundTrip);
        llSourceLocation = findViewById(R.id.llSourceLocation);
        llDestinationLocation = findViewById(R.id.llDestinationLocation);
        llDepart = findViewById(R.id.llDepart);
        llReturn = findViewById(R.id.llReturn);
        llPassengersDetails = findViewById(R.id.llPassengersDetails);
        llClass = findViewById(R.id.llClass);
        tvSourceShortName = findViewById(R.id.tvSourceShortName);
        tvSourceFullName = findViewById(R.id.tvSourceFullName);
        tvDestinationShortName = findViewById(R.id.tvDestinationShortName);
        tvDestinationFullName = findViewById(R.id.tvDestinationFullName);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvWeekDay = findViewById(R.id.tvWeekDay);
        tvReturnDate = findViewById(R.id.tvReturnDate);
        tvReturnWeekDay = findViewById(R.id.tvReturnWeekDay);
        tvPassengerDetails = findViewById(R.id.tvPassengerDetails);
        tvClassDetails = findViewById(R.id.tvClassDetails);
        ivFlightsExchange = findViewById(R.id.ivFlightsExchange);
        btnGetSetGo = findViewById(R.id.btnGetSetGo);

        networkDetection = new NetworkDetection();
        AppController.getInstance().setFlightsActivityContext(this);
        AppController.getInstance().setMapOfPositionAdultDetailsRequest(new LinkedHashMap<Integer, AdultDetailsRequest>());
    }

    private void initializeListeners() {
        rbOneWay.setOnCheckedChangeListener(this);
        rbRoundTrip.setOnCheckedChangeListener(this);
        llSourceLocation.setOnClickListener(this);
        llDestinationLocation.setOnClickListener(this);
        llDepart.setOnClickListener(this);
        llReturn.setOnClickListener(this);
        llPassengersDetails.setOnClickListener(this);
        llClass.setOnClickListener(this);
        tvPassengerDetails.setOnClickListener(this);
        btnGetSetGo.setOnClickListener(this);
        ivFlightsExchange.setOnClickListener(this);
    }

    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy-EEEE", Locale.getDefault());
        SimpleDateFormat dff = new SimpleDateFormat("dd-MM-yyyy-EEEE", Locale.getDefault());
        SimpleDateFormat selectedDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDate = selectedDateFormat.format(calendar.getTime());
        selectedDepartDate = selectedDate;

        String formattedDate = df.format(calendar.getTime());
        String[] dayOfWeek = formattedDate.split("-");

        departSelectedDate = dayOfWeek[0] + " " + dayOfWeek[1] + "'" + " " + dayOfWeek[2];
        AppController.getInstance().setDepartSelectedDate(departSelectedDate);
        tvSelectedDate.setText(departSelectedDate);
        tvWeekDay.setText(getString(R.string.today) + "," + dayOfWeek[3]);


        String formattedDates = dff.format(calendar.getTime());
        String[] dayOfWeeks = formattedDates.split("-");
        d1=dayOfWeeks[2]+"/"+dayOfWeeks[1]+"/"+dayOfWeeks[0];

    }

    private void serviceCallForFlightStations() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            FlightsStationsListHttpClient flightsStationsListHttpClient = new FlightsStationsListHttpClient(this);
            flightsStationsListHttpClient.callBack = this;
            flightsStationsListHttpClient.getJsonForType();
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            closeProgressbar();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.rbOneWay:
                if (isChecked) {
                    if (rbRoundTrip.isChecked())
                        rbRoundTrip.setChecked(false);
                }
                tvReturnWeekDay.setText("");
                tvReturnWeekDay.setVisibility(View.GONE);
                tvReturnDate.setText(getString(R.string.select_date_to_book_return));
                break;
            case R.id.rbRoundTrip:
                if (isChecked) {
                    if (rbOneWay.isChecked())
                        rbOneWay.setChecked(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llSourceLocation:
                if (listOfFlightStations != null && listOfFlightStations.size() != 0) {
                    getStationNames(getString(R.string.source));
                } else {
                    Toast.makeText(this, getString(R.string.no_stations_available), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.llDestinationLocation:
                if (listOfFlightStations != null && listOfFlightStations.size() != 0) {
                    getStationNames(getString(R.string.destination));
                } else {
                    Toast.makeText(this, getString(R.string.no_stations_available), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.llDepart:
                isDepartDateSelected = true;
                showDatePicker();
                break;
            case R.id.llReturn:
                mak=false;
                isDepartDateSelected = false;
                showDatePicker();
                break;
            case R.id.llPassengersDetails:
                showPassengerDetailsBottomSheetView();
                break;
            case R.id.tvPassengerDetails:
                showPassengerDetailsBottomSheetView();
                break;
            case R.id.llClass:
                showClassBottomSheetView();
                break;
            case R.id.ivFlightsExchange:
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
                ivFlightsExchange.startAnimation(animation);

                String tempSourceCode = destinationShortCode;
                String tempDestinationCode = sourceShortCode;
                String tempSourceFullName = destinationFullName;
                String tempDestinationFullName = sourceFullName;
                sourceShortCode = tempSourceCode;
                destinationShortCode = tempDestinationCode;
                sourceFullName = tempSourceFullName;
                destinationFullName = tempDestinationFullName;

                tvSourceShortName.setText(sourceShortCode);
                tvDestinationShortName.setText(destinationShortCode);
                tvSourceFullName.setText(sourceFullName);
                tvDestinationFullName.setText(destinationFullName);
                break;
            case R.id.btnGetSetGo:
                if (!sourceShortCode.isEmpty()) {
                    if (!destinationShortCode.isEmpty()) {
                        boolean isRoundTripSelected = rbRoundTrip.isChecked();
                        if (isRoundTripSelected) {
                            if (!selectedDepartDate.isEmpty()) {
                                if (!selectedReturnDate.isEmpty()) {

                                   if (compare)
                                   {
                                       if (sourceCountry.equalsIgnoreCase(destinationCountry)) {
                                           goToDomesticFlightSearchRoundTrip();
                                       } else {
                                           goToInternationalFlightSearchRoundTrip();
                                       }
                                   }else
                                   {
                                       Toast.makeText(this, "Return date should be greater than are equal to depart date", Toast.LENGTH_SHORT).show();
                                   }



                                } else {
                                    Toast.makeText(this, getString(R.string.please_select_return_date), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, getString(R.string.please_select_depature_date), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (!selectedDepartDate.isEmpty()) {
                                gotoFlightResults();
                            } else {
                                Toast.makeText(this, getString(R.string.please_select_depature_date), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.please_enter_destination), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.please_enter_source), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ivAdultsMinus:
                if (adultCount != 1)
                    adultCount = adultCount - 1;
                tvAdultsCount.setText(String.valueOf(adultCount));
                break;
            case R.id.ivAdultsPlus:
                if (adultCount != 9) {
                    adultCount = adultCount + 1;
                } else {
                    Toast.makeText(this, getString(R.string.sorry_maximum_limit_is_nine), Toast.LENGTH_SHORT).show();
                }
                tvAdultsCount.setText(String.valueOf(adultCount));
                break;
            case R.id.ivChildrenMinus:
                if (childrenCount != 0) {
                    childrenCount = childrenCount - 1;
                }
                tvChildrenCount.setText(String.valueOf(childrenCount));
                break;
            case R.id.ivChildrenPlus:
                if (childrenCount != 6) {
                    childrenCount = childrenCount + 1;
                } else {
                    Toast.makeText(this, getString(R.string.sorry_maximum_limit_is_four), Toast.LENGTH_SHORT).show();
                }
                tvChildrenCount.setText(String.valueOf(childrenCount));
                break;
            case R.id.ivInfantsMinus:
                if (infantCount != 0) {
                    infantCount = infantCount - 1;
                }
                tvInfantsCount.setText(String.valueOf(infantCount));
                break;
            case R.id.ivInfantsPlus:
                if (infantCount != 6) {
                    infantCount = infantCount + 1;
                } else {
                    Toast.makeText(this, getString(R.string.sorry_maximum_limit_is_four), Toast.LENGTH_SHORT).show();
                }
                tvInfantsCount.setText(String.valueOf(infantCount));
                break;
            case R.id.tvCancel:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            case R.id.tvDone:
                String passengers = String.valueOf(adultCount) + "" + getString(R.string.adult) + "," + " "
                        + String.valueOf(childrenCount) + " " + getString(R.string.children) + "," + String.valueOf(infantCount) + " " + getString(R.string.infants);
                tvPassengerDetails.setText(passengers);
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            case R.id.tvEconomy:
                selectedClass = "E";
                tvClassDetails.setText(getString(R.string.economy));
                if (classDialog != null) {
                    classDialog.dismiss();
                }
                break;
            case R.id.tvBusiness:
                selectedClass = "B";
                tvClassDetails.setText(getString(R.string.business));
                if (classDialog != null) {
                    classDialog.dismiss();
                }
                break;
            case R.id.tvClassCancel:
                if (classDialog != null) {
                    classDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private void showPassengerDetailsBottomSheetView() {
        View view = getLayoutInflater().inflate(R.layout.layout_add_passengers_details, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        ivAdultsMinus = view.findViewById(R.id.ivAdultsMinus);
        ivAdultsPlus = view.findViewById(R.id.ivAdultsPlus);
        ivChildrenMinus = view.findViewById(R.id.ivChildrenMinus);
        ivChildrenPlus = view.findViewById(R.id.ivChildrenPlus);
        ivInfantsMinus = view.findViewById(R.id.ivInfantsMinus);
        ivInfantsPlus = view.findViewById(R.id.ivInfantsPlus);
        ivAdultsMinus = view.findViewById(R.id.ivAdultsMinus);
        tvAdultsCount = view.findViewById(R.id.tvAdultsCount);
        tvChildrenCount = view.findViewById(R.id.tvChildrenCount);
        tvInfantsCount = view.findViewById(R.id.tvInfantsCount);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvDone = view.findViewById(R.id.tvDone);

        tvAdultsCount.setText(String.valueOf(adultCount));
        tvChildrenCount.setText(String.valueOf(childrenCount));
        tvInfantsCount.setText(String.valueOf(infantCount));

        ivAdultsPlus.setOnClickListener(this);
        ivAdultsMinus.setOnClickListener(this);
        ivChildrenPlus.setOnClickListener(this);
        ivChildrenMinus.setOnClickListener(this);
        ivInfantsPlus.setOnClickListener(this);
        ivInfantsMinus.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        dialog.show();
    }

    private void showClassBottomSheetView() {
        View view = getLayoutInflater().inflate(R.layout.layout_flights_class_details, null);
        classDialog = new BottomSheetDialog(this);
        classDialog.setContentView(view);
        tvClassCancel = view.findViewById(R.id.tvClassCancel);
        tvEconomy = view.findViewById(R.id.tvEconomy);
        tvBusiness = view.findViewById(R.id.tvBusiness);
        tvClassCancel.setOnClickListener(this);
        tvEconomy.setOnClickListener(this);
        tvBusiness.setOnClickListener(this);
        classDialog.show();
    }

    private void showDatePicker() {
        Calendar calender = Calendar.getInstance();
        if (dateSelected.equals(""))
        {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DateAndTimePicker, this, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
            if (!datePickerDialog.isShowing())
                datePickerDialog.show();
            else
                datePickerDialog.dismiss();
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }else
        {
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

                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public static boolean compareDates(String d1,String d2)
    {
        boolean b = false;
      try
      {
          SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy/MM/dd");

          try {
              if(dfDate.parse(d1).before(dfDate.parse(d2)))
              {
                  b = true;//If start date is before end date
              }
              else if(dfDate.parse(d1).equals(dfDate.parse(d2)))
              {
                  b = true;//If two dates are equal
              }
              else
              {
                  b = false; //If start date is after the end date
              }
          } catch (ParseException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          return b;
      }catch (Exception e)
      {
          e.printStackTrace();
      }
        return b;
    }

    private void getStationNames(String from) {
        Intent flightsStationsNamesIntent = new Intent(this, FlightsStationNamesActivity.class);
        flightsStationsNamesIntent.putExtra(getString(R.string.from), from);
        startActivity(flightsStationsNamesIntent);
    }

    private void gotoFlightResults() {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM, sourceShortCode);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO, destinationShortCode);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE, selectedDepartDate);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT, String.valueOf(adultCount));
        if (childrenCount != 0)
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, String.valueOf(childrenCount));
        else
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, "");
        if (infantCount != 0)
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, String.valueOf(infantCount));
        else
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, "");

        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS, selectedClass);

        String details = String.valueOf(adultCount) + " " + getString(R.string.adult) + " - " + String.valueOf(childrenCount) + " " + getString(R.string.children) + " - " + String.valueOf(infantCount) + " " + getString(R.string.infants);
        Intent intent = new Intent(this, FlightSearchResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.SEARCH_RESULTS_MAP, (Serializable) mapOfRequestParams);
        bundle.putString(getString(R.string.source), sourceFullName);
        bundle.putString(getString(R.string.destination), destinationFullName);
        bundle.putString(getString(R.string.source_short_code), sourceShortCode);
        bundle.putString(getString(R.string.destination_short_code), destinationShortCode);
        bundle.putString(getString(R.string.selected_depature_date), departSelectedDate);
        bundle.putString(getString(R.string.selected_return_date), returnSelectedDate);
        bundle.putString(getString(R.string.source_country), sourceCountry);
        bundle.putString(getString(R.string.destination_country), destinationCountry);
        bundle.putString(getString(R.string.details), details);
        bundle.putInt(getString(R.string.adult_count), adultCount);
        bundle.putInt(getString(R.string.children_count), childrenCount);
        bundle.putInt(getString(R.string.infant_count), infantCount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void goToInternationalFlightSearchRoundTrip() {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM, sourceShortCode);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO, destinationShortCode);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE, selectedDepartDate);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_RETURN_DATE, selectedReturnDate);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT, String.valueOf(adultCount));
        mapOfRequestParams.put(Constants.SERVICE_INTERNATIONAL_FLIGHTS_PARAM_JOURNEY_TYPE, "round_trip");
        if (childrenCount != 0)
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, String.valueOf(childrenCount));
        else
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, "");
        if (infantCount != 0)
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, String.valueOf(infantCount));
        else
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, "");

        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS, selectedClass);

        //String details = String.valueOf(adultCount) + " " + getString(R.string.adult) + " - " + String.valueOf(childrenCount) + " " + getString(R.string.children) + " - " + String.valueOf(infantCount) + " " + getString(R.string.infants);
        Intent internationalFlightsIntent = new Intent(this, InternationalFlightsSearchRoundTripActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.SEARCH_RESULTS_MAP, (Serializable) mapOfRequestParams);
        bundle.putString(getString(R.string.source), sourceFullName);
        bundle.putString(getString(R.string.destination), destinationFullName);
        bundle.putString(getString(R.string.selected_depature_date), departSelectedDate);
        bundle.putString(getString(R.string.selected_return_date), returnSelectedDate);
        bundle.putString(getString(R.string.source_country), sourceCountry);
        bundle.putString(getString(R.string.destination_country), destinationCountry);
        bundle.putInt(getString(R.string.adult_count), adultCount);
        bundle.putInt(getString(R.string.children_count), childrenCount);
        bundle.putInt(getString(R.string.infant_count), infantCount);
        //bundle.putString(getString(R.string.details), details);
        internationalFlightsIntent.putExtras(bundle);
        startActivity(internationalFlightsIntent);
    }

    private void goToDomesticFlightSearchRoundTrip() {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM, sourceShortCode);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO, destinationShortCode);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE, selectedDepartDate);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_RETURN_DATE, selectedReturnDate);
        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT, String.valueOf(adultCount));
        mapOfRequestParams.put(Constants.SERVICE_INTERNATIONAL_FLIGHTS_PARAM_JOURNEY_TYPE, "round_trip");
        if (childrenCount != 0)
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, String.valueOf(childrenCount));
        else
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, "");
        if (infantCount != 0)
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, String.valueOf(infantCount));
        else
            mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, "");

        mapOfRequestParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS, selectedClass);

        //String details = String.valueOf(adultCount) + " " + getString(R.string.adult) + " - " + String.valueOf(childrenCount) + " " + getString(R.string.children) + " - " + String.valueOf(infantCount) + " " + getString(R.string.infants);
        Intent domesticFlightsIntent = new Intent(this, DomesticFlightsSearchRoundTripActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.SEARCH_RESULTS_MAP, (Serializable) mapOfRequestParams);
        bundle.putString(getString(R.string.source), sourceFullName);
        bundle.putString(getString(R.string.destination), destinationFullName);
        bundle.putString(getString(R.string.selected_depature_date), departSelectedDate);
        bundle.putString(getString(R.string.selected_return_date), returnSelectedDate);
        bundle.putString(getString(R.string.source_country), sourceCountry);
        bundle.putString(getString(R.string.destination_country), destinationCountry);
        bundle.putInt(getString(R.string.adult_count), adultCount);
        bundle.putInt(getString(R.string.children_count), childrenCount);
        bundle.putInt(getString(R.string.infant_count), infantCount);
        bundle.putString("mFrom", sourceShortCode);
        bundle.putString("mTo", destinationShortCode);
        //bundle.putString(getString(R.string.details), details);
        domesticFlightsIntent.putExtras(bundle);
        startActivity(domesticFlightsIntent);
    }

    @Override
    public void selectedFlightStations(Station flightStations, String from) {
        if (from.equalsIgnoreCase(getString(R.string.source))) {
            sourceShortCode = flightStations.getCityCode();
            sourceFullName = flightStations.getCity();
            sourceCountry = flightStations.getCountry();
            AppController.getInstance().setSourceShortCode(sourceShortCode);
            AppController.getInstance().setSourceCountry(sourceCountry);
            tvSourceShortName.setText(flightStations.getCityCode());
            tvSourceFullName.setText(flightStations.getCity());
        } else {
            destinationShortCode = flightStations.getCityCode();
            destinationFullName = flightStations.getCity();
            destinationCountry = flightStations.getCountry();
            AppController.getInstance().setDestinationShortCode(destinationShortCode);
            AppController.getInstance().setDestinationCountry(destinationCountry);
            tvDestinationShortName.setText(flightStations.getCityCode());
            tvDestinationFullName.setText(flightStations.getCity());
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int currentMonth = month + 1;
        String selectedCurrentMonth = String.valueOf(currentMonth);
        String selectedDayOfMonth = String.valueOf(dayOfMonth);
        if (selectedCurrentMonth.length() == 1) {
            selectedCurrentMonth = "0" + String.valueOf(currentMonth);
        } else {
            selectedCurrentMonth = String.valueOf(currentMonth);
        }
        if (selectedDayOfMonth.length() == 1) {
            selectedDayOfMonth = "0" + String.valueOf(dayOfMonth);
        } else {
            selectedDayOfMonth = String.valueOf(dayOfMonth);
        }
        selectedDate = year + "-" + selectedCurrentMonth + "-" + selectedDayOfMonth;
        dateSelected = year + "/" + selectedCurrentMonth + "/" + selectedDayOfMonth;
        Log.e("selectedDate",dateSelected);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(year, month, dayOfMonth - 1);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String formattedDate = df.format(c.getTime());

        String monthName = getMonth(month);
        String dayOfWeek = simpledateformat.format(date);

        if (dayOfMonth == Integer.parseInt(formattedDate)) {
            if (isDepartDateSelected) {
                d1=year + "/" + selectedCurrentMonth + "/" + selectedDayOfMonth;
                departSelectedDate = dayOfMonth + " " + monthName + "'" + " " + year;
                AppController.getInstance().setDepartSelectedDate(departSelectedDate);
                selectedDepartDate = selectedDate;
                tvSelectedDate.setText(departSelectedDate);
                tvWeekDay.setText(getString(R.string.today) + "," + dayOfWeek);
            } else {
                d2=year + "/" + selectedCurrentMonth + "/" + selectedDayOfMonth;
                returnSelectedDate = dayOfMonth + " " + monthName + "'" + " " + year;
                AppController.getInstance().setReturnSelectedDate(returnSelectedDate);
                selectedReturnDate = selectedDate;
                rbRoundTrip.setChecked(true);
                tvReturnDate.setText(returnSelectedDate);
                tvReturnWeekDay.setText(getString(R.string.today) + "," + dayOfWeek);
                tvReturnWeekDay.setVisibility(View.VISIBLE);
            }
        } else if (dayOfMonth == (Integer.parseInt(formattedDate) + 1)) {
            if (isDepartDateSelected) {
                d1=year + "/" + selectedCurrentMonth + "/" + selectedDayOfMonth;
                departSelectedDate = dayOfMonth + " " + monthName + "'" + " " + year;
                AppController.getInstance().setDepartSelectedDate(departSelectedDate);
                selectedDepartDate = selectedDate;
                tvSelectedDate.setText(departSelectedDate);
                tvWeekDay.setText(getString(R.string.tomorrow) + "," + dayOfWeek);
            } else {
                d2=year + "/" + selectedCurrentMonth + "/" + selectedDayOfMonth;
                returnSelectedDate = dayOfMonth + " " + monthName + "'" + " " + year;
                AppController.getInstance().setReturnSelectedDate(returnSelectedDate);
                selectedReturnDate = selectedDate;
                rbRoundTrip.setChecked(true);
                tvReturnDate.setText(returnSelectedDate);
                tvReturnWeekDay.setText(getString(R.string.tomorrow) + "," + dayOfWeek);
                tvReturnWeekDay.setVisibility(View.VISIBLE);
            }
        } else {
            if (isDepartDateSelected) {
                d1=year + "/" + selectedCurrentMonth + "/" + selectedDayOfMonth;
                departSelectedDate = dayOfMonth + " " + monthName + "'" + " " + year;
                AppController.getInstance().setDepartSelectedDate(departSelectedDate);
                selectedDepartDate = selectedDate;
                tvSelectedDate.setText(departSelectedDate);
                tvWeekDay.setText(dayOfWeek);
            } else {
                d2=year + "/" + selectedCurrentMonth + "/" + selectedDayOfMonth;
                returnSelectedDate = dayOfMonth + " " + monthName + "'" + " " + year;
                AppController.getInstance().setReturnSelectedDate(returnSelectedDate);
                selectedReturnDate = selectedDate;
                rbRoundTrip.setChecked(true);
                tvReturnDate.setText(returnSelectedDate);
                tvReturnWeekDay.setText(dayOfWeek);
                tvReturnWeekDay.setVisibility(View.VISIBLE);
            }
        }
        if (!mak)
        {
            compare=compareDates(d1,d2);
            Log.e("s",""+compare);
        }



    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getShortMonths()[month];
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_FLIGHT_STATIONS:
//                Log.e("json",jsonResponse);
                if (jsonResponse != null) {
                   try {
                       Gson gson = new Gson();
                       FlightStations flightStations =gson.fromJson(jsonResponse, FlightStations.class);
                       if (flightStations != null) {
                           listOfFlightStations = flightStations.getStations();
                       }
                       AppController.getInstance().setListOfFlightStations(listOfFlightStations);
                       PreferenceConnector.writeString(this, getString(R.string.stations_response), jsonResponse);
                   }catch (Exception e)
                   {
                       e.printStackTrace();
                       finish();
                       Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
                   }
                }
                closeProgressbar();
                break;
            default:
                break;
        }
    }
}
