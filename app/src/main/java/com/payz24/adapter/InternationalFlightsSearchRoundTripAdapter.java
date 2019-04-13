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
import com.payz24.responseModels.flightSearchResults.FlightSegment;
import com.payz24.responseModels.flightSearchResults.OriginDestinationOption;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahesh on 2/17/2018.
 */

public class InternationalFlightsSearchRoundTripAdapter extends RecyclerView.Adapter<InternationalFlightsSearchRoundTripAdapter.ViewHolder> {

    private Context context;
    private List<OriginDestinationOption> listOfOriginDestinations;
    private ItemClickListener clickListener;
    private String totalDepartureJourneyTime;
    private String[] departureDateTime;
    private String[] arrivalDepartureDateTime;
    private String departureAirLineCode;
    private String departureAirwayName;

    private String totalReturnJourneyTime;
    private String[] returnDateTime;
    private String[] arrivalReturnDateTime;
    private String returnAirLineCode;
    private String returnAirwayName;
    private String fromVia = "";
    private String toVia = "";
    String rnumber,dnumber;

    public InternationalFlightsSearchRoundTripAdapter(Context context, List<OriginDestinationOption> listOfOriginDestinations) {
        this.context = context;
        this.listOfOriginDestinations = listOfOriginDestinations;
    }

