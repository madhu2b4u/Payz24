package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.payz24.R;
import com.payz24.responseModels.busLayout.Seat;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by mahesh on 1/27/2018.
 */

public class BusLayoutAdapter extends RecyclerView.Adapter<BusLayoutAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Seat> listOfSeatStructure;
    private boolean isLadiesSeat;
    private boolean isAvailable;
    private boolean isSleeper;
    private int selectedRow;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private ItemClickListener clickListener;
    private LinkedHashMap<Integer, Boolean> mapOfPositionAndSelection;
    private boolean isSelected;


    public BusLayoutAdapter(Context context, List<Seat> listOfSeatStructure, int selectedRow) {
        this.context = context;
        this.selectedRow = selectedRow;
        this.listOfSeatStructure = listOfSeatStructure;

        mapOfPositionAndSelection = new LinkedHashMap<>();
    }

    public interface ItemClickListener {
        void onSeatClicked(View view, int position, boolean isSelected);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_bus_layout_item_header, parent, false);
            return new ViewHolder(view, viewType);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_bus_layout_items, parent, false);
            return new ViewHolder(view, viewType);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.holderId == TYPE_ITEM) {
            if (listOfSeatStructure.get(position - 1).getLadiesSeat() != null) {
                isLadiesSeat = listOfSeatStructure.get(position - 1).getLadiesSeat();
                isAvailable = listOfSeatStructure.get(position - 1).getAvailable();
                isSleeper = listOfSeatStructure.get(position - 1).getSleeper();
            }
            String seatName = listOfSeatStructure.get(position - 1).getId();
            if(selectedRow == 1){
                ViewGroup.MarginLayoutParams marginLayoutParams1 = (ViewGroup.MarginLayoutParams) holder.ivBusSeat.getLayoutParams();
                holder.ivBusSeat.getLayoutParams().height = 200;
                holder.ivBusSeat.getLayoutParams().width = 200;
                holder.ivBusSeat.setLayoutParams(marginLayoutParams1);
            }
            if (seatName != null) {
                if (listOfSeatStructure.get(position - 1).getLength() > 1 || listOfSeatStructure.get(position - 1).getWidth() > 1) {
                    if (isLadiesSeat) {
                        holder.ivBusSeat.setImageResource(R.drawable.ic_bus_sleeper_seat_ladies);
                        holder.ivBusSeat.setOnClickListener(this);
                        holder.ivBusSeat.setTag(position - 1 + "&&" + context.getString(R.string.sleeper_seat_ladies));
                    } else if (isAvailable) {
                        holder.ivBusSeat.setImageResource(R.drawable.ic_bus_sleeper_seat);
                        holder.ivBusSeat.setOnClickListener(this);
                        holder.ivBusSeat.setTag(position - 1 + "&&" + context.getString(R.string.sleeper_seat_normal));
                    } else {
                        holder.ivBusSeat.setImageResource(R.drawable.ic_bus_sleeper_seat_booked);
                        holder.ivBusSeat.setOnClickListener(null);
                    }
                } else if (!isSleeper) {
                    if (isLadiesSeat) {
                        if (isAvailable) {
                            holder.ivBusSeat.setImageResource(R.drawable.ic_bus_seat_ladies);
                            holder.ivBusSeat.setOnClickListener(this);
                            holder.ivBusSeat.setTag(position - 1 + "&&" + context.getString(R.string.seat_ladies));
                        } else {
                            holder.ivBusSeat.setImageResource(R.drawable.ic_bus_seat_booked);
                            holder.ivBusSeat.setOnClickListener(null);
                        }
                    } else if (isAvailable) {
                        holder.ivBusSeat.setImageResource(R.drawable.ic_bus_seat);
                        holder.ivBusSeat.setOnClickListener(this);
                        holder.ivBusSeat.setTag(position - 1 + "&&" + context.getString(R.string.bus_seat_normal));
                    } else {
                        holder.ivBusSeat.setImageResource(R.drawable.ic_bus_seat_booked);
                        holder.ivBusSeat.setOnClickListener(null);
                    }
                } else {
                    if (isLadiesSeat) {
                        holder.ivBusSeat.setImageResource(R.drawable.ic_bus_sleeper_seat_ladies);
                        holder.ivBusSeat.setOnClickListener(this);
                        holder.ivBusSeat.setTag(position + "&&" + context.getString(R.string.sleeper_seat_ladies));
                    } else if (isAvailable) {
                        holder.ivBusSeat.setImageResource(R.drawable.ic_bus_sleeper_seat);
                        holder.ivBusSeat.setOnClickListener(this);
                        holder.ivBusSeat.setTag(position + "&&" + context.getString(R.string.sleeper_seat_normal));
                    } else {
                        holder.ivBusSeat.setImageResource(R.drawable.ic_bus_sleeper_seat_booked);
                        holder.ivBusSeat.setOnClickListener(null);
                    }
                }
            } else {
                holder.ivBusSeat.setImageResource(R.drawable.ic_empty_space);
            }
        } else {
            holder.ivDriverPic.setImageResource(R.drawable.ic_steering_wheel);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return listOfSeatStructure.size() + 1;
    }

    @Override
    public void onClick(View view) {
        ImageView ivSeat = view.findViewById(R.id.ivBusSeat);
        String[] positionSeatName = view.getTag().toString().split("&&");
        int pos = Integer.parseInt(positionSeatName[0]);
        String seatName = positionSeatName[1];

        //if(mapOfPositionAndSelection.size() < 6) {
        if (mapOfPositionAndSelection.containsKey(pos)) {
            isSelected = false;
            mapOfPositionAndSelection.remove(pos);
            if (seatName.equalsIgnoreCase(context.getString(R.string.sleeper_seat_ladies))) {
                ivSeat.setImageResource(R.drawable.ic_bus_sleeper_seat_ladies);
            } else if (seatName.equalsIgnoreCase(context.getString(R.string.sleeper_seat_normal))) {
                ivSeat.setImageResource(R.drawable.ic_bus_sleeper_seat);
            } else if (seatName.equalsIgnoreCase(context.getString(R.string.seat_ladies))) {
                ivSeat.setImageResource(R.drawable.ic_bus_seat_ladies);
            } else if (seatName.equalsIgnoreCase(context.getString(R.string.bus_seat_normal))) {
                ivSeat.setImageResource(R.drawable.ic_bus_seat);
            }
        } else {
            if (mapOfPositionAndSelection.size() < 6) {
                mapOfPositionAndSelection.put(pos, true);
                isSelected = true;
                if (seatName.equalsIgnoreCase(context.getString(R.string.sleeper_seat_ladies))) {
                    ivSeat.setImageResource(R.drawable.ic_bus_sleeper_seat_selected);
                } else if (seatName.equalsIgnoreCase(context.getString(R.string.sleeper_seat_normal))) {
                    ivSeat.setImageResource(R.drawable.ic_bus_sleeper_seat_selected);
                } else if (seatName.equalsIgnoreCase(context.getString(R.string.seat_ladies))) {
                    ivSeat.setImageResource(R.drawable.ic_bus_seat_selected);
                } else if (seatName.equalsIgnoreCase(context.getString(R.string.bus_seat_normal))) {
                    ivSeat.setImageResource(R.drawable.ic_bus_seat_selected);
                }
            } else {
                pos = -1;
            }
        }
        if (clickListener != null)
            clickListener.onSeatClicked(view, pos, isSelected);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivBusSeat, ivDriverPic;
        private int holderId;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_ITEM) {
                holderId = TYPE_ITEM;
                ivBusSeat = itemView.findViewById(R.id.ivBusSeat);
            } else {
                ivDriverPic = itemView.findViewById(R.id.ivDriverPic);
            }
        }
    }
}
