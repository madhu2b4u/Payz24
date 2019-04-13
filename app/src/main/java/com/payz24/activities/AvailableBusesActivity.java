package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.adapter.AvailableBusesAdapter;
import com.payz24.application.AppController;
import com.payz24.interfaces.BusFilterCallBack;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;



public class AvailableBusesActivity extends BaseActivity implements AvailableBusesAdapter.ItemClickListener, View.OnClickListener, BusFilterCallBack {

    private RecyclerView rvAvailableBus;
    private TextView tvError;
    private ImageView ivFilter;
    private String leavingFromStationName;
    private String goingToStationName;
    private String travellingDate;
    private List<ApiAvailableBus> listOfAvailableBuses;

    private LinkedList<String> listOfSelectedOperatorsNames;
    private boolean isACSelecteds=false;
    private boolean isNonACSelecteds=false;
    private boolean isSeaterSelecteds=false;
    private boolean isSleeperSelecteds=false;
    private boolean isSixAMSelecteds=false;
    private boolean isTwelvePMSelecteds=false;
    private boolean isSixPMSelecteds=false;
    private boolean isTwelveAMSelecteds=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_available_buses);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        prepareResults();
    }

    private void getDataFromIntent() {
        listOfAvailableBuses = AppController.getInstance().getListOfAvailableBuses();
        leavingFromStationName = getIntent().getStringExtra(getString(R.string.leaving_from));
        goingToStationName = getIntent().getStringExtra(getString(R.string.going_to));
        travellingDate = getIntent().getStringExtra(getString(R.string.doj));
        AppController.getInstance().setAvailableBusesContext(this);
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        ivFilter = findViewById(R.id.ivFilter);
        toolbar.setTitle(leavingFromStationName + " to " + goingToStationName);
        setSupportActionBar(toolbar);
        rvAvailableBus = findViewById(R.id.rvAvailableBus);
        tvError = findViewById(R.id.tvError);
    }

    private void initializeListeners() {
        ivFilter.setOnClickListener(this);
    }

    private void prepareResults() {
        if (listOfAvailableBuses != null && listOfAvailableBuses.size() != 0) {
            rvAvailableBus.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            initializeAdapter(listOfAvailableBuses);
        } else {
            rvAvailableBus.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        }
    }
    private void initializeAdapters(List<ApiAvailableBus> listOfAvailableBuses) {

        if (listOfAvailableBuses != null && listOfAvailableBuses.size() != 0) {
            rvAvailableBus.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            initializeAdapter(listOfAvailableBuses);
        } else {
            rvAvailableBus.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        }
        AvailableBusesAdapter availableBusesAdapter = new AvailableBusesAdapter(this, listOfAvailableBuses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAvailableBus.setLayoutManager(layoutManager);
        rvAvailableBus.setItemAnimator(new DefaultItemAnimator());
        availableBusesAdapter.setClickListener(this);
        rvAvailableBus.setAdapter(availableBusesAdapter);
    }

    private void initializeAdapter(List<ApiAvailableBus> listOfAvailableBuses) {


        AvailableBusesAdapter availableBusesAdapter = new AvailableBusesAdapter(this, listOfAvailableBuses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAvailableBus.setLayoutManager(layoutManager);
        rvAvailableBus.setItemAnimator(new DefaultItemAnimator());
        availableBusesAdapter.setClickListener(this);
        rvAvailableBus.setAdapter(availableBusesAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        ApiAvailableBus apiAvailableBus = listOfAvailableBuses.get(position);
        Intent busLayoutIntent = new Intent(this, BusLayoutActivity.class);
        busLayoutIntent.putExtra(getString(R.string.leaving_from), leavingFromStationName);
        busLayoutIntent.putExtra(getString(R.string.going_to), goingToStationName);
        busLayoutIntent.putExtra(getString(R.string.doj), travellingDate);
        busLayoutIntent.putExtra(getString(R.string.travels_names), apiAvailableBus.getOperatorName());
        AppController.getInstance().setSelectedAvailableBus(apiAvailableBus);
        startActivity(busLayoutIntent);
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivFilter:
                Intent busFilterIntent = new Intent(this, BusFilterActivity.class);
                busFilterIntent.putExtra("isACSelecteds",isACSelecteds);
                Log.e("isACSelecteds", String.valueOf(isACSelecteds));
                busFilterIntent.putExtra("isSeaterSelecteds",isSeaterSelecteds);
                busFilterIntent.putExtra("isNonACSelecteds",isNonACSelecteds);
                busFilterIntent.putExtra("isSleeperSelecteds",isSleeperSelecteds);
                busFilterIntent.putExtra("isSixAMSelecteds",isSixAMSelecteds);
                busFilterIntent.putExtra("isTwelvePMSelecteds",isTwelvePMSelecteds);
                busFilterIntent.putExtra("isSixPMSelecteds",isSixPMSelecteds);
                busFilterIntent.putExtra("isTwelveAMSelecteds",isTwelveAMSelecteds);

                startActivity(busFilterIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void busFilter(LinkedList<String> listOfSelectedOperatorsNames, boolean isAcSelected, boolean isNonAcSelected, boolean isSeaterSelected, boolean isSleeperSelected, boolean isSixAMSelected, boolean isTwelvePMSelected, boolean isSixPMSelected, boolean isTwelveAMSelected) {
        isACSelecteds=isAcSelected;
        isNonACSelecteds=isNonAcSelected;
        isSeaterSelecteds=isSeaterSelected;
        isSleeperSelecteds=isSleeperSelected;
        isSixAMSelecteds=isSixAMSelected;
        isTwelvePMSelecteds=isTwelvePMSelected;
        isSixPMSelecteds=isSixPMSelected;
        isTwelveAMSelecteds=isTwelveAMSelected;

        Log.e("isACSelecteds", String.valueOf(isACSelecteds));

        List<ApiAvailableBus> listOfAvailableBuses = new ArrayList<>();
        prepareResults();
        if (listOfSelectedOperatorsNames != null) {
            for (int index = 0; index < this.listOfAvailableBuses.size(); index++) {
                String travelOperatorName = this.listOfAvailableBuses.get(index).getOperatorName();
                String departureTime = this.listOfAvailableBuses.get(index).getDepartureTime();
                String arrivalTime = this.listOfAvailableBuses.get(index).getArrivalTime();
                String busType = this.listOfAvailableBuses.get(index).getBusType().toLowerCase();
                if (listOfSelectedOperatorsNames.contains(travelOperatorName)) {
                    if (isSixAMSelected) {
                        if (isAcSelected) {
                            if (!busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else {
                                    if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            }
                        } else if (isNonAcSelected) {
                            if (busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else {
                                    if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            }
                        } else {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    }

                    if (isSixPMSelected) {
                        if (isAcSelected) {
                            if (!busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else {
                                    if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            }
                        } else if (isNonAcSelected) {
                            if (busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else {
                                    if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            }
                        } else {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    }

                    if (isTwelvePMSelected) {
                        if (isAcSelected) {
                            if (!busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }

                                    }
                                } else {
                                    if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            }
                        } else if (isNonAcSelected) {
                            if (busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else {
                                    if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            }
                        } else {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    }


                    if (isTwelveAMSelected) {
                        if (isAcSelected) {
                            if (!busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else {
                                    if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            }
                        } else if (isNonAcSelected) {
                            if (busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                                    }
                                } else {
                                    if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            }
                        } else {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    }

                    if (!isSixAMSelected && !isSixPMSelected && !isTwelveAMSelected && !isTwelvePMSelected) {
                        if (isAcSelected) {
                            if (!busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                } else {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        } else if (isNonAcSelected) {
                            if (busType.contains("non ac")) {
                                if (isSleeperSelected) {
                                    if (!busType.contains("semi sleeper")) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                } else if (isSeaterSelected) {
                                    if (busType.contains("semi sleeper")) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                } else {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        } else {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            } else {
                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                            }
                        }
                    }
                }
            }
        } else {
            for (int index = 0; index < this.listOfAvailableBuses.size(); index++) {
                String travelOperatorName = this.listOfAvailableBuses.get(index).getOperatorName();
                String departureTime = this.listOfAvailableBuses.get(index).getDepartureTime();
                String busType = this.listOfAvailableBuses.get(index).getBusType().toLowerCase();
                String arrivalTime = this.listOfAvailableBuses.get(index).getArrivalTime();
                ApiAvailableBus apiAvailableBuss = this.listOfAvailableBuses.get(index);

                if (isSixAMSelected) {
                    if (isAcSelected) {
                        if (!busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                            }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    } else if (isNonAcSelected) {
                        if (busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("Semi Sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                            }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("Semi Sleeper")) {
                                    if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    } else {
                        if (isSleeperSelected) {
                            if (!busType.contains("Semi Sleeper")) {
                                if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                    if (busType.contains("sleeper"))
                                        if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                            }
                        } else if (isSeaterSelected) {
                            if (busType.contains("Semi Sleeper")) {
                                if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        } else {
                            if (inBetweenTwoTimes("06:00 AM", "11:59 AM", departureTime)) {
                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                            }
                        }
                    }
                }

                if (isSixPMSelected) {
                    if (isAcSelected) {
                        if (!busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                            }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    } else if (isNonAcSelected) {
                        if (busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                            }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    } else {
                        if (isSleeperSelected) {
                            if (!busType.contains("semi sleeper")) {
                                if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                    if (busType.contains("sleeper"))
                                        if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                            }
                        } else if (isSeaterSelected) {
                            if (busType.contains("semi sleeper")) {
                                if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        } else {
                            if (inBetweenTwoTimes("06:00 PM", "11:59 PM", departureTime)) {
                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                            }
                        }
                    }
                }

                if (isTwelvePMSelected) {
                    if (isAcSelected) {
                        if (!busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                            }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    } else if (isNonAcSelected) {
                        if (busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                            }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    } else {
                        if (isSleeperSelected) {
                            if (!busType.contains("semi sleeper")) {
                                if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                    if (busType.contains("sleeper"))
                                        if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                            }
                        } else if (isSeaterSelected) {
                            if (busType.contains("semi sleeper")) {
                                if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        } else {
                            if (inBetweenTwoTimes("12:00 PM", "5:59 PM", departureTime)) {
                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                            }
                        }
                    }
                }


                if (isTwelveAMSelected) {
                    if (isAcSelected) {
                        if (!busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                            }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    } else if (isNonAcSelected) {
                        if (busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                            }
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                        listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                    }
                                }
                            } else {
                                if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        }
                    } else {
                        if (isSleeperSelected) {
                            if (!busType.contains("semi sleeper")) {
                                if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                    if (busType.contains("sleeper"))
                                        if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                        }
                            }
                        } else if (isSeaterSelected) {
                            if (busType.contains("semi sleeper")) {
                                if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            }
                        } else {
                            if (inBetweenTwoTimes("12:00 AM", "5:59 AM", departureTime)) {
                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                            }
                        }
                    }
                }

                if (!isSixAMSelected && !isSixPMSelected && !isTwelveAMSelected && !isTwelvePMSelected) {
                    if (isAcSelected) {
                        if (!busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            } else {
                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                            }
                        }
                    } else if (isNonAcSelected) {
                        if (busType.contains("non ac")) {
                            if (isSleeperSelected) {
                                if (!busType.contains("semi sleeper")) {
                                    if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                        if (busType.contains("sleeper"))
                                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            } else if (isSeaterSelected) {
                                if (busType.contains("semi sleeper")) {
                                    listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                                }
                            } else {
                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                            }
                        }
                    } else {
                        if (isSleeperSelected) {
                            if (!busType.contains("semi sleeper")) {
                                if (!busType.contains("brand new volvo multi-axled sleeper 2x2 ac"))
                                 Log.e("Sss","ssssss");

                                        if (busType.contains("sleeper"))
                                        {
                                            //Log.e("getOperatorName", ""+listOfAvailableBuses.get(index).getOperatorName());
                                            listOfAvailableBuses.add(apiAvailableBuss);
                                        }


                            }
                        } else if (isSeaterSelected) {
                            if (!busType.contains("semi sleeper")) {
                                listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                            }
                        } else {
                            listOfAvailableBuses.add(this.listOfAvailableBuses.get(index));
                        }
                    }
                }
            }
        }
        //prepareResults();
        initializeAdapters(listOfAvailableBuses);

    }

    private boolean inBetweenTwoTimes(String fromDate, String toDate, String arrivalTime) {
        boolean hasSelectedTime = false;
        try {
            Date time1 = new SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(fromDate);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = new SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(toDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            Date d = new SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(arrivalTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                hasSelectedTime = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hasSelectedTime;
    }
}
