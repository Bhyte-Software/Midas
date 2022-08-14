package com.bhyte.midas.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class GiftCardChooseDesign extends AppCompatActivity {

    public static String chosenColor;
    Context context;

    RelativeLayout cardOne, cardTwo, cardThree, cardFour;
    ImageView cardImage, red, blue, green, black;
    MaterialButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_choose_design);

        this.context = getApplicationContext();

        // Hooks
        cardOne = findViewById(R.id.card_one);
        cardTwo = findViewById(R.id.card_two);
        cardThree = findViewById(R.id.card_three);
        cardFour = findViewById(R.id.card_four);
        nextButton = findViewById(R.id.next);
        cardImage = findViewById(R.id.card_image);
        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        green = findViewById(R.id.green);
        black = findViewById(R.id.black);

        nextButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), GiftCardChooseLabel.class)));

        // Default Chosen Color
        chosenColor = "Red";

        checkChosenDesign();
    }

    private void checkChosenDesign() {
        cardOne.setOnClickListener(v -> {
            cardImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.g_card));
            red.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_card_small_selected));
            green.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_green_small));
            blue.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_blue_small));
            black.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_black_small));

            chosenColor = "Red";
        });
        cardTwo.setOnClickListener(v -> {
            cardImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_green));
            red.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_red_small));
            green.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.green_card_small_selected));
            blue.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_blue_small));
            black.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_black_small));

            chosenColor = "Green";
        });
        cardThree.setOnClickListener(v -> {
            cardImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_blue));
            red.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_red_small));
            green.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_green_small));
            blue.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blue_card_small_selected));
            black.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_black_small));

            chosenColor = "Blue";
        });
        cardFour.setOnClickListener(v -> {
            cardImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_black));
            red.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_red_small));
            green.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_green_small));
            blue.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_blue_small));
            black.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.black_card_small_selected));

            chosenColor = "Black";
        });
    }

    public void callBack(View view) {
        finish();
    }
}