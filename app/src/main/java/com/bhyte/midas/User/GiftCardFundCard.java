package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhyte.midas.R;

public class GiftCardFundCard extends AppCompatActivity {

    String chosenColor;
    String todayDate;

    RelativeLayout cardLayout;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_fund_card);

        // Hooks
        cardLayout = findViewById(R.id.card);
        date = findViewById(R.id.date);

        // Data from Previous Activity
        chosenColor = GiftCardChooseDesign.chosenColor;
        todayDate = GiftCardChooseLabel.dateToday;

        // Update Card Background
        switch (chosenColor) {
            case "Black":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardFundCard.this, R.drawable.black_card_gift));
                break;
            case "Blue":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardFundCard.this, R.drawable.blue_card_gift));
                break;
            case "Red":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardFundCard.this, R.drawable.red_card_gift));
                break;
            case "Green":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardFundCard.this, R.drawable.green_card_gift));
                break;
        }

        // Update Date
        date.setText(todayDate);

    }

    public void callGiftCardDetails(View view) {
        startActivity(new Intent(getApplicationContext(), GiftCardAddDetails.class));
    }

    public void callBack(View view) {
        finish();
    }
}