package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bhyte.midas.R;

public class GiftCardChooseDesign extends AppCompatActivity {

    RelativeLayout cardOne, cardTwo, cardThree, cardFour;
    ImageView cardImage, tickOne, tickTwo, tickThree, tickFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_choose_design);

        // Hooks
        cardOne = findViewById(R.id.card_one);
        cardTwo = findViewById(R.id.card_two);
        cardThree = findViewById(R.id.card_three);
        cardFour = findViewById(R.id.card_four);
        cardImage = findViewById(R.id.card_image);
        tickOne = findViewById(R.id.check_one);
        tickTwo = findViewById(R.id.check_two);
        tickThree = findViewById(R.id.check_three);
        tickFour = findViewById(R.id.check_four);

        checkChosenDesign();
    }

    private void checkChosenDesign() {

        cardOne.setOnClickListener(v -> {
            tickOne.setVisibility(View.VISIBLE);
            tickTwo.setVisibility(View.INVISIBLE);
            tickThree.setVisibility(View.INVISIBLE);
            tickFour.setVisibility(View.INVISIBLE);
            cardImage.setImageDrawable(getResources().getDrawable(R.drawable.gift_card_red));
        });

        cardTwo.setOnClickListener(v -> {
            tickOne.setVisibility(View.INVISIBLE);
            tickTwo.setVisibility(View.VISIBLE);
            tickThree.setVisibility(View.INVISIBLE);
            tickFour.setVisibility(View.INVISIBLE);
            cardImage.setImageDrawable(getResources().getDrawable(R.drawable.gift_card_blue));
        });

        cardThree.setOnClickListener(v -> {
            tickOne.setVisibility(View.INVISIBLE);
            tickTwo.setVisibility(View.INVISIBLE);
            tickThree.setVisibility(View.VISIBLE);
            tickFour.setVisibility(View.INVISIBLE);
            cardImage.setImageDrawable(getResources().getDrawable(R.drawable.gift_card_green));
        });

        cardFour.setOnClickListener(v -> {
            tickOne.setVisibility(View.INVISIBLE);
            tickTwo.setVisibility(View.INVISIBLE);
            tickThree.setVisibility(View.INVISIBLE);
            tickFour.setVisibility(View.VISIBLE);
            cardImage.setImageDrawable(getResources().getDrawable(R.drawable.gift_card_black));
        });

    }

    public void callBack(View view) {
        finish();
    }
}