package com.payz24.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.payz24.R;
import com.payz24.adapter.ViewPlansTabsAdapter;
import com.payz24.fragment.FullTalkTimeFragment;
import com.payz24.fragment.RateCutterFragment;
import com.payz24.fragment.RoamingFragment;
import com.payz24.fragment.SMSFragment;
import com.payz24.fragment.ThreeGFourGFragment;
import com.payz24.fragment.TopUpFragment;
import com.payz24.fragment.TwoGFragment;

/**
 * Created by mahesh on 1/18/2018.
 */

public class ViewPlansActivity extends BaseActivity{

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private String circleName;
    private String serviceProviderName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_plans);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeTabs();
    }

    private void getDataFromIntent() {
        circleName = getIntent().getStringExtra(getString(R.string.circle_name));
        serviceProviderName = getIntent().getStringExtra(getString(R.string.service_provider_name));
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.view_plans));
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout);
        frameLayout = findViewById(R.id.detailsFragment);
    }

    private void initializeTabs() {
        ViewPlansTabsAdapter viewPlansTabsAdapter = new ViewPlansTabsAdapter(this,getSupportFragmentManager(),tabLayout,R.id.detailsFragment,circleName, serviceProviderName);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.full_talk_time)).setTag(getResources().getString(R.string.full_talk_time)),FullTalkTimeFragment.class,null);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.top_up)).setTag(getResources().getString(R.string.top_up)),TopUpFragment.class,null);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.three_four_g)).setTag(getResources().getString(R.string.three_four_g)),ThreeGFourGFragment.class,null);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.two_g)).setTag(getResources().getString(R.string.two_g)),TwoGFragment.class,null);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sms)).setTag(getResources().getString(R.string.sms)),SMSFragment.class,null);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.rate_cutter)).setTag(getResources().getString(R.string.rate_cutter)),RateCutterFragment.class,null);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.roaming)).setTag(getResources().getString(R.string.roaming)),RoamingFragment.class,null);
    }

}
