package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.gms.common.data.DataHolder;
import com.payz24.R;
import com.payz24.interfaces.NoResultsCallBack;
import com.payz24.responseModels.availableBuses.BoardingPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahesh on 1/29/2018.
 */

public class BoardingPointAdapter extends RecyclerView.Adapter<BoardingPointAdapter.ViewHolder> {

    private Context context;
    private List<BoardingPoint> listOfBoardingPoints;
    private ItemClickListener clickListener;
    private List<BoardingPoint> listOfTempList;
    private NoResultsCallBack noResultsCallBack;

    public BoardingPointAdapter(Context context, List<BoardingPoint> listOfBoardingPoints) {
        this.context = context;
        listOfTempList = listOfBoardingPoints;
        this.listOfBoardingPoints = listOfBoardingPoints;
        noResultsCallBack = (NoResultsCallBack) context;
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
        String name = listOfBoardingPoints.get(position).getLocation();
        String time = listOfBoardingPoints.get(position).getTime();
        holder.tvBoardingPointName.setText(name);
        holder.tvBoardingPointTime.setText(time);
    }
    public void setFilter(String searchedText) {
        listOfTempList = new LinkedList<>();
        if (!searchedText.isEmpty()) {
            for (int index = 0; index < listOfBoardingPoints.size(); index++) {
                if (listOfBoardingPoints.get(index).getTime().toLowerCase().contains(searchedText.toLowerCase())) {
                    listOfTempList.add(listOfBoardingPoints.get(index));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateList(List<BoardingPoint> list){
        listOfBoardingPoints = list;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return listOfBoardingPoints.size();
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
}
