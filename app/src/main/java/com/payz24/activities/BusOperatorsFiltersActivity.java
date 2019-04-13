package com.payz24.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.adapter.BusOperatorsFiltersAdapter;
import com.payz24.application.AppController;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 2/18/2018.
 */

public class BusOperatorsFiltersActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private RecyclerView rvOperators;
    private TextView tvError,tvDone;
    private ImageView ivBack;
    private EditText etSearchOperators;
    private ImageView ivClear;
    private LinkedList<String> listOfSelectedOperatorsNames;
    private List<ApiAvailableBus> listOfAvailableBuses;
    private BusOperatorsFiltersAdapter busOperatorsFiltersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_operators_filters);
        getDataFromIntent();
        initializeUi();
        initializeListeners();
        initializeAdapter();
    }

    private void getDataFromIntent() {
        listOfAvailableBuses = AppController.getInstance().getListOfAvailableBuses();
    }

    private void initializeUi() {
        rvOperators = findViewById(R.id.rvOperators);
        tvError = findViewById(R.id.tvError);
        ivBack = findViewById(R.id.ivBack);
        etSearchOperators = findViewById(R.id.etSearchOperators);
        ivClear = findViewById(R.id.ivClear);
        tvDone = findViewById(R.id.tvDone);
    }

    private void initializeListeners() {
        ivBack.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        etSearchOperators.addTextChangedListener(this);
    }

    private void initializeAdapter() {
        busOperatorsFiltersAdapter = new BusOperatorsFiltersAdapter(this, listOfAvailableBuses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvOperators.setLayoutManager(layoutManager);
        rvOperators.setItemAnimator(new DefaultItemAnimator());
        rvOperators.setAdapter(busOperatorsFiltersAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivClear:
                etSearchOperators.setText("");
                break;
            case R.id.tvDone:
                listOfSelectedOperatorsNames = AppController.getInstance().getListOfSelectedOperatorsNames();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        if (busOperatorsFiltersAdapter != null) {
            busOperatorsFiltersAdapter.filter(sequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
