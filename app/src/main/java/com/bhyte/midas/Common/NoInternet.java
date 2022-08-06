package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.R;

public class NoInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
    }

    public void callSettings(View view) {
        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    public void callGetStarted(View view) {
        startActivity(new Intent(getApplicationContext(), GetStarted.class));
    }
}