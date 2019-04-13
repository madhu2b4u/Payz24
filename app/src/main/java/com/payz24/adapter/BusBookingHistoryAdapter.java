package com.payz24.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.activities.CancelBusTicketActivity;
import com.payz24.activities.CancelFlightActivity;
import com.payz24.application.AppController;
import com.payz24.responseModels.busBookingHistory.Result;
import com.payz24.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahesh on 3/6/2018.
 */

public class BusBookingHistoryAdapter extends RecyclerView.Adapter<BusBookingHistoryAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Result> listOfResults;
    private ItemClickListener clickListener;
    private String selectedDate;

    public BusBookingHistoryAdapter(Context context, List<Result> listOfResults) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bus_booking_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String journeyDate = listOfResults.get(position).getJourneyDate();
        String[] convertedDate = convertDateFormat(journeyDate).split("-");
        String source = listOfResults.get(position).getSourceCity();
        String destination = listOfResults.get(position).getDestinationCity();
        String serviceProvider = listOfResults.get(position).getServiceProvider();
        String busType = listOfResults.get(position).getBusType();
        String bookingStatus = listOfResults.get(position).getBookingStatus();
        String boardingPoint = listOfResults.get(position).getBoardingPoint();


        String date = convertedDate[0];
        String dayName = convertedDate[1];
        String monthNameYear = convertedDate[2];
        holder.tvDate.setText(date);
        holder.tvDayName.setText(dayName);
        holder.tvYear.setText(monthNameYear);
        holder.tvSourceDestination.setText(source + " - " + destination);
        holder.tvOperatorName.setText(serviceProvider);
        holder.tvStatus.setText(bookingStatus);
        holder.tvBoardingPoint.setText(boardingPoint);
        holder.tvCancel.setTag(position);
        selectCurrentDate();
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(listOfResults.get(position).getJourneyDate());
            Date date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(selectedDate);

            if (date1.compareTo(date2) > 0) {
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.llCancel.setVisibility(View.VISIBLE);
                holder.cancelView.setVisibility(View.VISIBLE);
            } else if (date1.compareTo(date2) < 0) {
                holder.tvCancel.setVisibility(View.GONE);
                holder.llCancel.setVisibility(View.GONE);
                holder.cancelView.setVisibility(View.GONE);
            } else {
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.llCancel.setVisibility(View.VISIBLE);
                holder.cancelView.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvCancel.setOnClickListener(this);
        if (bookingStatus.equalsIgnoreCase(context.getString(R.string.success)) || bookingStatus.equalsIgnoreCase("Confirmed")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.tvCancel.setVisibility(View.GONE);
        }
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

                AppController.getInstance().setSelectedBusBookingResult(result);
                showAlertDialog(result);
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
        private LinearLayout llCancel;
        private View cancelView;

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
            llCancel = itemView.findViewById(R.id.llCancel);
            cancelView = itemView.findViewById(R.id.cancelView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }

    private void showAlertDialog(final Result result) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.Are_you_sure_do_you_want_to_cancel_ticket));
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent cancelBusTicketIntent = new Intent(context, CancelBusTicketActivity.class);
                cancelBusTicketIntent.putExtra(Constants.BUS_TICKET_OVER_VIEW_SCREEN_BID, result.getBid());
                context.startActivity(cancelBusTicketIntent);
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
