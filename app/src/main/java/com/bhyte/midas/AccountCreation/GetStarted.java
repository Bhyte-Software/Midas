package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.Common.NoInternet;
import com.bhyte.midas.R;
import com.bhyte.midas.Util.CheckInternetConnection;
import com.google.android.material.button.MaterialButton;

public class GetStarted extends AppCompatActivity {
    private long pressedTime;
    MaterialButton createAccountButton, signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        // Error Fix for Check Internet Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Hooks
        createAccountButton = findViewById(R.id.create_account_button);
        signInButton = findViewById(R.id.sign_in_button);

        // Click Listeners
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternetConnection.isConnected(GetStarted.this)) {
                    startActivity(new Intent(getApplicationContext(), SignIn.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), NoInternet.class));
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternetConnection.isConnected(GetStarted.this)) {
                    startActivity(new Intent(getApplicationContext(), SignUpEnterNumber.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), NoInternet.class));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            // Make Custom Toast Instead
            Toast toast = Toast.makeText(GetStarted.this, "Press back again to exit midas", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
        }
        pressedTime = System.currentTimeMillis();
    }

}