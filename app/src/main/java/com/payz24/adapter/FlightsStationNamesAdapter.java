package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.activities.FlightsStationNamesActivity;
import com.payz24.interfaces.NoResultsCallBack;
import com.payz24.responseModels.flightStations.FlightStations;
import com.payz24.responseModels.flightStations.Station;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 2/4/2018.
 */

public class FlightsStationNamesAdapter extends RecyclerView.Adapter<FlightsStationNamesAdapter.ViewHolder> {

    private Context context;
    private String from;
    private List<Station> listOfFlightStations;
    private ItemClickListener clickListener;
    private List<Station> listOfTempList;
    private NoResultsCallBack noResultsCallBack;

    public FlightsStationNamesAdapter(Context context, List<Station> listOfFlightStations, String from) {
        this.context = context;
        this.from = from;
        this.listOfFlightStations = listOfFlightStations;
        listOfTempList = listOfFlightStations;
        noResultsCallBack = (NoResultsCallBack) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_flights_stations_names_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String city = listOfTempList.get(position).getCity();
        String country = listOfTempList.get(position).getCountry();
        String cityCode = listOfTempList.get(position).getCityCode();
        String fullName = cityCode + "," + city + " " + country;
        holder.tvStationName.setText(city);
        holder.tvStationFullName.setText(fullName);
    }

    @Override
    public int getItemCount() {
        return listOfTempList.size();
    }

    public void filter(String searchedText) {
        listOfTempList = new LinkedList<>();
        if (searchedText.isEmpty()) {
            listOfTempList = listOfFlightStations;
        } else {
            for (int index = 0; index < listOfFlightStations.size(); index++) {
                if (listOfFlightStations.get(index).getCity().toLowerCase().contains(searchedText.toLowerCase())) {
                    listOfTempList.add(listOfFlightStations.get(index));
                }
                if (listOfFlightStations.get(index).getCityCode().toLowerCase().contains(searchedText.toLowerCase())) {
                    if (!listOfTempList.contains(listOfFlightStations.get(index))) {
                        listOfTempList.add(listOfFlightStations.get(index));
                    }
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

    public interface ItemClickListener {
        void onClick(View view, int position, String comingFrom, Station station);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivPic;
        private TextView tvStationName, tvStationFullName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.ivPic);
            tvStationName = itemView.findViewById(R.id.tvStationName);
            tvStationFullName = itemView.findViewById(R.id.tvStationFullName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition(), from, listOfTempList.get(getLayoutPosition()));
        }
    }
}
