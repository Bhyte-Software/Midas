package com.bhyte.midas.AccountCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bhyte.midas.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpEnterNumber extends AppCompatActivity {

    TextInputLayout enterNumberLayout;
    public String countryCode;
    public String phoneNumber;
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

        phoneNumber = enterNumberLayout.getEditText().getText().toString().trim();
        int lengthOfVal = phoneNumber.length();

        if (lengthOfVal == 10) {
            enterNumberLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
            enterNumberLayout.setEndIconDrawable(R.drawable.green_tick);
        } else {
            enterNumberLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        }
    }

    public void getCode(View view) {
        countryCode = "233";
        completeNumber = phoneNumber.substring(1);
        fullPhoneNumber = "+" + countryCode + completeNumber;

        Intent otpIntent = new Intent(getApplicationContext(), SignUpEnterOTP.class);
        startActivity(otpIntent);
        finish();

    }
}