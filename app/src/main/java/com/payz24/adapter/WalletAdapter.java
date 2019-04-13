package com.payz24.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.NfcEvent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payz24.R;
import com.payz24.utils.WalletItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<WalletItem> feedItems;
    Fragment fragment;
    WalletItem item;


    public WalletAdapter(Activity activity, List<WalletItem> feedItems, Fragment fragment) {
        this.activity = activity;
        this.feedItems = feedItems;
        this.fragment=fragment;
    }



    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_wallet, null);



        item = feedItems.get(position);

        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        desc.setText(item.getDescription());
        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        TextView dateTime = (TextView) convertView.findViewById(R.id.datetime);
        amount.setText(activity.getResources().getString(R.string.Rs)+" "+item.getTransactionamount());
        String str = item.getDate();
        String[] splited = str.split("\\s+");

        dateTime.setText(splited[0]);




        return convertView;
    }




}
