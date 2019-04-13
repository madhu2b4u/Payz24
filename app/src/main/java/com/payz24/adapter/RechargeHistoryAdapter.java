package com.payz24.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.activities.FlightCancel.FlightCancel;
import com.payz24.application.AppController;
import com.payz24.fragment.RechargeItem;
import com.payz24.fragment.RechargeItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by someo on 29-05-2018.
 */

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.ViewHolder>  {

    private Context context;
    private List<RechargeItem> listOfResults;

    private String selectedDate = "";

    public RechargeHistoryAdapter(Context context, List<RechargeItem> listOfResults) {
        this.context = context;
        this.listOfResults = listOfResults;
    }




    @Override
    public RechargeHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new RechargeHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RechargeHistoryAdapter.ViewHolder holder, int position) {
        String rechargeDate = listOfResults.get(position).getCreated();
        String[] convertedDate = convertDateFormat(rechargeDate).split("-");
        String message = listOfResults.get(position).getMessage();
        String number = listOfResults.get(position).getRc_no();
        String network = listOfResults.get(position).getProvider_name();
        String type = listOfResults.get(position).getRc_type();
        String price = listOfResults.get(position).getRc_amount();

        String date = convertedDate[0];
        String dayName = convertedDate[1];
        String monthNameYear = convertedDate[2];

        holder.tvDate.setText(date);
        holder.tvDayName.setText(dayName);
        holder.tvYear.setText(monthNameYear);
        holder.tvOperatorName.setText(network + " - " + type);
        holder.tvSourceDestination.setText(number);
        holder.tvPrice.setText(context.getResources().getString(R.string.Rs)+" "+price);
        String stat=listOfResults.get(position).getStatus();

       /* if (message.equalsIgnoreCase("SUCCESS")) {
            selectCurrentDate();
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(listOfResults.get(position).getCreated());
                Date date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(selectedDate);
                if (date1.compareTo(date2) > 0) {
                    holder.tvCancel.setVisibility(View.VISIBLE);
                } else if (date1.compareTo(date2) < 0) {
                    holder.tvCancel.setVisibility(View.GONE);
                } else {
                    holder.tvCancel.setVisibility(View.VISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.tvCancel.setVisibility(View.GONE);
        }

        if (journeyType.equalsIgnoreCase("O")) {
            holder.tvBoardingPoint.setText(context.getString(R.string.one_way));
        } else {
            holder.tvBoardingPoint.setText(context.getString(R.string.round_trip));
        }*/
        if (!stat.equalsIgnoreCase("SUCCESS")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.tvStatus.setText(stat);
        } else {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
            holder.tvStatus.setText(stat);
        }
       // holder.tvStatus.setText("SUCCESS");
    }



    private static String convertDateFormat(String dateString) {
        String formattedDate = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = simpleDateFormat.parse(dateString);
            SimpleDateFormat outputFormat = null;
            outputFormat = new SimpleDateFormat("dd-EEEE-MMM yyyy", Locale.getDefault());
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }



    @Override
    public int getItemCount() {
        return listOfResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate, tvDayName, tvStatus, tvSourceDestination, tvOperatorName, tvYear,tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDayName = itemView.findViewById(R.id.tvDayName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvSourceDestination = itemView.findViewById(R.id.tvSourceDestination);
            tvOperatorName = itemView.findViewById(R.id.tvOperatorName);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }

    }




}