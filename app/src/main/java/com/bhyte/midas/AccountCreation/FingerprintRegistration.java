package com.bhyte.midas.AccountCreation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;

public class FingerprintRegistration extends AppCompatActivity {

    private static final int SPLASH_TIMER = 2000;

    ProgressBar progressBar;
    TextView progressBarStatus, dataText;
    ImageView imageView;

    KeyguardManager keyguardManager;
    FingerprintManager fingerprintManager;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_registration);

        // Hooks
        imageView = findViewById(R.id.fingerprint_icon);
        dataText = findViewById(R.id.data_text);
        progressBar = findViewById(R.id.progress);
        progressBarStatus = findViewById(R.id.registration_status);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if (!fingerprintManager.isHardwareDetected()) {
                // Custom Toast
                Toast toast = Toast.makeText(FingerprintRegistration.this, "Fingerprint scanner not detected in device!", Toast.LENGTH_SHORT);
                View view1 = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.white));
                toast.show();
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // Custom Toast
                Toast toast = Toast.makeText(FingerprintRegistration.this, "Permission not granted to use fingerprint scanner!", Toast.LENGTH_SHORT);
                View view1 = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.white));
                toast.show();
            }  else if (!fingerprintManager.hasEnrolledFingerprints()) {
                // Custom Toast
                Toast toast = Toast.makeText(FingerprintRegistration.this, "You should add at least one fingerprint to register!", Toast.LENGTH_SHORT);
                View view1 = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.white));
                toast.show();
            } else {
                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.StartAuth(fingerprintManager, null);

                if(dataText.getText().toString().contains("You can now access the app")){
                    // Custom Toast
                    Toast toast = Toast.makeText(FingerprintRegistration.this, "Access Granted", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();
                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(getResources().getColor(R.color.light_green), PorterDuff.Mode.SRC_IN);
                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.white));
                    toast.show();

                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(getApplicationContext(), SignUpVerifyIdentity.class));
                        finish();
                    }, SPLASH_TIMER);

                } else {
                    // Custom Toast
                    Toast toast = Toast.makeText(FingerprintRegistration.this, dataText.getText().toString(), Toast.LENGTH_SHORT);
                    View view1 = toast.getView();
                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.white));
                    toast.show();
                }

            }
        }

    }

    public void callVerifyIdentity(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpVerifyIdentity.class));
    }
}