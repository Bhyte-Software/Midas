package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.AccountCreation.SignIn;
import com.bhyte.midas.R;

public class SignInWithFingerprint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_with_fingerprint);
    }

    public void callSignIn(View view) {
        startActivity(new Intent(getApplicationContext(), SignIn.class));
    }
}