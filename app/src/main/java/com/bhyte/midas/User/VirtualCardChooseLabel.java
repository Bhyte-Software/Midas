package com.bhyte.midas.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VirtualCardChooseLabel extends AppCompatActivity {

    public static String userFullName;
    public static String dateToday;
    public String chosenColor;
    EditText nameInputLayout;
    String fullName;
    TextView date, nameOnCard;
    RelativeLayout cardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_choose_label);

        // Hooks
        cardLayout = findViewById(R.id.card);
        date = findViewById(R.id.date);
        nameOnCard = findViewById(R.id.card_name);
        nameInputLayout = findViewById(R.id.name_input_layout);

        // Data from Previous Activity
        chosenColor = VirtualCardChooseDesign.chosenColor;

        nameInputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nameInputLayout.getText().length() >= 0) {
                    nameOnCard.setText(nameInputLayout.getText().toString());
                    checkInputLayout();
                }
            }
        });

        // Update Date
        updateDate();

        // Update Card Background
        switch (chosenColor) {
            case "Black":
                cardLayout.setBackground(ContextCompat.getDrawable(VirtualCardChooseLabel.this, R.drawable.black_card));
                break;
            case "Blue":
                cardLayout.setBackground(ContextCompat.getDrawable(VirtualCardChooseLabel.this, R.drawable.blue_card));
                break;
            case "Red":
                cardLayout.setBackground(ContextCompat.getDrawable(VirtualCardChooseLabel.this, R.drawable.red_card));
                break;
            case "Yellow":
                cardLayout.setBackground(ContextCompat.getDrawable(VirtualCardChooseLabel.this, R.drawable.yellow_card));
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

    private void checkInputLayout() {

        // Display check icon at end of EditText if user inputs whitespace
        if (nameInputLayout.getText().toString().contains(" ") && nameInputLayout.getText().toString().length() >= 8) {
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