package com.bhyte.midas.AccountCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bhyte.midas.R;

public class SignUpEnterOTP extends AppCompatActivity {

    public String fullPhoneNumber;
    public String completeNumber;
    TextView verify_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enter_otp);

        // Hooks
        verify_description = findViewById(R.id.verification_description);

        // Get Data from Previous Activity
        fullPhoneNumber = SignUpEnterNumber.fullPhoneNumber;
        completeNumber = SignUpEnterNumber.completeNumber;

        //
        fullPhoneNumber = fullPhoneNumber.substring(4);

        // Append
        verify_description.append(" " + "+ (233)" + " " + fullPhoneNumber);

    }

    public void callAddFingerprint(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpEnableFingerprint.class));
        finish();
    }
}