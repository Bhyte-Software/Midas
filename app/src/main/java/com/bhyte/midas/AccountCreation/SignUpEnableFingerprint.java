package com.bhyte.midas.AccountCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class SignUpEnableFingerprint extends AppCompatActivity {

    public static String fingerprintStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enable_fingerprint);
    }

    public void callVerifyIdentity(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpVerifyIdentity.class));
        fingerprintStatus = "fingerprint not registered";
    }

    public void callContinue(View view) {
        // Continue to fingerprint registration screen
        fingerprintStatus = "fingerprint registered";
        startActivity(new Intent(getApplicationContext(), FingerprintRegistration.class));
        finish();
    }
}