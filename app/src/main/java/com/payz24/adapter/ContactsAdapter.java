package com.payz24.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.payz24.R;
import com.payz24.activities.ContactsActivity;
import com.payz24.application.AppController;
import com.payz24.interfaces.ContactsCallBack;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mahesh on 1/15/2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context context;
    private LinkedHashMap<String, String> mapOfContactNameAndNumber;
    private LinkedList<String> listOfContactNames;
    private LinkedList<String> listOfContactNumbers;
    private LinkedList<String> listOfTempContactNames;
    private LinkedList<String> listOfTempContactNumbers;
    private ItemClickListener clickListener;
    private TextDrawable.IBuilder mDrawableBuilder;
    private ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
    private View view;
    private TextDrawable drawable;

    public ContactsAdapter(Context context, LinkedHashMap<String, String> mapOfContactNameAndNumber) {
        this.context = context;
        this.mapOfContactNameAndNumber = mapOfContactNameAndNumber;
        listOfContactNames = new LinkedList<>(mapOfContactNameAndNumber.keySet());
        listOfContactNumbers = new LinkedList<>(mapOfContactNameAndNumber.values());
        listOfTempContactNames = listOfContactNames;
        listOfTempContactNumbers = listOfContactNumbers;
        mDrawableBuilder = TextDrawable.builder().round();
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_contact_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String contactName = listOfTempContactNames.get(position);
        String contactNumber = listOfTempContactNumbers.get(position);
        List<String> listOfTempContactNumbers = Arrays.asList(contactNumber.split("\\s*,\\s*"));
        if (!contactName.isEmpty())
            drawable = mDrawableBuilder.build(String.valueOf(contactName.charAt(0)), colorGenerator.getColor(contactName.charAt(0)));
        else
            drawable = mDrawableBuilder.build(String.valueOf(contactNumber.charAt(0)), colorGenerator.getColor(contactNumber.charAt(0)));
        holder.ivContactPic.setImageDrawable(drawable);
        holder.tvContactName.setText(contactName);
        if (holder.llContactNumbers != null)
            holder.llContactNumbers.removeAllViews();
        for (int index = 0; index < listOfTempContactNumbers.size(); index++) {
            initializeView(contactName, index, listOfTempContactNumbers.get(index));
            holder.llContactNumbers.addView(view);
        }
    }

    public void setFilter(String searchedText) {
        listOfTempContactNumbers = new LinkedList<>();
        listOfTempContactNames = new LinkedList<>();
        if (!searchedText.isEmpty()) {
            for (int index = 0; index < listOfContactNumbers.size(); index++) {
                if (listOfContactNumbers.get(index).toLowerCase().contains(searchedText.toLowerCase())) {
                    String contactName = listOfContactNames.get(index);
                    String contactNumber = listOfContactNumbers.get(index);
                    if (!listOfTempContactNames.contains(contactName))
                        listOfTempContactNames.add(contactName);
                    if (!listOfTempContactNumbers.contains(contactNumber))
                        listOfTempContactNumbers.add(contactNumber);
                } else if (listOfContactNames.get(index).toLowerCase().contains(searchedText.toLowerCase())) {
                    String contactName = listOfContactNames.get(index);
                    String contactNumber = listOfContactNumbers.get(index);
                    if (!listOfTempContactNames.contains(contactName))
                        listOfTempContactNames.add(contactName);
                    if (!listOfTempContactNumbers.contains(contactNumber))
                        listOfTempContactNumbers.add(contactNumber);
                } else {
                    if (searchedText.matches(".*\\d.*")) {
                        if(!listOfTempContactNumbers.contains(searchedText)) {
                            listOfTempContactNumbers.add(searchedText);
                            listOfTempContactNames.add("");
                        }
                    }
                }
            }
        } else {
            listOfTempContactNames = listOfContactNames;
            listOfTempContactNumbers = listOfContactNumbers;
        }
        notifyDataSetChanged();
    }

    private void initializeView(String contactName, int index, String contactNumber) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_contact_sub_items, null);
        TextView tvContactNumber = view.findViewById(R.id.tvContactNumber);
        if (index != 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvContactNumber.getLayoutParams();
            params.setMargins(0, 100, 0, 0);
            tvContactNumber.setLayoutParams(params);
        }
        tvContactNumber.setText(contactNumber);
        tvContactNumber.setTag(contactName + "&&" + contactNumber);
        tvContactNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] contactNameAndNumber = view.getTag().toString().split("&&");
                // ContactsCallBack contactsCallBack = (ContactsCallBack) AppController.getInstance().getMobileRechargeContext();
                ContactsCallBack contactsCallBack = (ContactsCallBack) context;
                contactsCallBack.selectedContacts(contactNameAndNumber[0], contactNameAndNumber[1]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfTempContactNumbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivContactPic;
        private TextView tvContactName;
        private LinearLayout llContactNumbers;

        public ViewHolder(View itemView) {
            super(itemView);

            ivContactPic = itemView.findViewById(R.id.ivContactPic);
            tvContactName = itemView.findViewById(R.id.tvContactName);
            llContactNumbers = itemView.findViewById(R.id.llContactNumbers);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getLayoutPosition());
        }
    }
}
