package com.bhyte.midas.AccountCreation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.Common.NoInternet;
import com.bhyte.midas.R;
import com.bhyte.midas.Util.CheckInternetConnection;
import com.bhyte.midas.Util.Common;
import com.google.android.material.button.MaterialButton;

public class SignUpEnterNumber extends AppCompatActivity {

    public static String fullPhoneNumber;
    public static String completeNumber;
    public String countryCode;
    public String phoneNumber;
    public int lengthOfVal;
    public Context context;
    EditText enterNumberLayout;
    MaterialButton getCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enter_number);

        // Error Fix for Check Internet Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Hooks
        enterNumberLayout = findViewById(R.id.input_number_field);
        getCode = findViewById(R.id.get_code);

        getCode.setOnClickListener(v -> {
            if (CheckInternetConnection.isConnected(SignUpEnterNumber.this)) {
                getCode();
            } else {
                startActivity(new Intent(getApplicationContext(), NoInternet.class));
            }
        });

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

    @SuppressLint("ObsoleteSdkInt")
    public void getCode() {

        if (lengthOfVal == 10) {
            countryCode = "233";
            completeNumber = phoneNumber.substring(1);
            fullPhoneNumber = "+" + countryCode + completeNumber;

            startActivity(new Intent(getApplicationContext(), SignUpEnterOTP.class));
            finish();

        } else if (lengthOfVal == 0) {
            // Custom Toast for Android Versions < 11
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                Toast toast = Toast.makeText(SignUpEnterNumber.this, "Please enter your number to continue", Toast.LENGTH_SHORT);
                LinearLayout layout = (LinearLayout) toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                layout.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = layout.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            }
            // Default Android Toast for android version 11
            else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
                TextView textView = (TextView) layout.findViewById(R.id.text);
                textView.setText("Please enter your number to continue");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 20);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();

            }
        } else {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                Toast toast = Toast.makeText(SignUpEnterNumber.this, "Please enter a valid 10 digit number e.g 0202280564", Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
                TextView textView = (TextView) layout.findViewById(R.id.text);
                textView.setText("Please enter a valid 10 digit number");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 20);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        }
    }

}