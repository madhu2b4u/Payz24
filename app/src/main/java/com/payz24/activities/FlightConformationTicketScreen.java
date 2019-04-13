package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.utils.PreferenceConnector;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mahesh on 3/3/2018.
 */

public class FlightConformationTicketScreen extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCurrentDate, tvPNR, tvAirWayName, tvSourceShortName, tvSourceFullName, tvDestinationShortName, tvDestinationFullName, tvDepartureDate, tvArrivalDate, tvPassengerName, tvPaidValue;
    private TextView tvBookAgain;
    private String sourceFullName = "";
    private String destinationFullName = "";
    private String from = "";
    private double totalAdultBaseFare = 0;
    private double totalAdultTax = 0;
    private double totalChildrenBaseFare = 0;
    private double totalChildrenTax = 0;
    private double totalInfantBaseFare = 0;
    private double totalInfantTax = 0;
    private String sourceCountryName = "";
    private String destinationCountryName = "";
    private int numberOfAdults = 0;
    private int numberOfChildren = 0;
    private int numberOfInfants = 0;
    private String toAirWayName = "";
    private String toOperatingAirwayCode = "";
    private String fromAirWayName = "";
    private String fromAirwayCode = "";
    private String airwayName = "";
    private String operatingAirwayCode = "";
    private String transid = "";
    private String partnerreferenceID = "";
    private String formattedDate = "";
    TextView home;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flight_conformation_screen);
        getDataFromIntent();
        getCurrentDate();
        initializeUi();
        initializeListeners();
        setText();
    }

    private void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = simpleDateFormat.format(calendar.getTime());
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sourceFullName = bundle.getString(getString(R.string.source));
            destinationFullName = bundle.getString(getString(R.string.destination));
            totalAdultBaseFare = bundle.getDouble(getString(R.string.total_adult_base_fare));
            totalAdultTax = bundle.getDouble(getString(R.string.total_adult_tax));
            totalChildrenBaseFare = bundle.getDouble(getString(R.string.total_children_base_fare));
            totalChildrenTax = bundle.getDouble(getString(R.string.total_children_tax));
            totalInfantBaseFare = bundle.getDouble(getString(R.string.total_infant_base_fare));
            totalInfantTax = bundle.getDouble(getString(R.string.total_infant_tax));
            from = bundle.getString(getString(R.string.from));
            sourceCountryName = bundle.getString(getString(R.string.source_country));
            destinationCountryName = bundle.getString(getString(R.string.destination_country));
            numberOfAdults = bundle.getInt(getString(R.string.adult_count));
            numberOfChildren = bundle.getInt(getString(R.string.children_count));
            numberOfInfants = bundle.getInt(getString(R.string.infant_count));
            if (bundle.containsKey(getString(R.string.toAirwayName))) {
                toAirWayName = bundle.getString(getString(R.string.toAirwayName));
                toOperatingAirwayCode = bundle.getString(getString(R.string.toAirwayCode));
                fromAirWayName = bundle.getString(getString(R.string.fromAirwayName));
                fromAirwayCode = bundle.getString(getString(R.string.fromAirwayCode));
            } else {
                airwayName = bundle.getString(getString(R.string.airway_name));
                operatingAirwayCode = bundle.getString(getString(R.string.airway_code));
            }
            transid = bundle.getString(getString(R.string.transid));
            partnerreferenceID = bundle.getString(getString(R.string.partnerreferenceID));

        }
    }
    private void gToDashboard() {
        Intent busIntent = new Intent(this, DashboardActivity.class);
        busIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(busIntent);
        finish();
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.booking_details));
        setSupportActionBar(toolbar);

        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gToDashboard();
            }
        });
        tvPNR = findViewById(R.id.tvPNR);
        tvAirWayName = findViewById(R.id.tvAirWayName);
        tvSourceShortName = findViewById(R.id.tvSourceShortName);
        tvSourceFullName = findViewById(R.id.tvSourceFullName);
        tvDestinationShortName = findViewById(R.id.tvDestinationShortName);
        tvDestinationFullName = findViewById(R.id.tvDestinationFullName);
        tvDepartureDate = findViewById(R.id.tvDepartureDate);
        tvArrivalDate = findViewById(R.id.tvArrivalDate);
        tvPassengerName = findViewById(R.id.tvPassengerName);
        tvPaidValue = findViewById(R.id.tvPaidValue);
        tvBookAgain = findViewById(R.id.tvBookAgain);

        TextView moredetails = findViewById(R.id.moredetails);

        moredetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FlightConformationTicketScreen.this,FlightDetails.class);
                intent.putExtra("pnr",transid);
                startActivity(intent);
            }
        });
    }

    private void initializeListeners() {
        tvBookAgain.setOnClickListener(this);
    }

    private void setText() {
        tvCurrentDate.setText(getString(R.string.booked_on) + " " + formattedDate);
        tvPNR.setText("PNR" + " : " + " " + transid);
        if (toAirWayName.isEmpty()) {
            tvAirWayName.setText(airwayName + " " + operatingAirwayCode);
        } else {
            tvAirWayName.setText(toAirWayName + " , " + fromAirWayName);
        }
        tvSourceShortName.setText(AppController.getInstance().getSourceShortCode());
        tvSourceFullName.setText(sourceFullName);
        tvDestinationShortName.setText(AppController.getInstance().getDestinationShortCode());
        tvDestinationFullName.setText(destinationFullName);
        tvDepartureDate.setText(AppController.getInstance().getDepartSelectedDate());
        if (AppController.getInstance().getReturnSelectedDate() != null && !AppController.getInstance().getReturnSelectedDate().isEmpty())
            tvArrivalDate.setText(AppController.getInstance().getReturnSelectedDate());
        else
            tvArrivalDate.setText(AppController.getInstance().getDepartSelectedDate());
        double grandTotal = totalAdultBaseFare + totalAdultTax + totalChildrenBaseFare + totalChildrenTax + totalInfantBaseFare + totalInfantTax;
        tvPaidValue.setText(String.valueOf(grandTotal));
    }

    @Override
    public void onClick(View view) {
        Intent flightIntent = new Intent(this, FlightsActivity.class);
        flightIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(flightIntent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent dashBoardIntent = new Intent(this, DashboardActivity.class);
        dashBoardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.to), getString(R.string.flights));
        dashBoardIntent.putExtras(bundle);
        startActivity(dashBoardIntent);
    }
}
