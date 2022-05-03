package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhyte.midas.Common.TermsOfService;
import com.bhyte.midas.R;

public class CreateGiftCardFinal extends AppCompatActivity {

    String chosenColor;
    String todayDate;

    RelativeLayout cardLayoutOne, cardLayoutTwo;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_gift_card_final);

        // Hooks
        cardLayoutOne = findViewById(R.id.card);
        cardLayoutTwo = findViewById(R.id.card2);
        date = findViewById(R.id.date);

        // Data from previous Activity
        todayDate = GiftCardChooseLabel.dateToday;
        chosenColor = GiftCardChooseDesign.chosenColor;

        // Update Card Background
        switch (chosenColor) {
            case "Black":
                cardLayoutOne.setBackground(ContextCompat.getDrawable(CreateGiftCardFinal.this, R.drawable.black_card_gift));
                cardLayoutTwo.setBackground(ContextCompat.getDrawable(CreateGiftCardFinal.this, R.drawable.black_card_final));
                break;
            case "Blue":
                cardLayoutOne.setBackground(ContextCompat.getDrawable(CreateGiftCardFinal.this, R.drawable.blue_card_gift));
                cardLayoutTwo.setBackground(ContextCompat.getDrawable(CreateGiftCardFinal.this, R.drawable.blue_card_final));
                break;
            case "Red":
                cardLayoutOne.setBackground(ContextCompat.getDrawable(CreateGiftCardFinal.this, R.drawable.red_card_gift));
                cardLayoutTwo.setBackground(ContextCompat.getDrawable(CreateGiftCardFinal.this, R.drawable.red_card_final));
                break;
            case "Green":
                cardLayoutOne.setBackground(ContextCompat.getDrawable(CreateGiftCardFinal.this, R.drawable.green_card_gift));
                cardLayoutTwo.setBackground(ContextCompat.getDrawable(CreateGiftCardFinal.this, R.drawable.green_card_final));
                break;
        }

        // Update Date
        date.setText(todayDate);

    }

    public void callProcessingCardCreation(View view) {
        startActivity(new Intent(getApplicationContext(), ProcessingCardCreation.class));
    }

    public void callBack(View view) {
        finish();
    }

    public void callTermsConditions(View view) {
        startActivity(new Intent(getApplicationContext(), TermsOfService.class));
    }
}