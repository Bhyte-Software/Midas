package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhyte.midas.R;

public class GiftCardAddDetails extends AppCompatActivity {

    String chosenColor;
    RelativeLayout cardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_add_details);

        // Hooks
        cardLayout = findViewById(R.id.card);

        // Data from Previous Activity
        chosenColor = GiftCardChooseDesign.chosenColor;

        // Update Card Background
        switch (chosenColor) {
            case "Black":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardAddDetails.this, R.drawable.gift_card_black));
                break;
            case "Blue":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardAddDetails.this, R.drawable.gift_card_blue));
                break;
            case "Red":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardAddDetails.this, R.drawable.gift_card_red));
                break;
            case "Green":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardAddDetails.this, R.drawable.gift_card_green));
                break;
        }

    }

    public void callCreateCardFinal(View view) {
        startActivity(new Intent(getApplicationContext(), CreateGiftCardFinal.class));
    }

    public void callBack(View view) {
        finish();
    }
}