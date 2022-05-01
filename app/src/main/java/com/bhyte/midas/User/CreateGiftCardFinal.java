package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.Common.TermsOfService;
import com.bhyte.midas.R;

public class CreateGiftCardFinal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_gift_card_final);
    }

    public void callProcessingCardCreation(View view) {
    }

    public void callBack(View view) {
        finish();
    }

    public void callTermsConditions(View view) {
        startActivity(new Intent(getApplicationContext(), TermsOfService.class));
    }
}