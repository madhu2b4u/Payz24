package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payz24.R;
import com.payz24.adapter.DomesticFlightsSearchRoundTripTabsAdapter;
import com.payz24.application.AppController;
import com.payz24.fragment.DomesticFlightsSearchRoundTripReturnFragment;
import com.payz24.fragment.DomesticFlightsSearchRoundTripDepartFragment;
import com.payz24.http.DomesticFlightsSearchRoundTripHttpClient;
import com.payz24.interfaces.FlightFilterCallBack;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.interfaces.RoundTripFlightSelectedCallBack;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.DomesticFlightsSearchRoundTripResponse;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.ResponseDepart;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.ResponseReturn;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.Result;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mahesh on 2/11/2018.
 */

public class DomesticFlightsSearchRoundTripActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack, RoundTripFlightSelectedCallBack {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivBack, ivOriginFlight, ivDestinationFlight;
    private TextView tvSource, tvDestination, tvDetails, tvTotalAmount;
    private ImageView ivFlightImage;
    private ImageView ivDepartFilter,ivReturnFilter;
    private Button btnBookNow;
    private LinearLayout llFlightDetails;
    private LinearLayout llLoading, llViewPager;
    private Map<String, String> mapOfRequestParams;
    private String sourceFullName = "";
    private String destinationFullName = "";
    private String departSelectedDate = "";
    private String returnSelectedDate = "";
    private String details = "";
    private String sourceCountry = "";
    private String destinationCountry = "";
    private String origin = "";
    private String destination = "";
    private boolean isToSelected;
    private boolean isFromSelected;
    private OriginDestinationOption toOriginDestinationOption;
    private OriginDestinationOption fromOriginDestinationOption;
    private String departureDetails = "";
    private String returnDetails = "";
    private String url = "";
    private String numberOfStops;
    private int adultCount;
    private int childrenCount;
    private int infantCount;
    private double markUpFee = 0.0;
    String mTo,mFrom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_domestic_flights_search_round_trip);
        getDataFromIntent();
        initializeUi();
        initializeListeners();
        llLoading.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        serviceCallForFlightsResults();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mapOfRequestParams = (Map<String, String>) bundle.getSerializable(Constants.SEARCH_RESULTS_MAP);
            sourceFullName = bundle.getString(getString(R.string.source));
            destinationFullName = bundle.getString(getString(R.string.destination));
            departSelectedDate = bundle.getString(getString(R.string.selected_depature_date));
            returnSelectedDate = bundle.getString(getString(R.string.selected_return_date));
            details = bundle.getString(getString(R.string.details));
            sourceCountry = bundle.getString(getString(R.string.source_country));
            destinationCountry = bundle.getString(getString(R.string.destination_country));
            mTo = bundle.getString("mTo");
            mFrom = bundle.getString("mFrom");
            adultCount = bundle.getInt(getString(R.string.adult_count));
            childrenCount = bundle.getInt(getString(R.string.children_count));
            infantCount = bundle.getInt(getString(R.string.infant_count));

            Log.e("source",sourceFullName);
            Log.e("sourceCountry",sourceCountry);

        }
    }


    private void initializeUi() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        ivBack = findViewById(R.id.ivBack);
        ivDepartFilter = findViewById(R.id.ivDepartFilter);
        ivReturnFilter = findViewById(R.id.ivReturnFilter);
        ivOriginFlight = findViewById(R.id.ivOriginFlight);
        ivDestinationFlight = findViewById(R.id.ivDestinationFlight);
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvDetails = findViewById(R.id.tvDetails);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        llViewPager = findViewById(R.id.llViewPager);
        llLoading = findViewById(R.id.llLoading);
        llFlightDetails = findViewById(R.id.llFlightDetails);
        ivFlightImage = findViewById(R.id.ivFlightImage);
        llViewPager.setVisibility(View.GONE);
        btnBookNow = findViewById(R.id.btnBookNow);

        tvSource.setText(mFrom);
        tvDestination.setText(mTo);
        List<String> listOfPassengersCount = new ArrayList<>();
        String passengersCount = "";
        if (adultCount != 0) {
            passengersCount = String.valueOf(adultCount) + " " + getString(R.string.adult);
            listOfPassengersCount.add(passengersCount);
        }
        if (childrenCount != 0) {
            passengersCount = String.valueOf(childrenCount) + " " + getString(R.string.child);
            listOfPassengersCount.add(passengersCount);
        }
        if (infantCount != 0) {
            passengersCount = String.valueOf(infantCount) + " " + getString(R.string.infants);
            listOfPassengersCount.add(passengersCount);
        }
        String details = departSelectedDate + " - " + returnSelectedDate + " | " + TextUtils.join(",", listOfPassengersCount);
        tvDetails.setText(details);
        tvTotalAmount.setText(getString(R.string.Rs) + " " + "0");
        AppController.getInstance().setDomesticFlightsSearchRoundTripActivityContext(this);
        Glide.with(this)
                .load(R.drawable.ic_flight_loading)
                .into(ivFlightImage);
    }

    private void initializeListeners() {
        ivBack.setOnClickListener(this);
        btnBookNow.setOnClickListener(this);
    }

    private void setupViewpager() {
        DomesticFlightsSearchRoundTripTabsAdapter domesticFlightsSearchRoundTripTabs = new DomesticFlightsSearchRoundTripTabsAdapter(this, getSupportFragmentManager(), tabLayout, viewPager, ivDepartFilter,ivReturnFilter);
        domesticFlightsSearchRoundTripTabs.addTab(tabLayout.newTab().setText(origin + " - " + destination).setTag(origin + " - " + destination), DomesticFlightsSearchRoundTripDepartFragment.class, null);
        domesticFlightsSearchRoundTripTabs.addTab(tabLayout.newTab().setText(destination + " - " + origin).setTag(destination + " - " + origin), DomesticFlightsSearchRoundTripReturnFragment.class, null);
    }

    private void serviceCallForFlightsResults() {
        DomesticFlightsSearchRoundTripHttpClient domesticFlightsSearchRoundTripHttpClient = new DomesticFlightsSearchRoundTripHttpClient(this);
        domesticFlightsSearchRoundTripHttpClient.callBack = this;
        domesticFlightsSearchRoundTripHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnBookNow:



                if (isFromSelected && isToSelected) {
                    Intent domesticFlightsSearchRoundTripFlightReviewIntent = new Intent(this, DomesticFlightsSearchRoundTripFlightsReviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string.origin), origin);
                    bundle.putString(getString(R.string.destination), destination);
                    bundle.putInt(getString(R.string.adult_count), adultCount);
                    bundle.putInt(getString(R.string.children_count), childrenCount);
                    bundle.putInt(getString(R.string.infant_count), infantCount);
                    bundle.putString(getString(R.string.source_country), sourceCountry);
                    bundle.putString(getString(R.string.destination_country), destinationCountry);
                    bundle.putString(getString(R.string.selected_depature_date), departSelectedDate);
                    bundle.putString(getString(R.string.selected_return_date), returnSelectedDate);
                    domesticFlightsSearchRoundTripFlightReviewIntent.putExtras(bundle);
                    startActivity(domesticFlightsSearchRoundTripFlightReviewIntent);
                } else {
                    Toast.makeText(this, getString(R.string.please_select_return_journey), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP:
                if (jsonResponse != null) {
                    try {


                        DomesticFlightsSearchRoundTripResponse domesticFlightsSearchRoundTripResponse = new Gson().fromJson(jsonResponse, DomesticFlightsSearchRoundTripResponse.class);
                        if (domesticFlightsSearchRoundTripResponse != null) {
                            Result result = domesticFlightsSearchRoundTripResponse.getResult();
                            origin = result.getRequest().getOrigin();
                            destination = result.getRequest().getDestination();
                            ResponseDepart responseDepart = result.getResponseDepart();
                            ResponseReturn responseReturn = result.getResponseReturn();
                            AppController.getInstance().setResult(result);
                            AppController.getInstance().setResponseDepart(responseDepart);
                            AppController.getInstance().setResponseReturn(responseReturn);
                        } else {
                            finish();
                            Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                        }
                        setupViewpager();
                    } catch (Exception exception) {
                        finish();
                        Toast.makeText(this, getString(R.string.no_results_found), Toast.LENGTH_SHORT).show();
                        exception.printStackTrace();
                    }

                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                llLoading.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                llViewPager.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void roundTripFlightSelected(OriginDestinationOption originDestinationOption, String from) {
        if (from.equalsIgnoreCase(getString(R.string.depart))) {
            isToSelected = true;
            toOriginDestinationOption = originDestinationOption;

        } else {
            isFromSelected = true;
            fromOriginDestinationOption = originDestinationOption;
            if (isToSelected && isFromSelected) {
                String toFare = toOriginDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
                String toTax = toOriginDestinationOption.getFareDetails().getChargeableFares().getTax();
                String fromFare = fromOriginDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
                String fromTax = fromOriginDestinationOption.getFareDetails().getChargeableFares().getTax();
                int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
                String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
                Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                markUpFee = ((Integer.parseInt(toFare)+Integer.parseInt(fromFare)) * markUpPercentage);
                double totalFare=0.0;
                if (busMType.equalsIgnoreCase("M"))
                    totalFare = Integer.parseInt(toFare) + Integer.parseInt(toTax) + Integer.parseInt(fromFare) + Integer.parseInt(fromTax)-markUpFee;
                else
                    totalFare = Integer.parseInt(toFare) + Integer.parseInt(toTax) + Integer.parseInt(fromFare) + Integer.parseInt(fromTax)+markUpFee;




                Log.e("tofare",toFare);
                Log.e("toTax",toTax);
                Log.e("fromFare",fromFare);
                Log.e("fromTax",fromTax);
                Log.e("totalFare",""+totalFare);
                Log.e("markUpFee",""+markUpFee);


                tvTotalAmount.setText(getString(R.string.Rs) + " " + String.valueOf(totalFare));
                llFlightDetails.setVisibility(View.VISIBLE);
                AppController.getInstance().setSelectedToOriginDestinationOption(toOriginDestinationOption);
                AppController.getInstance().setSelectedFromOriginDestinationOption(fromOriginDestinationOption);
                Glide.with(this)
                        .load(getImageUrl(toOriginDestinationOption))
                        .into(ivOriginFlight);

                Glide.with(this)
                        .load(getImageUrl(fromOriginDestinationOption))
                        .into(ivDestinationFlight);
                ivOriginFlight.setVisibility(View.VISIBLE);
                ivDestinationFlight.setVisibility(View.VISIBLE);
            }
        }
    }

    private String getImageUrl(OriginDestinationOption originDestinationOption) {
        JsonObject jsonObject = originDestinationOption.getFlightSegments().getAsJsonObject();
        JSONObject flightSegmentJsonObject = null;
        try {
            flightSegmentJsonObject = new JSONObject(jsonObject.toString());
            Object json = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
            if (json instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), FlightSegment.class);
                url = flightSegment.getImageFileName();
                numberOfStops = flightSegment.getStopQuantity();
                String[] departureTime = flightSegment.getDepartureDateTime().split("T");
                String[] arrivalTime = flightSegment.getArrivalDateTime().split("T");
            } else if (json instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                String response = jsonArray.getString(0);
                FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                url = flightSegment.getImageFileName();
                numberOfStops = flightSegment.getStopQuantity();
                String departureDateTime = flightSegment.getDepartureDateTime();
                String arrivalDateTime = new Gson().fromJson(jsonArray.getString(jsonArray.length() - 1), FlightSegment.class).getArrivalDateTime();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }
}
