package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class VirtualCardFundCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_fund_card);
    }

    public void callBack(View view) {
        finish();
    }

    public void callProcessingCardCreation(View view) {
        startActivity(new Intent(getApplicationContext(), ProcessingCardCreation.class));
    }
}