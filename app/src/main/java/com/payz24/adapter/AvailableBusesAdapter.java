package com.payz24.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.payz24.R;
import com.payz24.activities.AvailableBusesActivity;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.busStations.StationList;
import com.payz24.utils.PreferenceConnector;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mahesh on 1/26/2018.
 */

public class AvailableBusesAdapter extends RecyclerView.Adapter<AvailableBusesAdapter.ViewHolder> {

    private Context context;
    private List<ApiAvailableBus> listOfAvailableBuses;
    private ItemClickListener clickListener;

    public AvailableBusesAdapter(Context context, List<ApiAvailableBus> listOfAvailableBuses) {
        this.context = context;
        this.listOfAvailableBuses = listOfAvailableBuses;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_available_buses_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String travelOperatorName = listOfAvailableBuses.get(position).getOperatorName();
        String travelOperatorBusType = listOfAvailableBuses.get(position).getBusType();
        String departureTime = listOfAvailableBuses.get(position).getDepartureTime();
        String arrivalTime = listOfAvailableBuses.get(position).getArrivalTime();
        int availableSeats = listOfAvailableBuses.get(position).getAvailableSeats();
        String fare = context.getString(R.string.Rs) + " " + listOfAvailableBuses.get(position).getFare();
        Log.e("Availiable Bus",travelOperatorName+","+travelOperatorBusType+","+departureTime+","+arrivalTime+","+availableSeats+","+fare);

        try
        {
            int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.bus_mark_up), ""));
            int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(context, context.getString(R.string.bus_conventional_fee), ""));
            Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
            Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
            String busMType = PreferenceConnector.readString(context, context.getString(R.string.bus_m_type), "");
            Log.e("busMType",""+busMType);
            int markUpFee = 0;
            int totalFare = 0;
            if (listOfAvailableBuses.get(position).getFare().contains(",")) {
                String[] multipleFares = listOfAvailableBuses.get(position).getFare().split(",");
                int busFare = Integer.parseInt(multipleFares[0]);
                markUpFee = (int) ((busFare) * markUpPercentage);


                if (busMType.equalsIgnoreCase("M"))
                    totalFare = (int) (busFare) - markUpFee;
                    // totalFare = (int) (busFare);
                else
                    totalFare = (int) (busFare) + markUpFee;
                // totalFare = (int) (busFare) ;


            } else {
                markUpFee = (int) (Integer.parseInt(listOfAvailableBuses.get(position).getFare()) * markUpPercentage);

                if (busMType.equalsIgnoreCase("M"))
                    totalFare = (int) (Integer.parseInt(listOfAvailableBuses.get(position).getFare()) - markUpFee);
                    //totalFare = (int) (Integer.parseInt(listOfAvailableBuses.get(position).getFare()));
                else
                    totalFare = (int) (Integer.parseInt(listOfAvailableBuses.get(position).getFare()) + markUpFee);
                // totalFare = (int) (Integer.parseInt(listOfAvailableBuses.get(position).getFare()));

             /*   Log.e("markUpFee",""+markUpFee);
                Log.e("totalFare",""+totalFare);
                Log.e("busFare",""+Integer.parseInt(listOfAvailableBuses.get(position).getFare()));*/

                holder.tvTravelOperatorName.setText(travelOperatorName);
                holder.tvTravelOperatorBusType.setText(travelOperatorBusType);
                holder.tvLeavingFromTime.setText(departureTime);
                holder.tvReachingTime.setText(arrivalTime);
                holder.tvAvailableSeats.setText(String.valueOf(availableSeats) + " " + "seats");
                holder.tvCost.setText(context.getString(R.string.Rs) +""+String.valueOf(totalFare));
                holder.tvEstimatedJourneyHours.setVisibility(View.GONE);


            }


        }catch (Exception e)
        {
            e.printStackTrace();

        }







    }

    @Override
    public int getItemCount() {
        return listOfAvailableBuses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTravelOperatorName, tvLeavingFromTime, tvTravelOperatorBusType, tvReachingTime, tvEstimatedJourneyHours, tvAvailableSeats, tvCost;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTravelOperatorName = itemView.findViewById(R.id.tvTravelOperatorName);
            tvLeavingFromTime = itemView.findViewById(R.id.tvLeavingFromTime);
            tvTravelOperatorBusType = itemView.findViewById(R.id.tvTravelOperatorBusType);
            tvReachingTime = itemView.findViewById(R.id.tvReachingTime);
            tvEstimatedJourneyHours = itemView.findViewById(R.id.tvEstimatedJourneyHours);

            tvAvailableSeats = itemView.findViewById(R.id.tvAvailableSeats);
            tvCost = itemView.findViewById(R.id.tvCost);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }


/*
    public  String calculateTime(String time1, String time2)
    {
       */
/* SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
        Date date1 = null;
        Date date2=  null;
        String times=null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);
            long difference = date2.getTime() - date1.getTime();
            times=""+(difference/1000);
            System.out.println(difference/1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }*//*

       // return  times;
        String times=null;
        try
        {
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();
            System.out.println("today:    " + today);
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            // now get "tomorrow"
            Date tomorrow = calendar.getTime();

            // print out tomorrow's date
            System.out.println("tomorrow: " + tomorrow);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            String todayAsString = dateFormat.format(today);
            String tomorrowAsString = dateFormat.format(tomorrow);

            System.out.println(todayAsString);
            System.out.println(tomorrowAsString);

            Log.e("time1",todayAsString+" "+time1);
            Log.e("time2",tomorrowAsString+" "+time2);
            String format = "dd/MM/yyyy hh:mm a";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date dateObj1 = sdf.parse(todayAsString+" "+time1);
            Date dateObj2 = sdf.parse(tomorrowAsString+" "+time2);
            System.out.println(dateObj1);
            System.out.println(dateObj2 + "\n");
            DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

            long diff = dateObj2.getTime() - dateObj1.getTime();
            int diffmin = (int) (diff / (60 * 1000));
            System.out.println("difference between minutues: " + crunchifyFormatter.format(diffmin));

            String startTime = "00:00";
            int minutes = diffmin;
            int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
            int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
            String newtime = h+":"+m;
            Log.e("newtime",newtime);


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return  times;
    }
*/


}
