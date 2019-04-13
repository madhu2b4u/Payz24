package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.activities.HomeActivity;

import java.util.LinkedList;

/**
 * Created by mahesh on 1/10/2018.
 */

public class ShopOnPay24Adapter extends RecyclerView.Adapter<ShopOnPay24Adapter.ViewHolder> {

    private Context context;
    private LinkedList<String> listOfCollectionNames;
    private LinkedList<Integer> listOfCollectionImages;
    private ShopOnPay24Adapter.ItemClickListener clickListener;

    public ShopOnPay24Adapter(Context context, LinkedList<String> listOfCollectionNames, LinkedList<Integer> listOfCollectionImages) {
        this.context = context;
        this.listOfCollectionNames = listOfCollectionNames;
        this.listOfCollectionImages = listOfCollectionImages;
    }

    public interface ItemClickListener {
        void onShopOnPay24ClickListener(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_home_categories_items, parent, false);
        return new ShopOnPay24Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvCollectionName.setText(listOfCollectionNames.get(position));
        holder.ivCollectionImage.setImageResource(listOfCollectionImages.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfCollectionNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivCollectionImage;
        private TextView tvCollectionName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCollectionImage = itemView.findViewById(R.id.ivCollectionImage);
            tvCollectionName = itemView.findViewById(R.id.tvCollectionName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onShopOnPay24ClickListener(view, getLayoutPosition());
        }
    }
}
