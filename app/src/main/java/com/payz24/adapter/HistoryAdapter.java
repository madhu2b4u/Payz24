package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.fragment.HistoryFragment;

import java.util.LinkedList;

/**
 * Created by mahesh on 3/7/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private LinkedList<String> listOfBookingTitles;
    private LinkedList<Integer> listOfBookingTitlesImages;
    private ItemClickListener clickListener;

    public HistoryAdapter(Context context, LinkedList<String> listOfBookingTitles, LinkedList<Integer> listOfBookingTitlesImages) {
        this.context = context;
        this.listOfBookingTitles = listOfBookingTitles;
        this.listOfBookingTitlesImages = listOfBookingTitlesImages;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_history_fragment_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivPic.setImageResource(listOfBookingTitlesImages.get(position));
        holder.tvTitle.setText(listOfBookingTitles.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfBookingTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.ivPic);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }
}
