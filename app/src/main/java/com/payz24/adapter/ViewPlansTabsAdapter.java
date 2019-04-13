package com.payz24.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.payz24.R;
import com.payz24.activities.ViewPlansActivity;
import com.payz24.fragment.FullTalkTimeFragment;
import com.payz24.fragment.RateCutterFragment;
import com.payz24.fragment.RoamingFragment;
import com.payz24.fragment.SMSFragment;
import com.payz24.fragment.ThreeGFourGFragment;
import com.payz24.fragment.TopUpFragment;
import com.payz24.fragment.TwoGFragment;
import com.payz24.utils.TabInfo;

import java.util.ArrayList;

/**
 * Created by mahesh on 1/18/2018.
 */

public class ViewPlansTabsAdapter implements TabLayout.OnTabSelectedListener {


    private Context context;
    private int frameLayout;
    private FragmentManager fragmentManager;
    private TabLayout tabLayout;
    private Fragment fragment;
    private String circleName = "";
    private String serviceProviderName = "";
    private final ArrayList<TabInfo> mTabs = new ArrayList<>();

    public ViewPlansTabsAdapter(Context context, FragmentManager fragmentManager, TabLayout tabLayout, int frameLayout, String circleName, String serviceProviderName) {
        this.context = context;
        this.tabLayout = tabLayout;
        this.circleName = circleName;
        this.frameLayout = frameLayout;
        this.fragmentManager = fragmentManager;
        this.serviceProviderName = serviceProviderName;
        tabLayout.addOnTabSelectedListener(this);
    }

    public void addTab(TabLayout.Tab tabSpec, Class<?> clss, Bundle args) {
        String tag = (String) tabSpec.getTag();
        TabInfo info = new TabInfo(tag, clss, args);
        mTabs.add(info);
        tabLayout.addTab(tabSpec);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        switch (position) {
            case 0:
                fragment = new FullTalkTimeFragment();
                break;
            case 1:
                fragment = new TopUpFragment();
                break;
            case 2:
                fragment = new ThreeGFourGFragment();
                break;
            case 3:
                fragment = new TwoGFragment();
                break;
            case 4:
                fragment = new SMSFragment();
                break;
            case 5:
                fragment = new RateCutterFragment();
                break;
            case 6:
                fragment = new RoamingFragment();
                break;
            default:
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.circle_name), circleName);
        bundle.putString(context.getString(R.string.service_provider_name), serviceProviderName);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
