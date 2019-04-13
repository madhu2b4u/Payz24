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
import com.payz24.adapter.ContactsAdapter;
import com.payz24.application.AppController;
import com.payz24.interfaces.ContactsCallBack;

import java.util.LinkedHashMap;

/**
 * Created by mahesh on 1/15/2018.
 */

public class ContactsActivity extends BaseActivity implements View.OnClickListener, ContactsAdapter.ItemClickListener, TextWatcher, ContactsCallBack {

    private TextView tvError;
    private RecyclerView rvContacts;
    private EditText etContact;
    private ImageView ivBack;
    private LinkedHashMap<String, String> mapOfContactNameAndNumber;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contacts);
        initializeUi();
        initializeListeners();
        initializeAdapter();
    }

    private void initializeUi() {
        rvContacts = findViewById(R.id.rvContacts);
        tvError = findViewById(R.id.tvError);
        etContact = findViewById(R.id.etContact);
        ivBack = findViewById(R.id.ivBack);

        mapOfContactNameAndNumber = AppController.getInstance().getMapOfContactNameAndNumber();
    }

    private void initializeListeners() {
        ivBack.setOnClickListener(this);
        etContact.addTextChangedListener(this);
    }

    private void initializeAdapter() {
        contactsAdapter = new ContactsAdapter(this, mapOfContactNameAndNumber);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvContacts.setLayoutManager(layoutManager);
        rvContacts.setItemAnimator(new DefaultItemAnimator());
        contactsAdapter.setClickListener(this);
        rvContacts.setAdapter(contactsAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        if (contactsAdapter != null) {
            contactsAdapter.setFilter(sequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void selectedContacts(String contactName, String contactNumber) {
        ContactsCallBack contactsCallBack = (ContactsCallBack) AppController.getInstance().getMobileRechargeContext();
        contactsCallBack.selectedContacts(contactName, contactNumber);
        finish();
    }
}
