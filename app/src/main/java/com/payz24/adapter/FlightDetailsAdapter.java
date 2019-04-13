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
import com.google.gson.JsonParser;
import com.payz24.R;
import com.payz24.activities.FlightSearchResultsActivity;
import com.payz24.responseModels.flightSearchResults.FlightSegment;
import com.payz24.responseModels.flightSearchResults.OriginDestinationOption;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahesh on 2/8/2018.
 */

public class FlightDetailsAdapter extends RecyclerView.Adapter<FlightDetailsAdapter.ViewHolder> {

    private Context context;
    private String destination = "";
    private List<OriginDestinationOption> listOfOriginDestinations;
    private ItemClickListener clickListener;
    private String totalJourneyTime = "";
    private String[] departureDateTime;
    private String[] arrivalDateTime;
    private String airwayName = "";
    String flightStops,flightNumber;
    private String airLineCode = "";
    private String fromVia = "";
    private String toVia = "";
    String rnumber,dnumber;


    public FlightDetailsAdapter(Context context, List<OriginDestinationOption> listOfOriginDestinations, String destination) {
        this.context = context;
        this.destination = destination;
        this.listOfOriginDestinations = listOfOriginDestinations;
        Log.e("flightAdapter","flightAdapter");
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
        JsonObject jsonObject = listOfOriginDestinations.get(position).getOnward().flightSegments.getAsJsonObject();
        String fare = listOfOriginDestinations.get(position).getFareDetails().getActualBaseFare();
        String tax = listOfOriginDestinations.get(position).getFareDetails().getTax();
        int totalFare = Integer.parseInt(fare) + Integer.parseInt(tax);
        List<String> listOfViaAirportNames = new ArrayList<>();
        try {
            JSONObject jo2 = new JSONObject(jsonObject.toString());
            Object json = new JSONTokener(jo2.getString("FlightSegment")).nextValue();
            if (json instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(jo2.getString("FlightSegment"), FlightSegment.class);
                totalJourneyTime = flightSegment.getJrnyTm();
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDateTime = flightSegment.getArrivalDateTime().split("T");
                airLineCode = flightSegment.getOperatingAirlineCode();
                flightStops = flightSegment.getNumStops();
                flightNumber=flightSegment.getFlightNumber();
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getOperatingAirlineName().toString();
                }
            } else if (json instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(jo2.getString("FlightSegment"));
                String response = jsonArray.getString(0);
                FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                totalJourneyTime = flightSegment.getJrnyTm();
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDateTime = new Gson().fromJson(jsonArray.getString(jsonArray.length() - 1), FlightSegment.class).getArrivalDateTime().split("T");
                airLineCode = flightSegment.getOperatingAirlineCode();

                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getOperatingAirlineName().toString();
                }
                for (int index = 0; index < jsonArray.length(); index++) {
                    String via = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class).getArrivalAirportCode();
                    String Conx = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class).getConx();//Indicates this flight connects to the next flight
                    if (Conx.equalsIgnoreCase("Y"))
                        listOfViaAirportNames.add(via);
                }
            }

            String departureTime = departureDateTime[1];
            String arrivalTime = arrivalDateTime[1];
            int journeyTime = Integer.parseInt(totalJourneyTime);
            int hours = journeyTime / 60; //since both are ints, you get an int
            int minutes = journeyTime % 60;
            String via = TextUtils.join(",", listOfViaAirportNames);
            int a=(via.split(",").length);
            if (a==1)
            {
                holder.stops.setText(a +" Stop");

            }else if (a==0)
            {
                holder.stops.setText(a+" Stop");

            }else if (a==2)
            {
                holder.stops.setText(a+" Stops");
            } else  if (a==3)
            {
                holder.stops.setText(a+" Stops");
            }


            String details = "";
            if (!via.isEmpty()) {
                details = String.valueOf(hours) + "h" + " " + String.valueOf(minutes) + "m" + " " + "|" + " " + "Via" + " " + via;
            } else {
                details = String.valueOf(hours) + "h" + " " + String.valueOf(minutes) + "m";
            }
            //for (int removeIndex = 0; removeIndex < 3; removeIndex++) {
            departureTime = departureTime.substring(0, departureTime.length() - 3);
            arrivalTime = arrivalTime.substring(0, arrivalTime.length() - 3);
            //}
            holder.tvAirwayName.setText(airwayName+"\n"+airLineCode+" "+flightNumber);
          //  holder.tvAirwayName.setText(airwayName+airLineCode);
            holder.tvDepartureTime.setText(departureTime);
            holder.tvArrivalTime.setText(arrivalTime);
            holder.tvViaDetails.setText(details);
            holder.stops.setText(flightStops);




            int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.flight_mark_up), ""));
            int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.flight_conventional_fee), ""));

            Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
            Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
            int markUpFee = (int) (Integer.parseInt(fare) * markUpPercentage);
            int totalFareInt=0;
            String busMType = PreferenceConnector.readString(context, context.getString(R.string.flight_m_type), "");

            if (busMType.equalsIgnoreCase("M"))
                totalFareInt = (int) (totalFare) - markUpFee;
            else
                totalFareInt = (int) (totalFare) + markUpFee;


            holder.tvFare.setText(context.getString(R.string.Rs) + " " + String.valueOf(totalFareInt));

          /*  int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.flight_mark_up), ""));
            int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.flight_conventional_fee), ""));

            Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
            Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
            int markUpFee = (int) (Integer.parseInt(fare) * markUpPercentage);




            int totalFareInt = (int) (totalFare) + markUpFee;

            holder.tvFare.setText(context.getString(R.string.Rs) + " " + String.valueOf(totalFareInt));*/
            String imageUrl = Constants.IMAGE_URL_BASE + "" + airLineCode + ".gif";
            Picasso.with(context).load(imageUrl).error(R.mipmap.ic_launcher_round).into(holder.ivAirLineImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void update(List<OriginDestinationOption> listOfOriginDestinations) {
        this.listOfOriginDestinations = listOfOriginDestinations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfOriginDestinations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvAirwayName, tvDepartureTime, tvArrivalTime, tvViaDetails, tvFare,stops;
        private ImageView ivAirLineImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAirwayName = itemView.findViewById(R.id.tvAirwayName);
            tvDepartureTime = itemView.findViewById(R.id.tvDepartureTime);
            tvArrivalTime = itemView.findViewById(R.id.tvArrivalTime);
            stops = itemView.findViewById(R.id.tvNoOfStops);
            tvViaDetails = itemView.findViewById(R.id.tvViaDetails);
            tvFare = itemView.findViewById(R.id.tvFare);
            ivAirLineImage = itemView.findViewById(R.id.ivAirLineImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }
}
