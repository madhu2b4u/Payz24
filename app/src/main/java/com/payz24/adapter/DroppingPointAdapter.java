package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.interfaces.NoResultsCallBack;
import com.payz24.responseModels.availableBuses.BoardingPoint;
import com.payz24.responseModels.blockTicket.DroppingPoint;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 1/29/2018.
 */

public class DroppingPointAdapter extends RecyclerView.Adapter<DroppingPointAdapter.ViewHolder> {

    private Context context;
    private String goingToStationName;
    private String arrivalTime;
    private ItemClickListener clickListener;
 //   private List<DroppingPoint> listOfBoardingPoints;

    public DroppingPointAdapter(Context context, String goingToStationName, String arrivalTime) {
        this.context = context;
        this.goingToStationName = goingToStationName;
        this.arrivalTime = arrivalTime;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_boarding_point_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvBoardingPointName.setText(goingToStationName);
        holder.tvBoardingPointTime.setText(arrivalTime);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvBoardingPointName, tvBoardingPointTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBoardingPointName = itemView.findViewById(R.id.tvBoardingPointName);
            tvBoardingPointTime = itemView.findViewById(R.id.tvBoardingPointTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }

/*
    public void updateList(List<DroppingPoint> list){
        listOfBoardingPoints = list;
        notifyDataSetChanged();
    }
*/





}
