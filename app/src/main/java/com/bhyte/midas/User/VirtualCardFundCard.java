package com.bhyte.midas.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;

public class VirtualCardFundCard extends AppCompatActivity {

    public String chosenColor;

    Animation animation;
    String fullName, currentDate;
    TextView cardName, date;
    RelativeLayout cardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_fund_card);

        // Hooks
        cardLayout = findViewById(R.id.card);
        date = findViewById(R.id.date);
        currentDate = VirtualCardChooseLabel.dateToday;
        cardName = findViewById(R.id.card_name);
        fullName = VirtualCardChooseLabel.userFullName;

        // Data from Previous Activity
        chosenColor = VirtualCardChooseDesign.chosenColor;

        // Update CardHolderName
        cardName.setText(fullName);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_animation);
        cardName.setAnimation(animation);

        // Update Date
        date.setText(currentDate);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_animation);
        date.setAnimation(animation);

        // Update Card Background
        switch (chosenColor) {
            case "Black":
                cardLayout.setBackground(ContextCompat.getDrawable(VirtualCardFundCard.this, R.drawable.black_card));
                break;
            case "Blue":
                cardLayout.setBackground(ContextCompat.getDrawable(VirtualCardFundCard.this, R.drawable.blue_card));
                break;
            case "Red":
                cardLayout.setBackground(ContextCompat.getDrawable(VirtualCardFundCard.this, R.drawable.red_card));
                break;
            case "Yellow":
                cardLayout.setBackground(ContextCompat.getDrawable(VirtualCardFundCard.this, R.drawable.yellow_card));
                break;
        }

    }

    public void callBack(View view) {
        finish();
    }

    public void callCreateCardFinal(View view) {
        startActivity(new Intent(getApplicationContext(), CreateCardFinal.class));
    }
}