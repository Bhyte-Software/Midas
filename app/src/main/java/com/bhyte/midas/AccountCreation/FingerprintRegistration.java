package com.bhyte.midas.AccountCreation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;

public class FingerprintRegistration extends AppCompatActivity {

    RelativeLayout bg;
    ProgressBar progressBar;
    TextView progressBarStatus, dataText, title, desc;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_registration);

        // Hooks
        bg = findViewById(R.id.bg);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.title_description);
        imageView = findViewById(R.id.fingerprint_icon);
        dataText = findViewById(R.id.data_text);
        progressBar = findViewById(R.id.progress);
        progressBarStatus = findViewById(R.id.registration_status);

        // Switch Theme Based on Mode
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                darkMode();
                break;

            case Configuration.UI_MODE_NIGHT_NO | Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;

        }

    }

    private void darkMode() {
        bg.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dark_bg));
        // Change Text Colors
        title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        desc.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_light));
        progressBarStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_light));
        dataText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_light));
    }

    public void callVerifyIdentity(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpVerifyIdentity.class));
    }
}