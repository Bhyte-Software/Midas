package com.bhyte.midas.User;

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

    RelativeLayout cardOne, cardTwo, cardThree, cardFour;
    ImageView cardImage;
    MaterialButton nextButton;

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
        nextButton = findViewById(R.id.next);

        nextButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), GiftCardChooseLabel.class)));

        // Default Chosen Color
        chosenColor = "Red";

        checkChosenDesign();
    }

    private void checkChosenDesign() {
        cardOne.setOnClickListener(v -> {
            cardImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_red));
            chosenColor = "Red";
        });
        cardTwo.setOnClickListener(v -> {
            cardImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_blue));
            chosenColor = "Blue";
        });
        cardThree.setOnClickListener(v -> {
            cardImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_green));
            chosenColor = "Green";
        });
        cardFour.setOnClickListener(v -> {
            cardImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gift_card_black));
            chosenColor = "Black";
        });
    }

    public void callBack(View view) {
        finish();
    }
}