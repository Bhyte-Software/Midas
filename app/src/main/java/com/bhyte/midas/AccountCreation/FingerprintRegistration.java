package com.bhyte.midas.AccountCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class FingerprintRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_registration);
    }

    public void callVerifyIdentity(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpVerifyIdentity.class));
    }
}