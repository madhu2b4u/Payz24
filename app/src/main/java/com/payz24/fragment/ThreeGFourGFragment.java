package com.payz24.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.adapter.ViewPlansAdapter;
import com.payz24.application.AppController;
import com.payz24.http.ViewPlansHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.interfaces.RechargeAmountCallBack;
import com.payz24.responseModels.viewPlans.ViewPlans;
import com.payz24.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by mahesh on 1/18/2018.
 */

public class ThreeGFourGFragment extends BaseFragment implements HttpReqResCallBack, ViewPlansAdapter.ItemClickListener {

    private RecyclerView rvViewPlans;
    private TextView tvError;
    private String circleName = "";
    private String serviceOperatorName = "";
    private LinkedList<ViewPlans> listOfViewPlans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_full_talk_time_fragment, container, false);
        getDataFromIntent();
        initializeUi(view);
        showProgressBar();
        serviceCallForViewPlans();
        return view;
    }

    private void getDataFromIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            circleName = bundle.getString(getActivity().getString(R.string.circle_name));
            if (circleName.equalsIgnoreCase(getString(R.string.andhra_pradesh_telangana)))
                circleName = "Andhra Pradesh";
            serviceOperatorName = bundle.getString(getActivity().getString(R.string.service_provider_name));
            if (serviceOperatorName != null) {
                if (serviceOperatorName.equalsIgnoreCase("Reliance GSM/CDMA")) {
                    serviceOperatorName = "RELIANCE GSM";
                } else if (serviceOperatorName.equalsIgnoreCase("TATA Docomo Flexi") || serviceOperatorName.equalsIgnoreCase("TATA Docomo Special")) {
                    serviceOperatorName = "TATA DOCOMO GSM";
                } else if (serviceOperatorName.equalsIgnoreCase("BSNL TopUp") || serviceOperatorName.equalsIgnoreCase("BSNL(Validity/Special)")) {
                    serviceOperatorName = "BSNL";
                } else if (serviceOperatorName.equalsIgnoreCase("MTNL TopUp") || serviceOperatorName.equalsIgnoreCase("MTNL(Validity/Special)")) {
                    serviceOperatorName = "MTNL MUMBAI";
                } else {
                    serviceOperatorName = serviceOperatorName.toUpperCase();
                }
            }
        }
    }

    private void initializeUi(View view) {
        rvViewPlans = view.findViewById(R.id.rvViewPlans);
        tvError = view.findViewById(R.id.tvError);
    }

    private void serviceCallForViewPlans() {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.VIEW_PLANS_PARAM_CIRCLE_ID, circleName);
        if(serviceOperatorName.equalsIgnoreCase("MTNL MUMBAI")) {
            if (circleName.equalsIgnoreCase(getString(R.string.mumbai))) {
                serviceOperatorName = "MTNL MUMBAI";
            } else if (circleName.equalsIgnoreCase(getString(R.string.delhi_ncr))) {
                serviceOperatorName = "MTNL DELHI";
            }
        }
        mapOfRequestParams.put(Constants.VIEW_PLANS_PARAM_OPERATOR_ID, serviceOperatorName);
        mapOfRequestParams.put(Constants.VIEW_PLANS_PARAM_TYPE, getString(R.string.three_g_four_g_short_code));

        ViewPlansHttpClient viewPlansHttpClient = new ViewPlansHttpClient(getActivity());
        viewPlansHttpClient.callBack = this;
        viewPlansHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_VIEW_PLANS:
                if (jsonResponse != null && !jsonResponse.equalsIgnoreCase("operator code missing")) {
                    if (!jsonResponse.equalsIgnoreCase(getString(R.string.sorry_no_plans_available_for_this_category))) {
                        if (statusCode == Constants.STATUS_CODE_SUCCESS) {
                            try {
                                listOfViewPlans = new LinkedList<>();
                                JSONArray jsonArray = new JSONArray(jsonResponse);
                                for (int index = 0; index < jsonArray.length(); index++) {
                                    String response = jsonArray.getString(index);
                                    ViewPlans viewPlans = new Gson().fromJson(response, ViewPlans.class);
                                    if (viewPlans != null) {
                                        listOfViewPlans.add(viewPlans);
                                    }
                                }
                                if (listOfViewPlans != null && listOfViewPlans.size() != 0) {
                                    initializeAdapter();
                                }
                            } catch (JSONException exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            rvViewPlans.setVisibility(View.GONE);
                            tvError.setVisibility(View.VISIBLE);
                        }
                    } else {
                        rvViewPlans.setVisibility(View.GONE);
                        tvError.setVisibility(View.VISIBLE);
                    }
                } else {
                    rvViewPlans.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                }
                closeProgressbar();
                break;
            default:
                break;
        }
    }

    private void initializeAdapter() {
        ViewPlansAdapter viewPlansAdapter = new ViewPlansAdapter(getActivity(), listOfViewPlans, getString(R.string.three_g_four_g_short_code));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvViewPlans.setLayoutManager(layoutManager);
        rvViewPlans.setItemAnimator(new DefaultItemAnimator());
        viewPlansAdapter.setClickListener(this);
        rvViewPlans.setAdapter(viewPlansAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        ViewPlans viewPlans = listOfViewPlans.get(position);
        String rechargeAmount = viewPlans.getAmount();
        RechargeAmountCallBack rechargeAmountCallBack = (RechargeAmountCallBack) AppController.getInstance().getMobileRechargeContext();
        rechargeAmountCallBack.rechargeAmount(rechargeAmount);
        getActivity().finish();
    }
}