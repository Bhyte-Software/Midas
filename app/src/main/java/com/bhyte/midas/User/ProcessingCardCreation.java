package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.Common.OnBoarding;
import com.bhyte.midas.R;

public class ProcessingCardCreation extends AppCompatActivity {

    private static final int SPLASH_TIMER = 4000;
    public static String keyOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_processing_card_creation);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
            keyOne = "Cards";
            finish();
        }, SPLASH_TIMER);

    }
}