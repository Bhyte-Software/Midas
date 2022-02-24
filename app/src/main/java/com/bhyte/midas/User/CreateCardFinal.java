package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bhyte.midas.Common.TermsOfService;
import com.bhyte.midas.R;

public class CreateCardFinal extends AppCompatActivity {

    public String chosenColor;
    RelativeLayout cardLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_final);

        // Hooks
        chosenColor = VirtualCardChooseDesign.chosenColor;
        cardLayout = findViewById(R.id.card);

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

    public void callProcessingCardCreation(View view) {
        startActivity(new Intent(getApplicationContext(), ProcessingCardCreation.class));
    }

    public void callTermsConditions(View view) {
        startActivity(new Intent(getApplicationContext(), TermsOfService.class));
    }
}