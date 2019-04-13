package com.payz24.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.payz24.activities.DomesticFlightsSearchRoundTripActivity;
import com.payz24.fragment.DomesticFlightsSearchRoundTripDepartFragment;
import com.payz24.fragment.DomesticFlightsSearchRoundTripReturnFragment;
import com.payz24.utils.TabInfo;

import java.util.ArrayList;

/**
 * Created by mahesh on 2/13/2018.
 */

public class DomesticFlightsSearchRoundTripTabsAdapter extends FragmentStatePagerAdapter implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivReturnFilter,ivDepartFilter;
    private final ArrayList<TabInfo> mTabs = new ArrayList<>();

    public DomesticFlightsSearchRoundTripTabsAdapter(Context context, FragmentManager supportFragmentManager, TabLayout tabLayout, ViewPager viewPager, ImageView ivDepartFilter, ImageView ivReturnFilter) {
        super(supportFragmentManager);
        this.context = context;
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        this.ivReturnFilter = ivReturnFilter;
        this.ivDepartFilter = ivDepartFilter;
        this.viewPager.setAdapter(this);
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    public void addTab(TabLayout.Tab tabSpec, Class<?> clss, Bundle args) {
        String tag = (String) tabSpec.getTag();
        TabInfo info = new TabInfo(tag, clss, args);
        mTabs.add(info);
        tabLayout.addTab(tabSpec);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = mTabs.get(position);
        Fragment fragment = Fragment.instantiate(context, info.clss.getName(), info.args);
        if(fragment instanceof DomesticFlightsSearchRoundTripDepartFragment){
            ((DomesticFlightsSearchRoundTripDepartFragment) fragment).viewPager = viewPager;
            ((DomesticFlightsSearchRoundTripDepartFragment) fragment).tabLayout = tabLayout;
            ((DomesticFlightsSearchRoundTripDepartFragment) fragment).ivDepartFilter = ivDepartFilter;
            ((DomesticFlightsSearchRoundTripDepartFragment) fragment).ivReturnFilter = ivReturnFilter;
        } else  if(fragment instanceof DomesticFlightsSearchRoundTripReturnFragment){
            ((DomesticFlightsSearchRoundTripReturnFragment) fragment).tabLayout = tabLayout;
            ((DomesticFlightsSearchRoundTripReturnFragment) fragment).ivDepartFilter = ivDepartFilter;
            ((DomesticFlightsSearchRoundTripReturnFragment) fragment).ivReturnFilter = ivReturnFilter;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        String tabName = (String) tab.getTag();
        if(position == 1){
            ivDepartFilter.setVisibility(View.GONE);
            ivReturnFilter.setVisibility(View.VISIBLE);
        }else {
            ivDepartFilter.setVisibility(View.VISIBLE);
            ivReturnFilter.setVisibility(View.GONE);
        }
        assert tabName != null;
        //tabLayout.setTabTextColors(Color.BLACK, context.getResources().getColor(R.color.light_blue));
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        String tabName = (String) tab.getTag();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
