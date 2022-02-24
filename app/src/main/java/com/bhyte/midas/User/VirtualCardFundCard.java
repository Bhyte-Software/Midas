package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bhyte.midas.R;

public class VirtualCardFundCard extends AppCompatActivity {

    public String chosenColor;
    RelativeLayout cardLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_fund_card);

        // Hooks
        cardLayout = findViewById(R.id.card);

        // Data from Previous Activity
        chosenColor = VirtualCardChooseDesign.chosenColor;

        // Update Card Background
        switch (chosenColor) {
            case "Black":
                cardLayout.setBackground(getResources().getDrawable(R.drawable.black_card));
                break;
            case "Blue":
                cardLayout.setBackground(getResources().getDrawable(R.drawable.blue_card));
                break;
            case "Red":
                cardLayout.setBackground(getResources().getDrawable(R.drawable.red_card));
                break;
            case "Yellow":
                cardLayout.setBackground(getResources().getDrawable(R.drawable.yellow_card));
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