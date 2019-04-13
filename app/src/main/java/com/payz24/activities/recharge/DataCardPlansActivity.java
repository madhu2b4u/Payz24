package com.payz24.activities.recharge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.payz24.R;
import com.payz24.activities.BaseActivity;
import com.payz24.adapter.DataCardViewPlansTabsAdapter;
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

public class DataCardPlansActivity extends BaseActivity {

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
        DataCardViewPlansTabsAdapter viewPlansTabsAdapter = new DataCardViewPlansTabsAdapter(this,getSupportFragmentManager(),tabLayout,R.id.detailsFragment,circleName, serviceProviderName);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.three_four_g)).setTag(getResources().getString(R.string.three_four_g)),ThreeGFourGFragment.class,null);
        viewPlansTabsAdapter.addTab(tabLayout.newTab().setText(getResources().getString(R.string.two_g)).setTag(getResources().getString(R.string.two_g)),TwoGFragment.class,null);
    }

}
