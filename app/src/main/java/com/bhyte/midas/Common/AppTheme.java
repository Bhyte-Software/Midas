package com.bhyte.midas.Common;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bhyte.midas.R;

public class AppTheme extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton darkThemeRadio, lightThemeRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_theme);

        // Hooks
        radioGroup = findViewById(R.id.radio_group);
        darkThemeRadio = findViewById(R.id.dark_theme);
        lightThemeRadio = findViewById(R.id.light_theme);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            lightThemeRadio.setChecked(true);
        }
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkThemeRadio.setChecked(true);
        }

        // RadioGroup Listener
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int i = radioGroup.getCheckedRadioButtonId();
            if (i == R.id.dark_theme) {
                // Dark Theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (i == R.id.light_theme) {
                // Light Theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Click Listeners
    }

    public void callSettings(View view) {
        finish();
    }
}