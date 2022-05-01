package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class GiftCardFundCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_fund_card);
    }

    public void callGiftCardDetails(View view) {
        startActivity(new Intent(getApplicationContext(), GiftCardAddDetails.class));
    }

    public void callBack(View view) {
        finish();
    }
}