package com.payz24.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cooltechworks.views.ScratchTextView;
import com.payz24.R;
import com.payz24.utils.Constants;

public class FullScratchCard extends BaseActivity {

    String price;
    ScratchTextView scratchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_scratch_card);
        getDataFromIntent();
        scratchTextView=(ScratchTextView) findViewById(R.id.titlePrice);

        if(scratchTextView != null) {
            scratchTextView.setRevealListener(new ScratchTextView.IRevealListener() {
                @Override
                public void onRevealed(ScratchTextView tv) {
                    scratchTextView.setText("You won\n "+price);
                }

                @Override
                public void onRevealPercentChangedListener(ScratchTextView scratchTextView, float v) {


                }
            });
        }

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            price = intent.getStringExtra("price");
        }
    }
}
