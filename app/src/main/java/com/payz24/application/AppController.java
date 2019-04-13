package com.payz24.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.payz24.activities.FlightsActivity;
import com.payz24.fragment.DomesticFlightsSearchRoundTripDepartFragment;
import com.payz24.fragment.DomesticFlightsSearchRoundTripReturnFragment;
import com.payz24.responseModels.AdultDetailsRequest.AdultDetailsRequest;
import com.payz24.responseModels.AdultDetailsRequest.ChildDetailsRequest;
import com.payz24.responseModels.AdultDetailsRequest.InfantDetailsRequest;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.ResponseDepart;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.ResponseReturn;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.Result;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.availableBuses.BoardingPoint;
import com.payz24.responseModels.blockTicket.BlockTicket;
import com.payz24.responseModels.busLayout.Seat;
import com.payz24.responseModels.busStations.StationList;
import com.payz24.responseModels.flightSearchResults.AvailRequest;
import com.payz24.responseModels.flightSearchResults.OriginDestinationOption;
import com.payz24.responseModels.flightStations.FlightStations;
import com.payz24.responseModels.flightStations.Station;
import com.payz24.utils.HttpsTrustManager;
import com.splunk.mint.Mint;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 10/27/2017.
 */

public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();


    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    private static AppController mInstance;
    private Context mobileRechargeContext;
    private Context busActivityContext;
    private Context availableBusesContext;
    private Context flightTravellerDetailsContext;
    private Context domesticFlightsSearchRoundTripActivityContext;
    private Context flightSearchResultsActivityContext;
    private Context internationalFlightsSearchRoundTripActivityContext;
    private DomesticFlightsSearchRoundTripDepartFragment domesticFlightsSearchRoundTripDepartFragmentContext;
    private DomesticFlightsSearchRoundTripReturnFragment domesticFlightsSearchRoundTripReturnFragmentContext;

    private List<StationList> listOfBusStations;
    private List<ApiAvailableBus> listOfAvailableBuses;
    private List<Seat> listOfSelectedSeat;
    private List<Station> listOfFlightStations;
    private LinkedList<String> listOfSelectedOperatorsNames;
    private LinkedList<String> listOfAirwayNames;
    private LinkedList<String> listOfAirwayImages;
    private LinkedList<String> listOfAirwayFares;
    private LinkedHashMap<Integer, AdultDetailsRequest> mapOfPositionAdultDetailsRequest;
    private LinkedHashMap<Integer, InfantDetailsRequest> mapOfPositionInfantDetailsRequest;
    private LinkedHashMap<Integer, ChildDetailsRequest> mapOfPositionChildDetailsRequest;
    private LinkedHashMap<String, String> mapOfContactNameAndNumber;
    private ApiAvailableBus selectedAvailableBus;
    private BoardingPoint selectedBoardingPoint;
    private BlockTicket blockTicket;
    private Context flightsActivityContext;
    private ResponseDepart responseDepart;
    private ResponseReturn responseReturn;
    private OriginDestinationOption selectedOriginDestinationOption;
    private AvailRequest availRequest;
    private String sourceShortCode = "";
    private String destinationShortCode = "";
    private String departSelectedDate = "";
    private String returnSelectedDate = "";
    private String sourceCountry = "";
    private String destinationCountry = "";
    private com.payz24.responseModels.DomesticFlightsSearchRoundTrip.Result result;
    private com.payz24.responseModels.flightBookingHistory.Result selectedFlightBookingHistory;
    private com.payz24.responseModels.busBookingHistory.Result selectedBusBookingResult;
    private com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption selectedToOriginDestinationOption;
    private com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption selectedFromOriginDestinationOption;


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseApp.initializeApp(this);
        Mint.initAndStartSession(this, "74c233e9");
        HttpsTrustManager.allowAllSSL();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public LinkedHashMap<String, String> getMapOfContactNameAndNumber() {
        return mapOfContactNameAndNumber;
    }

    public void setMapOfContactNameAndNumber(LinkedHashMap<String, String> mapOfContactNameAndNumber) {
        this.mapOfContactNameAndNumber = mapOfContactNameAndNumber;
    }

    public Context getMobileRechargeContext() {
        return mobileRechargeContext;
    }

    public void setMobileRechargeContext(Context mobileRechargeContext) {
        this.mobileRechargeContext = mobileRechargeContext;
    }

    public List<StationList> getListOfBusStations() {
        return listOfBusStations;
    }

    public void setListOfBusStations(List<StationList> listOfBusStations) {
        this.listOfBusStations = listOfBusStations;
    }

    public Context getBusActivityContext() {
        return busActivityContext;
    }

    public void setBusActivityContext(Context busActivityContext) {
        this.busActivityContext = busActivityContext;
    }

    public List<ApiAvailableBus> getListOfAvailableBuses() {
        return listOfAvailableBuses;
    }

    public void setListOfAvailableBuses(List<ApiAvailableBus> listOfAvailableBuses) {
        this.listOfAvailableBuses = listOfAvailableBuses;
    }

    public ApiAvailableBus getSelectedAvailableBus() {
        return selectedAvailableBus;
    }

    public void setSelectedAvailableBus(ApiAvailableBus selectedAvailableBus) {
        this.selectedAvailableBus = selectedAvailableBus;
    }

    public BoardingPoint getSelectedBoardingPoint() {
        return selectedBoardingPoint;
    }

    public void setSelectedBoardingPoint(BoardingPoint selectedBoardingPoint) {
        this.selectedBoardingPoint = selectedBoardingPoint;
    }

    public List<Seat> getListOfSelectedSeat() {
        return listOfSelectedSeat;
    }

    public void setListOfSelectedSeat(List<Seat> listOfSelectedSeat) {
        this.listOfSelectedSeat = listOfSelectedSeat;
    }

    public BlockTicket getBlockTicket() {
        return blockTicket;
    }

    public void setBlockTicket(BlockTicket blockTicket) {
        this.blockTicket = blockTicket;
    }

    public Context getFlightsActivityContext() {
        return flightsActivityContext;
    }

    public void setFlightsActivityContext(Context flightsActivityContext) {
        this.flightsActivityContext = flightsActivityContext;
    }

    public List<Station> getListOfFlightStations() {
        return listOfFlightStations;
    }

    public void setListOfFlightStations(List<Station> listOfFlightStations) {
        this.listOfFlightStations = listOfFlightStations;
    }

    public OriginDestinationOption getSelectedOriginDestinationOption() {
        return selectedOriginDestinationOption;
    }

    public void setSelectedOriginDestinationOption(OriginDestinationOption selectedOriginDestinationOption) {
        this.selectedOriginDestinationOption = selectedOriginDestinationOption;
    }

    public ResponseDepart getResponseDepart() {
        return responseDepart;
    }

    public void setResponseDepart(ResponseDepart responseDepart) {
        this.responseDepart = responseDepart;
    }

    public ResponseReturn getResponseReturn() {
        return responseReturn;
    }

    public void setResponseReturn(ResponseReturn responseReturn) {
        this.responseReturn = responseReturn;
    }

    public Context getDomesticFlightsSearchRoundTripActivityContext() {
        return domesticFlightsSearchRoundTripActivityContext;
    }

    public void setDomesticFlightsSearchRoundTripActivityContext(Context domesticFlightsSearchRoundTripActivityContext) {
        this.domesticFlightsSearchRoundTripActivityContext = domesticFlightsSearchRoundTripActivityContext;
    }

    public com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption getSelectedToOriginDestinationOption() {
        return selectedToOriginDestinationOption;
    }

    public void setSelectedToOriginDestinationOption(com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption selectedToOriginDestinationOption) {
        this.selectedToOriginDestinationOption = selectedToOriginDestinationOption;
    }

    public com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption getSelectedFromOriginDestinationOption() {
        return selectedFromOriginDestinationOption;
    }

    public void setSelectedFromOriginDestinationOption(com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption selectedFromOriginDestinationOption) {
        this.selectedFromOriginDestinationOption = selectedFromOriginDestinationOption;
    }

    public LinkedList<String> getListOfSelectedOperatorsNames() {
        return listOfSelectedOperatorsNames;
    }

    public void setListOfSelectedOperatorsNames(LinkedList<String> listOfSelectedOperatorsNames) {
        this.listOfSelectedOperatorsNames = listOfSelectedOperatorsNames;
    }

    public Context getAvailableBusesContext() {
        return availableBusesContext;
    }

    public void setAvailableBusesContext(Context availableBusesContext) {
        this.availableBusesContext = availableBusesContext;
    }

    public Context getFlightTravellerDetailsContext() {
        return flightTravellerDetailsContext;
    }

    public void setFlightTravellerDetailsContext(Context flightTravellerDetailsContext) {
        this.flightTravellerDetailsContext = flightTravellerDetailsContext;
    }

    public AvailRequest getAvailRequest() {
        return availRequest;
    }

    public void setAvailRequest(AvailRequest availRequest) {
        this.availRequest = availRequest;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public LinkedList<String> getListOfAirwayNames() {
        return listOfAirwayNames;
    }

    public void setListOfAirwayNames(LinkedList<String> listOfAirwayNames) {
        this.listOfAirwayNames = listOfAirwayNames;
    }

    public LinkedList<String> getListOfAirwayImages() {
        return listOfAirwayImages;
    }

    public void setListOfAirwayImages(LinkedList<String> listOfAirwayImages) {
        this.listOfAirwayImages = listOfAirwayImages;
    }

    public LinkedList<String> getListOfAirwayFares() {
        return listOfAirwayFares;
    }

    public void setListOfAirwayFares(LinkedList<String> listOfAirwayFares) {
        this.listOfAirwayFares = listOfAirwayFares;
    }

    public Context getFlightSearchResultsActivityContext() {
        return flightSearchResultsActivityContext;
    }

    public void setFlightSearchResultsActivityContext(Context flightSearchResultsActivityContext) {
        this.flightSearchResultsActivityContext = flightSearchResultsActivityContext;
    }

    public Context getInternationalFlightsSearchRoundTripActivityContext() {
        return internationalFlightsSearchRoundTripActivityContext;
    }

    public void setInternationalFlightsSearchRoundTripActivityContext(Context internationalFlightsSearchRoundTripActivityContext) {
        this.internationalFlightsSearchRoundTripActivityContext = internationalFlightsSearchRoundTripActivityContext;
    }

    public DomesticFlightsSearchRoundTripDepartFragment getDomesticFlightsSearchRoundTripDepartFragmentContext() {
        return domesticFlightsSearchRoundTripDepartFragmentContext;
    }

    public void setDomesticFlightsSearchRoundTripDepartFragmentContext(DomesticFlightsSearchRoundTripDepartFragment domesticFlightsSearchRoundTripDepartFragmentContext) {
        this.domesticFlightsSearchRoundTripDepartFragmentContext = domesticFlightsSearchRoundTripDepartFragmentContext;
    }

    public DomesticFlightsSearchRoundTripReturnFragment getDomesticFlightsSearchRoundTripReturnFragmentContext() {
        return domesticFlightsSearchRoundTripReturnFragmentContext;
    }

    public void setDomesticFlightsSearchRoundTripReturnFragmentContext(DomesticFlightsSearchRoundTripReturnFragment domesticFlightsSearchRoundTripReturnFragmentContext) {
        this.domesticFlightsSearchRoundTripReturnFragmentContext = domesticFlightsSearchRoundTripReturnFragmentContext;
    }

    public String getSourceShortCode() {
        return sourceShortCode;
    }

    public void setSourceShortCode(String sourceShortCode) {
        this.sourceShortCode = sourceShortCode;
    }

    public String getDestinationShortCode() {
        return destinationShortCode;
    }

    public void setDestinationShortCode(String destinationShortCode) {
        this.destinationShortCode = destinationShortCode;
    }

    public String getDepartSelectedDate() {
        return departSelectedDate;
    }

    public void setDepartSelectedDate(String departSelectedDate) {
        this.departSelectedDate = departSelectedDate;
    }

    public String getReturnSelectedDate() {
        return returnSelectedDate;
    }

    public void setReturnSelectedDate(String returnSelectedDate) {
        this.returnSelectedDate = returnSelectedDate;
    }

    public LinkedHashMap<Integer, AdultDetailsRequest> getMapOfPositionAdultDetailsRequest() {
        return mapOfPositionAdultDetailsRequest;
    }

    public LinkedHashMap<Integer, InfantDetailsRequest> getMapOfPositionInfantDetailsRequest() {
        return mapOfPositionInfantDetailsRequest;
    }
    public LinkedHashMap<Integer, ChildDetailsRequest> getMapOfPositionChildDetailsRequest() {
        return mapOfPositionChildDetailsRequest;
    }

    public void setMapOfPositionAdultDetailsRequest(LinkedHashMap<Integer, AdultDetailsRequest> mapOfPositionAdultDetailsRequest) {
        this.mapOfPositionAdultDetailsRequest = mapOfPositionAdultDetailsRequest;
    }

    public void setMapOfPositionInfantDetailsRequest(LinkedHashMap<Integer, InfantDetailsRequest> mapOfPositionAdultDetailsRequest) {
        this.mapOfPositionInfantDetailsRequest = mapOfPositionAdultDetailsRequest;
    }

    public void setMapOfPositionChildDetailsRequest(LinkedHashMap<Integer, ChildDetailsRequest> mapOfPositionAdultDetailsRequest) {
        this.mapOfPositionChildDetailsRequest = mapOfPositionAdultDetailsRequest;
    }

    public String getSourceCountry() {
        return sourceCountry;
    }

    public void setSourceCountry(String sourceCountry) {
        this.sourceCountry = sourceCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public com.payz24.responseModels.flightBookingHistory.Result getSelectedFlightBookingHistory() {
        return selectedFlightBookingHistory;
    }

    public void setSelectedFlightBookingHistory(com.payz24.responseModels.flightBookingHistory.Result selectedFlightBookingHistory) {
        this.selectedFlightBookingHistory = selectedFlightBookingHistory;
    }

    public com.payz24.responseModels.busBookingHistory.Result getSelectedBusBookingResult() {
        return selectedBusBookingResult;
    }

    public void setSelectedBusBookingResult(com.payz24.responseModels.busBookingHistory.Result selectedBusBookingResult) {
        this.selectedBusBookingResult = selectedBusBookingResult;
    }
}
