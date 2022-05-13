package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bhyte.midas.Common.NoInternet;
import com.bhyte.midas.R;
import com.bhyte.midas.Util.CheckInternetConnection;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;

public class GetStarted extends AppCompatActivity {

    LinearLayout background;
    TextView title, desc;
    MaterialButton createAccountButton, signInButton;
    FirebaseAnalytics firebaseAnalytics;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        // Error Fix for Check Internet Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Hooks
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        background = findViewById(R.id.bg);
        createAccountButton = findViewById(R.id.create_account_button);
        signInButton = findViewById(R.id.sign_in_button);

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

        // Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Click Listeners
        signInButton.setOnClickListener(v -> {
            if (CheckInternetConnection.isConnected(GetStarted.this)) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            } else {
                startActivity(new Intent(getApplicationContext(), NoInternet.class));
            }
        });
        createAccountButton.setOnClickListener(v -> {
            if (CheckInternetConnection.isConnected(GetStarted.this)) {
                startActivity(new Intent(getApplicationContext(), SignUpEnterNumber.class));
            } else {
                startActivity(new Intent(getApplicationContext(), NoInternet.class));
            }
        });
    }

    private void darkMode() {
        background.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dark_bg));
        // Change Text Colors
        title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        desc.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_light));
        //
        Drawable buttonDrawable = signInButton.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable, getColor(R.color.dark_background));
        signInButton.setBackground(buttonDrawable);
        //
    }

    private void lightMode() {
        background.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_gradient));
        title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
    }
    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(0);
        } else {
            // Make Custom Toast Instead
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                Toast toast = Toast.makeText(GetStarted.this, "Press back again to exit midas", Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                TextView textView = layout.findViewById(R.id.text);
                textView.setText(R.string.back_to_exit);

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        }
        pressedTime = System.currentTimeMillis();
    }

    public void callBirthday(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpComplete.class));
    }
}