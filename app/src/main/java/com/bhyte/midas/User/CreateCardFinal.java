package com.bhyte.midas.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.Common.TermsOfService;
import com.bhyte.midas.R;

public class CreateCardFinal extends AppCompatActivity {

    public String chosenColor;

    Context context;
    RelativeLayout cardLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_final);

        this.context = getApplicationContext();

        // Hooks
        chosenColor = VirtualCardChooseDesign.chosenColor;
        cardLayout = findViewById(R.id.card);

        // Update Card Background
        switch (chosenColor) {
            case "Black":
                cardLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.black_card));
                break;
            case "Blue":
                cardLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_card));
                break;
            case "Red":
                cardLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.red_card));
                break;
            case "Yellow":
                cardLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.yellow_card));
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