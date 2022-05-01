package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class GiftCardAddDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_add_details);
    }

    public void callCreateCardFinal(View view) {
        startActivity(new Intent(getApplicationContext(), CreateGiftCardFinal.class));
    }

    public void callBack(View view) {
        finish();
    }
}