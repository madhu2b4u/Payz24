package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 2/18/2018.
 */

public class BusOperatorsFiltersAdapter extends RecyclerView.Adapter<BusOperatorsFiltersAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    private List<ApiAvailableBus> listOfAvailableBuses;
    private List<ApiAvailableBus> listOfTempAvailableBuses;
    private ItemClickListener clickListener;
    public boolean[] checkedHolder;
    private LinkedList<String> listOfSelectedOperatorsNames;
    private LinkedList<String> listOfSelectedServiceId;

    public BusOperatorsFiltersAdapter(Context context, List<ApiAvailableBus> listOfAvailableBuses) {
        this.context = context;
        listOfTempAvailableBuses = new ArrayList<>();
        listOfSelectedServiceId = new LinkedList<>();
        listOfSelectedOperatorsNames = new LinkedList<>();
        this.listOfAvailableBuses = listOfAvailableBuses;
        listOfTempAvailableBuses.addAll(listOfAvailableBuses);
        checkedHolder = new boolean[listOfAvailableBuses.size()];
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bus_operators_filters_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String travelsName = listOfTempAvailableBuses.get(position).getOperatorName();
        String serviceId = listOfTempAvailableBuses.get(position).getServiceId();
        holder.tvOperatorName.setText(travelsName);
        holder.checkBox.setTag(position);
        if (listOfSelectedServiceId.contains(serviceId)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(checkedHolder[position]);
        }
        holder.checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (int) buttonView.getTag();
        checkedHolder[position] = isChecked;
        if (isChecked) {
            if (!listOfSelectedOperatorsNames.contains(listOfTempAvailableBuses.get(position).getOperatorName())) {
                listOfSelectedOperatorsNames.add(listOfTempAvailableBuses.get(position).getOperatorName());
                listOfSelectedServiceId.add(listOfTempAvailableBuses.get(position).getServiceId());
            }
        } else {
            int index = listOfSelectedOperatorsNames.indexOf(listOfTempAvailableBuses.get(position).getOperatorName());
            if (index != -1) {
                listOfSelectedOperatorsNames.remove(index);
                listOfSelectedServiceId.remove(index);
            }
        }
        AppController.getInstance().setListOfSelectedOperatorsNames(listOfSelectedOperatorsNames);
    }

    @Override
    public int getItemCount() {
        return listOfTempAvailableBuses.size();
    }

    public void filter(String searchedText) {
        listOfTempAvailableBuses = new ArrayList<>();
        if (!searchedText.isEmpty()) {
            for (int index = 0; index < listOfAvailableBuses.size(); index++) {
                if (listOfAvailableBuses.get(index).getOperatorName().toLowerCase().contains(searchedText.toLowerCase())) {
                    listOfTempAvailableBuses.add(listOfAvailableBuses.get(index));
                }
            }
        } else {
            listOfTempAvailableBuses.addAll(listOfAvailableBuses);
        }
        checkedHolder = new boolean[listOfTempAvailableBuses.size()];
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOperatorName;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOperatorName = itemView.findViewById(R.id.tvOperatorName);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