    public void update(List<OriginDestinationOption> listOfOriginDestinations) {
        this.listOfOriginDestinations = listOfOriginDestinations;
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
        View view = LayoutInflater.from(context).inflate(R.layout.layout_international_flights_search_round_trip_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        fromVia = "";
        toVia = "";
        String returnJsonObject = "";
        JsonObject jsonObject = listOfOriginDestinations.get(position).getOnward().flightSegments.getAsJsonObject();
        if (listOfOriginDestinations.get(position).getReturnJsonObject() instanceof JSONArray) {

        } else {
            Gson gson = new Gson();
            returnJsonObject = gson.toJson(listOfOriginDestinations.get(position).getReturnJsonObject());
        }
        //JsonObject returnJsonObject = listOfOriginDestinations.get(position).getReturnJsonObject().getAsJsonObject();
        String fare = listOfOriginDestinations.get(position).getFareDetails().getActualBaseFare();
        String tax = listOfOriginDestinations.get(position).getFareDetails().getTax();
        int totalFare = Integer.parseInt(fare) + Integer.parseInt(tax);
        List<String> listOfViaAirportNames = new ArrayList<>();
        try {
            JSONObject jo2 = new JSONObject(jsonObject.toString());
            JSONObject returnJsonObj = new JSONObject(returnJsonObject);
            JSONObject flightsSegmentsJsonObject = new JSONObject(returnJsonObj.getString("FlightSegments"));
            Object json = new JSONTokener(jo2.getString("FlightSegment")).nextValue();
            Object jsonReturn = new JSONTokener(flightsSegmentsJsonObject.getString("FlightSegment")).nextValue();
            if (json instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(jo2.getString("FlightSegment"), FlightSegment.class);
                totalDepartureJourneyTime = flightSegment.getJrnyTm();
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDepartureDateTime = flightSegment.getArrivalDateTime().split("T");
                departureAirLineCode = flightSegment.getOperatingAirlineCode();
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    departureAirwayName = flightSegment.getOperatingAirlineName().toString();
                    dnumber=flightSegment.getFlightNumber().toString();
                }
            } else if (json instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(jo2.getString("FlightSegment"));
                String response = jsonArray.getString(0);
                FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                totalDepartureJourneyTime = flightSegment.getJrnyTm();
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDepartureDateTime = new Gson().fromJson(jsonArray.getString(jsonArray.length() - 1), FlightSegment.class).getArrivalDateTime().split("T");
                departureAirLineCode = flightSegment.getOperatingAirlineCode();
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    departureAirwayName = flightSegment.getOperatingAirlineName().toString();
                    dnumber=flightSegment.getFlightNumber().toString();
                }
                // for (int index = 0; index < jsonArray.length(); index++) {
                fromVia = new Gson().fromJson(jsonArray.getString(0), FlightSegment.class).getArrivalAirportCode();
                // String Conx = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class).getConx();//Indicates this flight connects to the next flight
                //if (Conx.equalsIgnoreCase("Y"))
                //listOfViaAirportNames.add("mahesh");
                //}
            }

            if (jsonReturn instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightsSegmentsJsonObject.getString("FlightSegment"), FlightSegment.class);
                totalReturnJourneyTime = flightSegment.getJrnyTm();
                returnDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalReturnDateTime = flightSegment.getArrivalDateTime().split("T");
                returnAirLineCode = flightSegment.getOperatingAirlineCode();
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    returnAirwayName = flightSegment.getOperatingAirlineName().toString();
                    rnumber=flightSegment.getFlightNumber().toString();
                }
            } else if (jsonReturn instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightsSegmentsJsonObject.getString("FlightSegment"));
                String response = jsonArray.getString(0);
                FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                totalReturnJourneyTime = flightSegment.getJrnyTm();
                returnDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalReturnDateTime = new Gson().fromJson(jsonArray.getString(jsonArray.length() - 1), FlightSegment.class).getArrivalDateTime().split("T");
                returnAirLineCode = flightSegment.getOperatingAirlineCode();
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    returnAirwayName = flightSegment.getOperatingAirlineName().toString();
                    rnumber=flightSegment.getFlightNumber().toString();
                }
                //for (int index = 0; index < jsonArray.length(); index++) {
                toVia = new Gson().fromJson(jsonArray.getString(0), FlightSegment.class).getArrivalAirportCode();
                //String Conx = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class).getConx();//Indicates this flight connects to the next flight
                // if (Conx.equalsIgnoreCase("Y"))
                //listOfViaAirportNames.add("via");
                //   }
            }

            String departureTime = departureDateTime[1];
            String arrivalTime = arrivalDepartureDateTime[1];
            int journeyTime = Integer.parseInt(totalDepartureJourneyTime);
            int hours = journeyTime / 60; //since both are ints, you get an int
            int minutes = journeyTime % 60;
            String details = "";
            if (!fromVia.isEmpty()) {
                details = String.valueOf(hours) + "h" + " " + String.valueOf(minutes) + "m" + " " + "|" + " " + "Via" + " " + fromVia;
            } else {
                details = String.valueOf(hours) + "h" + " " + String.valueOf(minutes) + "m";
            }
            //for (int removeIndex = 0; removeIndex < 3; removeIndex++) {
            departureTime = departureTime.substring(0, departureTime.length() - 3);
            arrivalTime = arrivalTime.substring(0, arrivalTime.length() - 3);
            //}



            String returnTime = returnDateTime[1];
            String arrivalReturnTime = arrivalReturnDateTime[1];
            int journeyReturnTime = Integer.parseInt(totalReturnJourneyTime);
            int returnHours = journeyReturnTime / 60; //since both are ints, you get an int
            int returnMinutes = journeyReturnTime % 60;
            String returnVia = TextUtils.join(",", listOfViaAirportNames);
            String returnDetails = "";
            if (!toVia.isEmpty()) {
                returnDetails = String.valueOf(returnHours) + "h" + " " + String.valueOf(returnMinutes) + "m" + " " + "|" + " " + "Via" + " " + toVia;
            } else {
                returnDetails = String.valueOf(returnHours) + "h" + " " + String.valueOf(returnMinutes) + "m";
            }
            //for (int removeIndex = 0; removeIndex < 3; removeIndex++) {
            returnTime = returnTime.substring(0, returnTime.length() - 3);
            arrivalReturnTime = arrivalReturnTime.substring(0, arrivalReturnTime.length() - 3);
            //}

            holder.tvReturnAirwayName.setText(returnAirwayName+"\n"+returnAirLineCode+" "+rnumber);
            holder.tvReturnDepartureTime.setText(returnTime);
            holder.tvReturnArrivalTime.setText(arrivalReturnTime);
            holder.tvReturnViaDetails.setText(returnDetails);

            int af=(fromVia.split(",").length);
            int at=(toVia.split(",").length);
            Log.e("af",""+af);
            Log.e("at",""+at);

            if (at==1)
            {
                holder.tvReturnStops.setText(at +" Stop");

            }else if (at==0)
            {
                holder.tvReturnStops.setText(at+" Stop");

            }else if (at==2)
            {
                holder.tvReturnStops.setText(at+" Stops");
            } else  if (at==3)
            {
                holder.tvReturnStops.setText(at+" Stops");
            }


            if (af==1)
            {
                holder.tvOnewayStops.setText(af +" Stop");

            }else if (af==0)
            {
                holder.tvOnewayStops.setText(af+" Stop");

            }else if (af==2)
            {
                holder.tvOnewayStops.setText(af+" Stops");
            } else  if (af==3)
            {
                holder.tvOnewayStops.setText(af+" Stops");
            }

            String imageUrl = Constants.IMAGE_URL_BASE + "" + returnAirLineCode + ".gif";
            Picasso.with(context).load(imageUrl).error(R.mipmap.ic_launcher_round).into(holder.ivReturnAirLineImage);

            holder.tvDepartureAirwayName.setText(departureAirwayName+"\n"+departureAirLineCode+" "+dnumber);
            holder.tvDepartureDepartureTime.setText(departureTime);
            holder.tvDepartureArrivalTime.setText(arrivalTime);
            holder.tvDepartureViaDetails.setText(details);

            int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.flight_mark_up), ""));
            int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.flight_conventional_fee), ""));

            Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
            Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
            int markUpFee = (int) (Integer.parseInt(fare) * markUpPercentage);
            String busMType = PreferenceConnector.readString(context, context.getString(R.string.flight_m_type), "");
            int totalFareInt=0;
            if (busMType.equalsIgnoreCase("M"))
                totalFareInt = (int) (totalFare) - markUpFee;
            else
                totalFareInt = (int) (totalFare) + markUpFee;


            holder.tvFare.setText(context.getString(R.string.Rs) + " " + String.valueOf(totalFareInt));
            String returnImageUrl = Constants.IMAGE_URL_BASE + "" + departureAirLineCode + ".gif";
            Picasso.with(context).load(returnImageUrl).error(R.mipmap.ic_launcher_round).into(holder.ivDepartureAirLineImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listOfOriginDestinations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvDepartureAirwayName, tvDepartureDepartureTime, tvDepartureArrivalTime, tvDepartureViaDetails;
        private ImageView ivDepartureAirLineImage;

        private TextView tvReturnAirwayName, tvReturnDepartureTime, tvReturnArrivalTime, tvReturnViaDetails, tvFare,tvOnewayStops,tvReturnStops;
        private ImageView ivReturnAirLineImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDepartureAirwayName = itemView.findViewById(R.id.tvDepartureAirwayName);
            tvDepartureDepartureTime = itemView.findViewById(R.id.tvDepartureDepartureTime);
            tvDepartureArrivalTime = itemView.findViewById(R.id.tvDepartureArrivalTime);
            tvDepartureViaDetails = itemView.findViewById(R.id.tvDepartureViaDetails);
            tvFare = itemView.findViewById(R.id.tvFare);

            tvReturnAirwayName = itemView.findViewById(R.id.tvReturnAirwayName);
            tvReturnDepartureTime = itemView.findViewById(R.id.tvReturnDepartureTime);
            tvReturnArrivalTime = itemView.findViewById(R.id.tvReturnArrivalTime);
            tvReturnViaDetails = itemView.findViewById(R.id.tvReturnViaDetails);

            ivDepartureAirLineImage = itemView.findViewById(R.id.ivDepartureAirLineImage);
            ivReturnAirLineImage = itemView.findViewById(R.id.ivReturnAirLineImage);
            tvOnewayStops = itemView.findViewById(R.id.tvOnewayStops);
            tvReturnStops = itemView.findViewById(R.id.tvReturnStops);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getLayoutPosition());
            }
        }
    }
}
