package com.payz24.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.adapter.BookOnPay24Adapter;
import com.payz24.adapter.RechargePayBillAdapter;
import com.payz24.adapter.ShopOnPay24Adapter;
import com.payz24.application.AppController;
import com.payz24.http.FlightsStationsListHttpClient;
import com.payz24.http.UtilitiesServicesListHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightStations.FlightStations;
import com.payz24.responseModels.utilitiesList.UtilitiesList;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.Permissions;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 1/8/2018.
 */

public class HomeActivity extends BaseActivity implements RechargePayBillAdapter.ItemClickListener, BookOnPay24Adapter.ItemClickListener, ShopOnPay24Adapter.ItemClickListener, HttpReqResCallBack {

    private RecyclerView rvRechargePayBills, rvBookOnPay24, rvShopOnPay24;
    private LinkedList<String> listOfRechargePayBillCollectionNames;
    private LinkedList<Integer> listOfRechargePayBillCollectionImages;
    private LinkedList<String> listOfBookOnPay24CollectionNames;
    private LinkedList<Integer> listOfBookOnPay24CollectionImages;
    private LinkedList<String> listOfShopOnPay24CollectionNames;
    private LinkedList<Integer> listOfShopOnPay24CollectionImages;
    private LinkedHashMap<String, Integer> mapOfUtilityNameId;
    private NetworkDetection networkDetection;
    private List<FlightStations> listOfFlightStations;
    private String to = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        initializeUi();
        showProgressBar();
        serviceCallForServiceList();
        prepareRechargePayBillResults();
        prepareBookOnPay24();
        prepareShopOnPay24();
        initializeRechargePayBillAdapter();
        initializeBookOnPay24Adapter();
        //initializeShopOnPay24Adapter();
    }

    private void initializeUi() {
        rvRechargePayBills = findViewById(R.id.rvRechargePayBills);
        rvBookOnPay24 = findViewById(R.id.rvBookOnPay24);
        rvShopOnPay24 = findViewById(R.id.rvShopOnPay24);

        listOfRechargePayBillCollectionNames = new LinkedList<>();
        listOfRechargePayBillCollectionImages = new LinkedList<>();
        listOfBookOnPay24CollectionNames = new LinkedList<>();
        listOfBookOnPay24CollectionImages = new LinkedList<>();
        listOfShopOnPay24CollectionNames = new LinkedList<>();
        listOfShopOnPay24CollectionImages = new LinkedList<>();
        mapOfUtilityNameId = new LinkedHashMap<>();
    }

    private void prepareRechargePayBillResults() {
        listOfRechargePayBillCollectionNames.add(getString(R.string.mobile));
        listOfRechargePayBillCollectionNames.add(getString(R.string.dth));
        listOfRechargePayBillCollectionNames.add(getString(R.string.electricity));
        listOfRechargePayBillCollectionNames.add(getString(R.string.credit_card));
        listOfRechargePayBillCollectionNames.add(getString(R.string.data_card));
        listOfRechargePayBillCollectionNames.add(getString(R.string.gas));
        listOfRechargePayBillCollectionNames.add(getString(R.string.broadband));
        listOfRechargePayBillCollectionNames.add(getString(R.string.landline));
        listOfRechargePayBillCollectionNames.add(getString(R.string.insurance));


        listOfRechargePayBillCollectionImages.add(R.drawable.ic_recharge);
        listOfRechargePayBillCollectionImages.add(R.drawable.ic_dth);
        listOfRechargePayBillCollectionImages.add(R.drawable.ic_electricity);
        listOfRechargePayBillCollectionImages.add(R.drawable.ic_creditcard);
        listOfRechargePayBillCollectionImages.add(R.drawable.ic_datacard);
        listOfRechargePayBillCollectionImages.add(R.drawable.ic_gas);
        listOfRechargePayBillCollectionImages.add(R.drawable.ic_broadband);
        listOfRechargePayBillCollectionImages.add(R.drawable.ic_landline);
        listOfRechargePayBillCollectionImages.add(R.drawable.ic_insurance);
    }

    private void prepareBookOnPay24() {

        listOfBookOnPay24CollectionNames.add(getString(R.string.flights));
        listOfBookOnPay24CollectionNames.add(getString(R.string.bus));
        listOfBookOnPay24CollectionNames.add(getString(R.string.hotels));
        listOfBookOnPay24CollectionNames.add(getString(R.string.movies));

        listOfBookOnPay24CollectionImages.add(R.drawable.ic_flights);
        listOfBookOnPay24CollectionImages.add(R.drawable.ic_bus);
        listOfBookOnPay24CollectionImages.add(R.drawable.ic_hotels);
        listOfBookOnPay24CollectionImages.add(R.drawable.ic_movies);
    }

    private void prepareShopOnPay24() {

    }

    private void initializeRechargePayBillAdapter() {
        RechargePayBillAdapter rechargePayBillAdapter = new RechargePayBillAdapter(this, listOfRechargePayBillCollectionNames, listOfRechargePayBillCollectionImages);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvRechargePayBills.setLayoutManager(layoutManager);
        rvRechargePayBills.setItemAnimator(new DefaultItemAnimator());
        rechargePayBillAdapter.setClickListener(this);
        rvRechargePayBills.setAdapter(rechargePayBillAdapter);
    }

    private void initializeBookOnPay24Adapter() {
        BookOnPay24Adapter bookOnPay24Adapter = new BookOnPay24Adapter(this, listOfBookOnPay24CollectionNames, listOfBookOnPay24CollectionImages);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvBookOnPay24.setLayoutManager(layoutManager);
        rvBookOnPay24.setItemAnimator(new DefaultItemAnimator());
        bookOnPay24Adapter.setClickListener(this);
        rvBookOnPay24.setAdapter(bookOnPay24Adapter);
    }

    private void initializeShopOnPay24Adapter() {
        ShopOnPay24Adapter shopOnPay24Adapter = new ShopOnPay24Adapter(this, listOfShopOnPay24CollectionNames, listOfShopOnPay24CollectionImages);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvShopOnPay24.setLayoutManager(layoutManager);
        rvShopOnPay24.setItemAnimator(new DefaultItemAnimator());
        shopOnPay24Adapter.setClickListener(this);
        rvShopOnPay24.setAdapter(shopOnPay24Adapter);
    }

    private void serviceCallForServiceList() {
        UtilitiesServicesListHttpClient utilitiesServicesListHttpClient = new UtilitiesServicesListHttpClient(this);
        utilitiesServicesListHttpClient.callBack = this;
        utilitiesServicesListHttpClient.getJsonForType();
    }

    @Override
    public void onRechargePayBillClickListener(View view, int position) {
        switch (position) {
            case 0:
                goToRechargeActivity();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                break;
        }
    }

    @Override
    public void onBookOnPay24ClickListener(View view, int position) {
        switch (position) {
            case 0:
                goToFlightsActivity();
                break;
            case 1:
                goToBusActivity();
                break;
            case 2:
                break;
            case 4:
                break;
            default:
                break;
        }
    }

    @Override
    public void onShopOnPay24ClickListener(View view, int position) {

    }

    private void goToRechargeActivity() {
        if (Permissions.checkPermissionForReadContacts(this)) {
            if (mapOfUtilityNameId != null && mapOfUtilityNameId.size() != 0) {
                int id = mapOfUtilityNameId.get(getString(R.string.mobile));
                Intent mobileRechargeIntent = new Intent(this, MobileRechargeActivity.class);
                mobileRechargeIntent.putExtra(getString(R.string.mobile), id);
                startActivity(mobileRechargeIntent);
            } else {
                Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Permissions.requestPermissionForReadContacts(this);
        }
    }

    private void goToBusActivity() {
        Intent busIntent = new Intent(this, BusActivity.class);
        startActivity(busIntent);
    }

    private void goToFlightsActivity() {
        Intent flightsIntent = new Intent(this, FlightsActivity.class);
        startActivity(flightsIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length != 0 && grantResults.length != 0) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_FOR_READ_CONTACTS_PERMISSION:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        goToRechargeActivity();        // Permission Granted
                    } else {
                        goToRechargeActivity();       // Permission Denied
                    }
                    break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_UTILITIES_LIST:
                if (jsonResponse != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(jsonResponse);
                        for (int index = 0; index < jsonArray.length(); index++) {
                            String response = jsonArray.getString(index);
                            UtilitiesList utilitiesList = new Gson().fromJson(response, UtilitiesList.class);
                            if (utilitiesList != null) {
                                int id = utilitiesList.getId();
                                String utilityName = utilitiesList.getServiceName();
                                mapOfUtilityNameId.put(utilityName, id);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
}
