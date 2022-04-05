package com.bhyte.midas.AccountCreation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.Common.NoInternet;
import com.bhyte.midas.R;
import com.bhyte.midas.Util.CheckInternetConnection;

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
        if (isNetworkConnected()) {
            startActivity(new Intent(getApplicationContext(), SignUpEnterNumber.class));
        } else {
            startActivity(new Intent(getApplicationContext(), NoInternet.class));
        }
    }

    public void callSignIn(View view) {
        if (isNetworkConnected()) {
            startActivity(new Intent(getApplicationContext(), SignIn.class));
        } else {
            startActivity(new Intent(getApplicationContext(), NoInternet.class));
        }    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}