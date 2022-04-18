package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhyte.midas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VirtualCardChooseLabel extends AppCompatActivity {

    public String chosenColor;
    public static String userFullName;
    public static String dateToday;

    EditText nameInputLayout;
    String fullName;
    TextView date;
    RelativeLayout cardLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_choose_label);

        // Hooks
        cardLayout = findViewById(R.id.card);
        date = findViewById(R.id.date);
        nameInputLayout = findViewById(R.id.name_input_layout);

        // Data from Previous Activity
        chosenColor = VirtualCardChooseDesign.chosenColor;

        // Update Date
        updateDate();

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

    private void updateDate() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());

        date.setText(currentDate);
        dateToday = currentDate;
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        checkInputLayout();
    }

    private void checkInputLayout() {

        // Display check icon at end of EditText if user inputs whitespace
        if (!nameInputLayout.getText().toString().matches("\\S+")) {
            nameInputLayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user_icon, 0, R.drawable.green_tick, 0);
        }

    }

    public void callBack(View view) {
        finish();
    }

    public void callFundVirtualCard(View view) {

        if (!validateFullName()) {
            return;
        }

        // Get Data from EditText
        fullName = nameInputLayout.getText().toString();
        userFullName = fullName;

        startActivity(new Intent(getApplicationContext(), VirtualCardFundCard.class));
        finish();
    }

    private boolean validateFullName() {
        String val = nameInputLayout.getText().toString().trim();
        String check_spaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            nameInputLayout.setError("Field cannot be empty!");
            return false;
        } else if (val.matches(check_spaces)) {
            nameInputLayout.setError("Enter your full name!");
            return false;
        } else {
            nameInputLayout.setError(null);
            return true;
        }
    }
}