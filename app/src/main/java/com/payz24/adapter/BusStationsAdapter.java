package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.interfaces.BusStationsCallBack;
import com.payz24.interfaces.NoResultsCallBack;
import com.payz24.responseModels.busStations.StationList;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 1/24/2018.
 */

public class BusStationsAdapter extends RecyclerView.Adapter<BusStationsAdapter.ViewHolder> {

    private Context context;
    private String clickedOn;
    private List<StationList> listOfTempList;
    private List<StationList> listOfBusStations;
    private ItemClickListener clickListener;
    private NoResultsCallBack noResultsCallBack;

    public BusStationsAdapter(Context context, List<StationList> listOfBusStations, String clickedOn) {
        this.context = context;
        this.listOfBusStations = listOfBusStations;
        this.listOfTempList = listOfBusStations;
        this.clickedOn = clickedOn;
        noResultsCallBack = (NoResultsCallBack) context;
    }

    public interface ItemClickListener {
        void onClick(View view, int position, StationList stationList);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bus_stations_items, parent, false);
        return new ViewHolder(view);
    }

    public void setFilter(String searchedText) {
        listOfTempList = new LinkedList<>();
        if (searchedText.isEmpty()) {
            listOfTempList = listOfBusStations;
            if (listOfTempList.isEmpty()) {
                noResultsCallBack.noResultsFound(false);
            } else {
                noResultsCallBack.noResultsFound(true);
            }
        } else {
            for (int index = 0; index < listOfBusStations.size(); index++) {
                if (listOfBusStations.get(index).getStationName().toLowerCase().contains(searchedText.toLowerCase())) {
                    listOfTempList.add(listOfBusStations.get(index));
                }
            }
            if (listOfTempList.isEmpty()) {
                noResultsCallBack.noResultsFound(false);
            } else {
                noResultsCallBack.noResultsFound(true);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String stationName = listOfTempList.get(position).getStationName();
        if (clickedOn.equalsIgnoreCase(context.getString(R.string.leaving_from))) {
            holder.ivPic.setImageResource(R.drawable.ic_leaving_from);
        } else {
            holder.ivPic.setImageResource(R.drawable.ic_going_to);
        }
        holder.tvStationName.setText(stationName);
    }

    @Override
    public int getItemCount() {
        return listOfTempList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivPic;
        private TextView tvStationName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.ivPic);
            tvStationName = itemView.findViewById(R.id.tvStationName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition(), listOfTempList.get(getLayoutPosition()));
        }
    }
}
