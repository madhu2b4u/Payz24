package com.payz24.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.responseModels.viewPlans.ViewPlans;

import java.util.LinkedList;

/**
 * Created by mahesh on 1/20/2018.
 */

public class ViewPlansAdapter extends RecyclerView.Adapter<ViewPlansAdapter.ViewHolder> {

    private Context context;
    private String type;
    private LinkedList<ViewPlans> listOfViewPlans;
    private ItemClickListener clickListener;

    public ViewPlansAdapter(Context context, LinkedList<ViewPlans> listOfViewPlans, String type) {
        this.context = context;
        this.type = type;
        this.listOfViewPlans = listOfViewPlans;
    }


    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_view_plans_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String validity = listOfViewPlans.get(position).getValidity();
        String rechargeAmount = "N/A";
        if (listOfViewPlans.get(position).getAmount() != null) {
            rechargeAmount = context.getString(R.string.Rs) + " " + listOfViewPlans.get(position).getAmount();
        }
        String description = listOfViewPlans.get(position).getDetail();
        String talkTimeValue = description;
        if (talkTimeValue != null) {
            talkTimeValue = talkTimeValue.trim();
            if (talkTimeValue.startsWith("Rs")) {
                talkTimeValue = talkTimeValue.replace("Rs.", "");
                talkTimeValue = talkTimeValue.replace("Rs", "");
            }
            talkTimeValue = context.getString(R.string.Rs) + " " + talkTimeValue;
        } else {
            talkTimeValue = "N/A";
        }
        holder.tvTalkTimeValue.setText(talkTimeValue);
        if (validity != null) {
            holder.tvValidityValue.setText(validity);
        } else {
            holder.tvValidityValue.setText("N/A");
        }
        if (description != null) {
            holder.tvDescription.setText(description);
        } else {
            holder.tvDescription.setText("N/A");
        }
        holder.tvPurchaseAmount.setText(rechargeAmount);
    }

    @Override
    public int getItemCount() {
        return listOfViewPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTalkTimeValue, tvDataValue, tvValidityValue, tvPurchaseAmount, tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTalkTimeValue = itemView.findViewById(R.id.tvTalkTimeValue);
            tvDataValue = itemView.findViewById(R.id.tvDataValue);
            tvValidityValue = itemView.findViewById(R.id.tvValidityValue);
            tvPurchaseAmount = itemView.findViewById(R.id.tvPurchaseAmount);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }
}
