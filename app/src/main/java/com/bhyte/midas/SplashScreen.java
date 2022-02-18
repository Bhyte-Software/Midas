package com.bhyte.midas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.Common.OnBoarding;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_TIMER = 4000;

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {

            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
            boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

            // Check if it's users first time
            if (isFirstTime) {

                SharedPreferences.Editor editor = onBoardingScreen.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent(getApplicationContext(), GetStarted.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);

    }
}