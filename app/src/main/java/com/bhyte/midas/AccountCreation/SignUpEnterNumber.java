package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.R;
import com.bhyte.midas.Util.Common;

public class SignUpEnterNumber extends AppCompatActivity {

    EditText enterNumberLayout;
    public String countryCode;
    public String phoneNumber;
    public int lengthOfVal;
    public static String fullPhoneNumber;
    public static String completeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enter_number);

        // Hooks
        enterNumberLayout = findViewById(R.id.input_number_field);

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        checkInputLayout();
    }

    public void checkInputLayout() {
        phoneNumber = enterNumberLayout.getText().toString().trim();
        lengthOfVal = phoneNumber.length();

        if (lengthOfVal == 10) {
            enterNumberLayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ghana_icon, 0, R.drawable.green_tick, 0);
            // Hide Keyboard
            Common.hideKeyboard(SignUpEnterNumber.this);
        } else {
            enterNumberLayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ghana_icon, 0, 0, 0);
            // Show Keyboard
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void getCode(View view) {

        if (lengthOfVal == 10) {

            countryCode = "233";
            completeNumber = phoneNumber.substring(1);
            fullPhoneNumber = "+" + countryCode + completeNumber;

            startActivity(new Intent(getApplicationContext(), SignUpEnterOTP.class));
            finish();

        } else if (lengthOfVal == 0) {
            Toast toast = Toast.makeText(SignUpEnterNumber.this, "Please enter your number to continue", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();

        } else {
            Toast toast = Toast.makeText(SignUpEnterNumber.this, "Please enter a valid 10 digit number e.g 0202280564", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
        }
    }

}