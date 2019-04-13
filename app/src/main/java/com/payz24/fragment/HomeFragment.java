package com.payz24.fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.activities.BusActivity;
import com.payz24.activities.FlightDetails;
import com.payz24.activities.FlightsActivity;
import com.payz24.activities.recharge.DthRechargeActivity;
import com.payz24.activities.recharge.DatacardActivity;
import com.payz24.activities.recharge.Electricity;
import com.payz24.activities.recharge.Gas;
import com.payz24.activities.recharge.Landline;
import com.payz24.activities.recharge.NewMobileRechargeActivity;
import com.payz24.adapter.BookOnPay24Adapter;
import com.payz24.adapter.RechargePayBillAdapter;
import com.payz24.adapter.ShopOnPay24Adapter;
import com.payz24.http.UtilitiesServicesListHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightStations.FlightStations;
import com.payz24.responseModels.utilitiesList.UtilitiesList;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.Permissions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 3/5/2018.
 */

public class HomeFragment extends BaseFragment implements RechargePayBillAdapter.ItemClickListener, BookOnPay24Adapter.ItemClickListener, ShopOnPay24Adapter.ItemClickListener, HttpReqResCallBack {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_fragment, container, false);
        getData();
        initializeUi(view);
        showProgressBar();
        serviceCallForServiceList();
        prepareRechargePayBillResults();
        prepareBookOnPay24();
        prepareShopOnPay24();
        initializeRechargePayBillAdapter();
        initializeBookOnPay24Adapter();
        if(to.equalsIgnoreCase(getString(R.string.flights))){
            goToFlightsActivity();
        }
        //initializeShopOnPay24Adapter();
        return view;
    }

    private void getData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            to = bundle.getString(getString(R.string.to));
        }
    }

    private void initializeUi(View view) {
        rvRechargePayBills = view.findViewById(R.id.rvRechargePayBills);
        rvBookOnPay24 = view.findViewById(R.id.rvBookOnPay24);
        rvShopOnPay24 = view.findViewById(R.id.rvShopOnPay24);

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
        RechargePayBillAdapter rechargePayBillAdapter = new RechargePayBillAdapter(getActivity(), listOfRechargePayBillCollectionNames, listOfRechargePayBillCollectionImages);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvRechargePayBills.setLayoutManager(layoutManager);
        rvRechargePayBills.setItemAnimator(new DefaultItemAnimator());
        rechargePayBillAdapter.setClickListener(this);
        rvRechargePayBills.setAdapter(rechargePayBillAdapter);
    }

    private void initializeBookOnPay24Adapter() {
        BookOnPay24Adapter bookOnPay24Adapter = new BookOnPay24Adapter(getActivity(), listOfBookOnPay24CollectionNames, listOfBookOnPay24CollectionImages);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvBookOnPay24.setLayoutManager(layoutManager);
        rvBookOnPay24.setItemAnimator(new DefaultItemAnimator());
        bookOnPay24Adapter.setClickListener(this);
        rvBookOnPay24.setAdapter(bookOnPay24Adapter);
    }

    private void initializeShopOnPay24Adapter() {
        ShopOnPay24Adapter shopOnPay24Adapter = new ShopOnPay24Adapter(getActivity(), listOfShopOnPay24CollectionNames, listOfShopOnPay24CollectionImages);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvShopOnPay24.setLayoutManager(layoutManager);
        rvShopOnPay24.setItemAnimator(new DefaultItemAnimator());
        shopOnPay24Adapter.setClickListener(this);
        rvShopOnPay24.setAdapter(shopOnPay24Adapter);
    }

    private void serviceCallForServiceList() {
        UtilitiesServicesListHttpClient utilitiesServicesListHttpClient = new UtilitiesServicesListHttpClient(getActivity());
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
                goToDthRechargeActivity();
                break;
            case 2:
                goToElectrictyRechargeActivity();
                break;
            case 3:
                break;
            case 4:
                goToDatacard();
                break;
            case 5:
                goToGasRechargeActivity();
                break;
            case 6:
                break;
            case 7:
                goToLandlineRechargeActivity();
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
        if (Permissions.checkPermissionForReadContacts(getActivity())) {
            if (mapOfUtilityNameId != null && mapOfUtilityNameId.size() != 0) {
                Intent mobileRechargeIntent = new Intent(getActivity(), NewMobileRechargeActivity.class);
                startActivity(mobileRechargeIntent);
            } else {
                Toast.makeText(getActivity(), getString(R.string.status_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Permissions.requestPermissionForReadContacts(getActivity());

        }
    }


    private void goToDthRechargeActivity() {
        if (Permissions.checkPermissionForReadContacts(getActivity())) {
            if (mapOfUtilityNameId != null && mapOfUtilityNameId.size() != 0) {
                  // int id = mapOfUtilityNameId.get(getString(R.string.mobile));
                Intent mobileRechargeIntent = new Intent(getActivity(), DthRechargeActivity.class);
                 //  mobileRechargeIntent.putExtra(getString(R.string.mobile), id);
                startActivity(mobileRechargeIntent);
            } else {
                Toast.makeText(getActivity(), getString(R.string.status_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Permissions.requestPermissionForReadContacts(getActivity());
        }
    }

    private void goToGasRechargeActivity() {
        if (Permissions.checkPermissionForReadContacts(getActivity())) {
            if (mapOfUtilityNameId != null && mapOfUtilityNameId.size() != 0) {
                  // int id = mapOfUtilityNameId.get(getString(R.string.mobile));
                Intent mobileRechargeIntent = new Intent(getActivity(), Gas.class);
                 //  mobileRechargeIntent.putExtra(getString(R.string.mobile), id);
                startActivity(mobileRechargeIntent);
            } else {
                Toast.makeText(getActivity(), getString(R.string.status_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Permissions.requestPermissionForReadContacts(getActivity());
        }
    }

    private void goToElectrictyRechargeActivity() {
        if (Permissions.checkPermissionForReadContacts(getActivity())) {
            if (mapOfUtilityNameId != null && mapOfUtilityNameId.size() != 0) {
                  // int id = mapOfUtilityNameId.get(getString(R.string.mobile));
                Intent mobileRechargeIntent = new Intent(getActivity(), Electricity.class);
                 //  mobileRechargeIntent.putExtra(getString(R.string.mobile), id);
                startActivity(mobileRechargeIntent);
            } else {
                Toast.makeText(getActivity(), getString(R.string.status_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Permissions.requestPermissionForReadContacts(getActivity());
        }
    }

    private void goToLandlineRechargeActivity() {
        if (Permissions.checkPermissionForReadContacts(getActivity())) {
            if (mapOfUtilityNameId != null && mapOfUtilityNameId.size() != 0) {
                  // int id = mapOfUtilityNameId.get(getString(R.string.mobile));
                Intent mobileRechargeIntent = new Intent(getActivity(), Landline.class);
                 //  mobileRechargeIntent.putExtra(getString(R.string.mobile), id);
                startActivity(mobileRechargeIntent);
            } else {
                Toast.makeText(getActivity(), getString(R.string.status_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Permissions.requestPermissionForReadContacts(getActivity());
        }
    }

    private void goToBusActivity() {
        Intent busIntent = new Intent(getActivity(),BusActivity.class);

        busIntent.putExtra("to", "");
        busIntent.putExtra("from", "");
        busIntent.putExtra("return", "return");
        startActivity(busIntent);
    }

    private void goToFlightsActivity() {
        Intent flightsIntent = new Intent(getActivity(),FlightsActivity.class);
        startActivity(flightsIntent);
    }

    private void goToDatacard() {
        if (Permissions.checkPermissionForReadContacts(getActivity())) {
            if (mapOfUtilityNameId != null && mapOfUtilityNameId.size() != 0) {
                  // int id = mapOfUtilityNameId.get(getString(R.string.mobile));
                Intent mobileRechargeIntent = new Intent(getActivity(), DatacardActivity.class);
                 //  mobileRechargeIntent.putExtra(getString(R.string.mobile), id);
                startActivity(mobileRechargeIntent);
            } else {
                Toast.makeText(getActivity(), getString(R.string.status_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Permissions.requestPermissionForReadContacts(getActivity());
        }
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
                    Toast.makeText(getActivity(), getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            default:
                break;
        }
    }
}
