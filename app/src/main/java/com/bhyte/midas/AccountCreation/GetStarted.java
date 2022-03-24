package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.R;

public class GetStarted extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), GetStarted.class));
    }

    public void callSignUpEnterNumber(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpEnterNumber.class));
    }

    public void callSignIn(View view) {
        startActivity(new Intent(getApplicationContext(), SignIn.class));
    }
}