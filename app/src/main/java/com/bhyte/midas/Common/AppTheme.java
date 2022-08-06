package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class AppTheme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_theme);
    }

    public void callSettings(View view) {
        finish();
    }
}