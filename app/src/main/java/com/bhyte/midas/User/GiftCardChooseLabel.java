package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhyte.midas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GiftCardChooseLabel extends AppCompatActivity {

    public String chosenColor;
    public static String dateToday;

    RelativeLayout cardLayout;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_choose_label);

        // Hooks
        cardLayout = findViewById(R.id.card);
        date = findViewById(R.id.date);

        // Data from Previous Activity
        chosenColor = GiftCardChooseDesign.chosenColor;

        // Update Card Background
        switch (chosenColor) {
            case "Black":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardChooseLabel.this, R.drawable.black_card_gift));
                break;
            case "Blue":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardChooseLabel.this, R.drawable.blue_card_gift));
                break;
            case "Red":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardChooseLabel.this, R.drawable.red_card_gift));
                break;
            case "Green":
                cardLayout.setBackground(ContextCompat.getDrawable(GiftCardChooseLabel.this, R.drawable.green_card_gift));
                break;
        }

        // Update Date
        updateDate();

    }

    private void updateDate() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());

        date.setText(currentDate);
        dateToday = currentDate;
    }

    public void callFundGiftCard(View view) {
        startActivity(new Intent(getApplicationContext(), GiftCardFundCard.class));
    }

    public void callBack(View view) {
        finish();
    }
}