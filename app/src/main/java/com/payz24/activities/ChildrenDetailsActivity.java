package com.payz24.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.interfaces.AdultPersonalDetailsCallBack;
import com.payz24.interfaces.ChildrenPersonalDetailsCallBack;
import com.payz24.responseModels.AdultDetailsRequest.ChildDetailsRequest;
import com.payz24.responseModels.AdultDetailsRequest.InfantDetailsRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mahesh on 2/20/2018.
 */

public class ChildrenDetailsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private RadioGroup radioGroup;
    private RadioButton rbMiss, rbMaster;
    private TextInputLayout inputLayoutFullName, inputLayoutMiddleName, inputLayoutLastName, inputLayoutDateOfBirth, inputLayoutPassportNumber;
    private EditText etFullName, etMiddleName, etLastName, etDateOfBirth, etPassportNumber, etExpiryDate;
    private Spinner visaTypeSpinner;
    private Button btnAddTraveller;
    private int position;
    private boolean isDateOfBirthSelected;
    private Pattern pattern;
    private Matcher matcher;
    private boolean containsSpecialCharacter;


    private LinkedHashMap<Integer, ChildDetailsRequest> mapOfPositionAdultDetailsRequest;
    private String selectedRadioButtonText = "";
    private String name = "";
    private String middleName = "";
    private String lastName = "";
    private String dateOfBirth = "";
    private String passport = "";
    private String expiryDate = "";

    int s=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_child_details);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        getDataFromSelectedPosition();
    }

    private void getDataFromSelectedPosition() {
        if (mapOfPositionAdultDetailsRequest != null && mapOfPositionAdultDetailsRequest.size() != 0) {
            if (mapOfPositionAdultDetailsRequest.containsKey(position)) {
                ChildDetailsRequest adultDetailsRequest = mapOfPositionAdultDetailsRequest.get(position);
                selectedRadioButtonText = adultDetailsRequest.selectedRadioButtonText;
                name = adultDetailsRequest.name;
                middleName = adultDetailsRequest.middleName;
                lastName = adultDetailsRequest.lastName;
                dateOfBirth = adultDetailsRequest.dateOfBirth;
                passport = adultDetailsRequest.passport;
                expiryDate = adultDetailsRequest.expiryDate;

                etFullName.setText(name);
                etMiddleName.setText(middleName);
                etLastName.setText(lastName);
                etDateOfBirth.setText(dateOfBirth);
                etPassportNumber.setText(passport);
                etExpiryDate.setText(expiryDate);
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                try {
                    Date dates = format.parse(dateOfBirth);
                    s =yearsSince(dates);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (selectedRadioButtonText.equalsIgnoreCase("Mstr.")) {
                    rbMaster.setChecked(true);
                } else if (selectedRadioButtonText.equalsIgnoreCase("Miss.")) {
                    rbMiss.setChecked(true);
                }
            }
        }
    }



    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra(getString(R.string.position), 0);
        }
        mapOfPositionAdultDetailsRequest = AppController.getInstance().getMapOfPositionChildDetailsRequest();
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.add_traveller));
        setSupportActionBar(toolbar);

        radioGroup = findViewById(R.id.radioGroup);
        rbMiss = findViewById(R.id.rbMiss);
        rbMaster = findViewById(R.id.rbMaster);

        inputLayoutFullName = findViewById(R.id.inputLayoutFullName);
        inputLayoutMiddleName = findViewById(R.id.inputLayoutMiddleName);
        inputLayoutLastName = findViewById(R.id.inputLayoutLastName);
        inputLayoutDateOfBirth = findViewById(R.id.inputLayoutDateOfBirth);
        inputLayoutPassportNumber = findViewById(R.id.inputLayoutPassportNumber);

        etFullName = findViewById(R.id.etFullName);
        etMiddleName = findViewById(R.id.etMiddleName);
        etLastName = findViewById(R.id.etLastName);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etPassportNumber = findViewById(R.id.etPassportNumber);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        visaTypeSpinner = findViewById(R.id.visaTypeSpinner);
        btnAddTraveller = findViewById(R.id.btnAddTraveller);

        if (mapOfPositionAdultDetailsRequest == null) {
            mapOfPositionAdultDetailsRequest = new LinkedHashMap<>();
        } else if (mapOfPositionAdultDetailsRequest.size() == 0) {
            mapOfPositionAdultDetailsRequest = new LinkedHashMap<>();
        }


    }

    private void initializeListeners() {
        radioGroup.setOnCheckedChangeListener(this);
        btnAddTraveller.setOnClickListener(this);
        etDateOfBirth.setOnClickListener(this);
        etExpiryDate.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        selectedRadioButtonText = radioButton.getText().toString();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnAddTraveller:
                String name = etFullName.getText().toString();
                String middleName = etMiddleName.getText().toString();
               String  lastName = etLastName.getText().toString();
               String dateOfBirth = etDateOfBirth.getText().toString();
              String  passport = etPassportNumber.getText().toString();
                String expiryDate = etExpiryDate.getText().toString();
                if (!name.isEmpty()) {
                    pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(name);
                    containsSpecialCharacter = matcher.find();
                    if (!containsSpecialCharacter) {
                        inputLayoutFullName.setErrorEnabled(false);
                        inputLayoutFullName.setError("");
                        if (!lastName.isEmpty()) {
                            pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                            matcher = pattern.matcher(lastName);
                            containsSpecialCharacter = matcher.find();
                            if (!containsSpecialCharacter) {
                                inputLayoutLastName.setErrorEnabled(false);
                                inputLayoutLastName.setError("");

                                    //if (!passport.isEmpty()) {
                                    //  inputLayoutPassportNumber.setError("");
                                    // inputLayoutPassportNumber.setErrorEnabled(false);
                                    //if (!expiryDate.isEmpty()) {
                                    // if (!selectedRadioButtonText.isEmpty()) {

                                   if (!dateOfBirth.isEmpty())
                                   {
                                       if ((s<=12)&&(s>=2))
                                       {
                                           sendChildData(selectedRadioButtonText, name, middleName, lastName, dateOfBirth, passport, expiryDate, visaTypeSpinner.getSelectedItem().toString());

                                       } else
                                       {
                                           inputLayoutDateOfBirth.setErrorEnabled(true);
                                           inputLayoutDateOfBirth.setError(getString(R.string.chold));
                                       }

                                   }else
                                   {
                                       inputLayoutDateOfBirth.setErrorEnabled(true);
                                       inputLayoutDateOfBirth.setError(getString(R.string.pdob));
                                   }



                                    // }
                                    //} else {
                                    //     Toast.makeText(this, getString(R.string.please_enter_passport_expiry_date), Toast.LENGTH_SHORT).show();
                                    //  }
                                    //} else {
                                    //   inputLayoutPassportNumber.setErrorEnabled(true);
                                    ////    inputLayoutPassportNumber.setError(getString(R.string.please_enter_passport_number));
                                    // }

                            }else {
                                inputLayoutLastName.setErrorEnabled(true);
                                inputLayoutLastName.setError(getString(R.string.please_enter_name_without_any_special_characters));
                            }
                        } else {
                            inputLayoutLastName.setErrorEnabled(true);
                            inputLayoutLastName.setError(getString(R.string.please_enter_last_name));
                        }
                    } else {
                        inputLayoutFullName.setErrorEnabled(true);
                        inputLayoutFullName.setError(getString(R.string.please_enter_name_without_any_special_characters));
                    }
                } else {
                    inputLayoutFullName.setErrorEnabled(true);
                    inputLayoutFullName.setError(getString(R.string.please_enter_first_name));
                }
                break;
            case R.id.etDateOfBirth:
                isDateOfBirthSelected = true;
                showDatePicker();
                break;
            case R.id.etExpiryDate:
                isDateOfBirthSelected = false;
                showPassportPicker();
                break;
            default:
                break;
        }
    }


    private void showPassportPicker() {
        Calendar calender = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DateAndTimePicker, this, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        if (!datePickerDialog.isShowing())
            datePickerDialog.show();
        else
            datePickerDialog.dismiss();



        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void showDatePicker() {
        Calendar calender = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DateAndTimePicker, this, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        if (!datePickerDialog.isShowing())
            datePickerDialog.show();
        else
            datePickerDialog.dismiss();

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void sendChildData(String selectedRadioButtonText, String name, String middleName, String lastName, String dateOfBirth, String passport, String expiryDate, String visaType) {

        ChildDetailsRequest adultDetailsRequest = new ChildDetailsRequest();
        adultDetailsRequest.selectedRadioButtonText = selectedRadioButtonText;
        adultDetailsRequest.name = name;
        adultDetailsRequest.middleName = middleName;
        adultDetailsRequest.lastName = lastName;
        adultDetailsRequest.dateOfBirth = dateOfBirth;
        adultDetailsRequest.passport = passport;
        adultDetailsRequest.expiryDate = expiryDate;
        adultDetailsRequest.visaType = visaType;
        if (selectedRadioButtonText.equalsIgnoreCase("Master")) {
            selectedRadioButtonText = "Mstr.";
        }
        mapOfPositionAdultDetailsRequest.put(position, adultDetailsRequest);
        AppController.getInstance().setMapOfPositionChildDetailsRequest(mapOfPositionAdultDetailsRequest);
        ChildrenPersonalDetailsCallBack childrenPersonalDetailsCallBack = (ChildrenPersonalDetailsCallBack) AppController.getInstance().getFlightTravellerDetailsContext();
        childrenPersonalDetailsCallBack.childrenPersonalDetails(position, selectedRadioButtonText, name, middleName, lastName, dateOfBirth, passport, expiryDate, visaType);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String monthString = String.valueOf(month + 1);
        if (monthString.length() == 1)
            monthString = "0" + "" + monthString;
        String date = String.format("%s-%s-%s", String.valueOf(dayOfMonth), monthString, String.valueOf(year));
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            Date dates = format.parse(date);
            s =yearsSince(dates);
            if (isDateOfBirthSelected) {
                etDateOfBirth.setText(date);
                Log.e("S",""+s);
            } else {
                etExpiryDate.setText(date);

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static int yearsSince(Date pastDate) {
        Calendar present = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.setTime(pastDate);

        int years = 0;

        while (past.before(present)) {
            past.add(Calendar.YEAR, 1);
            if (past.before(present)) {
                years++;
            }
        } return years;
    }
}
