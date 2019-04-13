package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payz24.R;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahesh on 2/13/2018.
 */

public class DomesticFlightsSearchRoundTripDepartFragmentAdapter extends RecyclerView.Adapter<DomesticFlightsSearchRoundTripDepartFragmentAdapter.ViewHolder> {

    private Context context;
    private RecyclerView rvFlightDetails;
    private List<OriginDestinationOption> listOfOriginDestinationOption;
    private String totalJourneyTime = "";
    private String[] departureDateTime;
    private String[] arrivalDateTime;
    private String airLineCode = "";
    private String airwayName = "";
    private ItemClickListener clickListener;
    private String[] hoursMinutes;
    String flightStops,flightNumber;

    public DomesticFlightsSearchRoundTripDepartFragmentAdapter(Context context, List<OriginDestinationOption> listOfOriginDestinationOption, RecyclerView rvFlightDetails) {
        this.context = context;
        this.rvFlightDetails = rvFlightDetails;
        this.listOfOriginDestinationOption = listOfOriginDestinationOption;
    }

    public void update(List<OriginDestinationOption> listOfDomesticOriginDestinationOptions) {
        this.listOfOriginDestinationOption = listOfDomesticOriginDestinationOptions;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_flights_search_results_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<String> listOfViaAirportNames = new ArrayList<>();
        OriginDestinationOption originDestinationOption = listOfOriginDestinationOption.get(position);
        String fare = originDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
        String tax = originDestinationOption.getFareDetails().getChargeableFares().getTax();
        int totalFare = Integer.parseInt(fare) + Integer.parseInt(tax);
        JsonObject jsonObject = originDestinationOption.getFlightSegments().getAsJsonObject();
        String busMType = PreferenceConnector.readString(context, context.getString(R.string.flight_m_type), "");


        try {
            JSONObject flightSegmentJsonObject = new JSONObject(jsonObject.toString());
            Object json = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
            if (json instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), FlightSegment.class);
                totalJourneyTime = "0";
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDateTime = flightSegment.getArrivalDateTime().split("T");
                airLineCode = flightSegment.getOperatingAirlineCode();
                airwayName = flightSegment.getAirLineName();
                flightNumber=flightSegment.getFlightNumber();
                hoursMinutes = hoursMinutesBetweenTwoTimes(flightSegment.getDepartureDateTime(), flightSegment.getArrivalDateTime()).split(":");
            } else if (json instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                String response = jsonArray.getString(0);
                FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                totalJourneyTime = "0";
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDateTime = new Gson().fromJson(jsonArray.getString(jsonArray.length() - 1), FlightSegment.class).getArrivalDateTime().split("T");
                airLineCode = flightSegment.getOperatingAirlineCode();
                flightNumber=flightSegment.getFlightNumber();
                airwayName = flightSegment.getAirLineName();
                flightStops=flightSegment.getStopQuantity();
                hoursMinutes = hoursMinutesBetweenTwoTimes(flightSegment.getDepartureDateTime(), new Gson().fromJson(jsonArray.getString(jsonArray.length() - 1), FlightSegment.class).getArrivalDateTime()).split(":");
                for (int index = 0; index < jsonArray.length(); index++) {
                    String via = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class).getArrivalAirportCode();
                    listOfViaAirportNames.add(via);

                }
            }

            String departureTime = departureDateTime[1];
            String arrivalTime = arrivalDateTime[1];
            String via = TextUtils.join(",", listOfViaAirportNames);
            int s=listOfViaAirportNames.size()-1;
            int a=(via.split(",").length)-1;

            String details = "";
            if (!via.isEmpty()) {
                details = String.valueOf(hoursMinutes[0]) + "h" + " " + String.valueOf(hoursMinutes[1]) + "m" + " " + "|" + " " + "Via" + " " + via;
            } else {
                details = String.valueOf(hoursMinutes[0]) + "h" + " " + String.valueOf(hoursMinutes[1]) + "m";
            }
            //for (int removeIndex = 0; removeIndex < 3; removeIndex++) {
            departureTime = departureTime.substring(0, departureTime.length() - 3);
            arrivalTime = arrivalTime.substring(0, arrivalTime.length() - 3);

            //}


            holder.tvAirwayName.setText(airwayName+"\n"+airLineCode+" "+flightNumber);
            holder.tvDepartureTime.setText(departureTime);
            holder.tvArrivalTime.setText(arrivalTime);
            holder.tvViaDetails.setText(details);
            if (a==1)
            {
                holder.stops.setText(a +" Stop");

            }else if (a==0)
            {
                holder.stops.setText(a+" Stops");

            }else if (a==2)
            {
                holder.stops.setText(a+" Stops");
            } else  if (a==3)
            {
                holder.stops.setText(a+" Stops");
            }

            int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.flight_mark_up), ""));
            int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.flight_conventional_fee), ""));
            Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
            Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
            int markUpFee = (int) (Integer.parseInt(fare) * markUpPercentage);
            int totalFareInt=0;

            if (busMType.equalsIgnoreCase("M"))
                totalFareInt = (int) (totalFare) - markUpFee;
            else
                totalFareInt = (int) (totalFare) + markUpFee;

            Log.e("totalFare",""+totalFare);
            Log.e("markUpFee",""+markUpFee);
            Log.e("fareMarkUp",""+fareMarkUp);


            holder.tvFare.setText(context.getString(R.string.Rs) + " " + String.valueOf(totalFareInt));
            String imageUrl = Constants.IMAGE_URL_BASE + "" + airLineCode + ".gif";
            Picasso.with(context).load(imageUrl).error(R.mipmap.ic_launcher_round).into(holder.ivAirLineImage);
            //rvFlightDetails.findViewHolderForAdapterPosition(0).itemView.performClick();
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

    }

    private String hoursMinutesBetweenTwoTimes(String departureDateTime, String arrivalDateTime) {
        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date d1 = null;
        Date d2 = null;
        String result = "";
        try {
            d1 = format.parse(departureDateTime);
            d2 = format.parse(arrivalDateTime);
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            result = String.valueOf(diffHours) + ":" + String.valueOf(diffMinutes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return listOfOriginDestinationOption.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivAirLineImage;
        private TextView tvAirwayName, tvDepartureTime, tvArrivalTime, tvViaDetails, tvFare, tvRefundable,stops;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAirLineImage = itemView.findViewById(R.id.ivAirLineImage);
            tvAirwayName = itemView.findViewById(R.id.tvAirwayName);
            tvDepartureTime = itemView.findViewById(R.id.tvDepartureTime);
            tvArrivalTime = itemView.findViewById(R.id.tvArrivalTime);
            tvViaDetails = itemView.findViewById(R.id.tvViaDetails);
            tvFare = itemView.findViewById(R.id.tvFare);
            tvRefundable = itemView.findViewById(R.id.tvRefundable);
            stops = itemView.findViewById(R.id.tvNoOfStops);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }
}
