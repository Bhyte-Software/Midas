package com.bhyte.midas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.User.VirtualCardDetails;

public class UserCards extends AppCompatActivity {

    CardView cardOne, cardTwo, cardThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cards);

        // Hooks
        cardOne = findViewById(R.id.card1).findViewById(R.id.card);
        cardTwo = findViewById(R.id.card2).findViewById(R.id.card);
        cardTwo = findViewById(R.id.card3).findViewById(R.id.card);

    }

    public void callCardDetails(View view) {
        startActivity(new Intent(getApplicationContext(), VirtualCardDetails.class));
    }
}