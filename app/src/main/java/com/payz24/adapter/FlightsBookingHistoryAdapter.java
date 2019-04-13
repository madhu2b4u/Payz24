package com.payz24.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.payz24.R;
import com.payz24.activities.CancelFlightActivity;
import com.payz24.activities.FlightCancel.FlightCancel;
import com.payz24.activities.FlightsBookingHistoryActivity;
import com.payz24.application.AppController;
import com.payz24.http.BaseHttpClient;
import com.payz24.responseModels.flightBookingHistory.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahesh on 3/8/2018.
 */

public class FlightsBookingHistoryAdapter extends RecyclerView.Adapter<FlightsBookingHistoryAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Result> listOfResults;
    private ItemClickListener clickListener;
    private String selectedDate = "";

    public FlightsBookingHistoryAdapter(Context context, List<Result> listOfResults) {
        this.context = context;
        this.listOfResults = listOfResults;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_flight_booking_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String journeyDate = listOfResults.get(position).getDepartDate();
        String[] convertedDate = convertDateFormat(journeyDate).split("-");
        String bookingStatus = listOfResults.get(position).getBookingStatus();
        String source = listOfResults.get(position).getFromCity();
        String destination = listOfResults.get(position).getToCity();
        String journeyType = listOfResults.get(position).getJourneyType();
        String pnr = listOfResults.get(position).getPnr();
        String cr = listOfResults.get(position).getCr();


        String date = convertedDate[0];
        String dayName = convertedDate[1];
        String monthNameYear = convertedDate[2];

        holder.tvDate.setText(date);
        holder.tvDayName.setText(dayName);
        holder.tvYear.setText(monthNameYear);
        holder.tvSourceDestination.setText(source + " - " + destination);
        holder.tvOperatorName.setText(pnr);
        holder.tvCancel.setTag(position);
        if (bookingStatus.equalsIgnoreCase("SUCCESS")) {
            selectCurrentDate();
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(listOfResults.get(position).getDepartDate());
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
        if (cr.equals("1"))
        {
            holder.tvCancel.setVisibility(View.GONE);
        }else if (cr.equals("0"))
        {
            holder.tvCancel.setVisibility(View.VISIBLE);
        }


        holder.tvCancel.setOnClickListener(this);
        if (journeyType.equalsIgnoreCase("O")) {
            holder.tvBoardingPoint.setText(context.getString(R.string.one_way));
        } else {
            holder.tvBoardingPoint.setText(context.getString(R.string.round_trip));
        }
        if (!bookingStatus.equalsIgnoreCase("SUCCESS")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
        holder.tvStatus.setText(bookingStatus);
    }

    private void selectCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat selectedDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDate = selectedDateFormat.format(c.getTime());
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
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvCancel:
                int pos = (int) view.getTag();
                Result result = listOfResults.get(pos);
                if (result.getCr().equals("1"))
                {
                    Toast.makeText(context, "Cancellation is under process", Toast.LENGTH_SHORT).show();
                }else if (result.getCr().equals("0"))
                {
                    AppController.getInstance().setSelectedFlightBookingHistory(result);
                    showAlertDialog(result.getPnr());
                }
                
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listOfResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvDate, tvDayName, tvStatus, tvSourceDestination, tvOperatorName, tvYear, tvBoardingPoint, tvCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDayName = itemView.findViewById(R.id.tvDayName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvSourceDestination = itemView.findViewById(R.id.tvSourceDestination);
            tvOperatorName = itemView.findViewById(R.id.tvOperatorName);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvBoardingPoint = itemView.findViewById(R.id.tvBoardingPoint);
            tvCancel = itemView.findViewById(R.id.tvCancel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }

    private void showAlertDialog(final String pnr) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.Are_you_sure_do_you_want_to_cancel_ticket));
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent cancelFlightIntent = new Intent(context, FlightCancel.class);
                cancelFlightIntent.putExtra("pnr",pnr);
                context.startActivity(cancelFlightIntent);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
