package com.payz24.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cooltechworks.views.ScratchTextView;
import com.payz24.R;
import com.payz24.activities.FullScratchCard;
import com.payz24.utils.ScratchItem;


import java.util.List;

/**
 * Created by someo on 16-05-2018.
 */

public class ScratchAdapter extends RecyclerView.Adapter<ScratchAdapter.MyViewHolder> {

    private Context mContext;
    private List<ScratchItem> albumList;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView thumbnail;
    TextView scratchTextView;

    public MyViewHolder(View view) {
        super(view);
       // title = (TextView) view.findViewById(R.id.title);
        scratchTextView=(TextView) view.findViewById(R.id.title);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

    }

}

    public ScratchAdapter(Context mContext, List<ScratchItem> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setting, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ScratchItem album = albumList.get(position);
        holder.scratchTextView.setText(album.getPrice());
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position==0)
                {
                    Intent intent=new Intent(mContext, FullScratchCard.class);
                    intent.putExtra("price",album.getPrice());
                    mContext.startActivity(intent);
                }
                if (position==1)
                {
                    Intent intent=new Intent(mContext, FullScratchCard.class);
                    intent.putExtra("price",album.getPrice());
                    mContext.startActivity(intent);
                }

                if (position==2)
                {
                    Intent intent=new Intent(mContext, FullScratchCard.class);
                    intent.putExtra("price",album.getPrice());
                    mContext.startActivity(intent);
                }





            }
        });


        }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}