package com.payz24.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payz24.R;

/**
 * Created by mahesh on 1/28/2018.
 */

public class SeatLegendsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_seat_legend_fragment,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
