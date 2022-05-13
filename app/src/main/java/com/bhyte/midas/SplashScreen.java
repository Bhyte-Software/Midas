package com.bhyte.midas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.Common.OnBoarding;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    RelativeLayout background;
    private static final int SPLASH_TIMER = 4000;
    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        // Hooks
        background = findViewById(R.id.background);

        // Switch Theme Based on Mode
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                darkMode();
                break;

            case Configuration.UI_MODE_NIGHT_NO | Configuration.UI_MODE_NIGHT_UNDEFINED:
                lightMode();
                break;

        }

        new Handler().postDelayed(() -> {

            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
            boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

            // Check if it's users first time
            if (isFirstTime) {

                SharedPreferences.Editor editor = onBoardingScreen.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(getApplicationContext(), GetStarted.class);
                startActivity(intent);
            }
            finish();
        }, SPLASH_TIMER);

    }

    private void darkMode() {
        background.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dark_bg));
    }

    private void lightMode() {
        background.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_gradient));
    }
}