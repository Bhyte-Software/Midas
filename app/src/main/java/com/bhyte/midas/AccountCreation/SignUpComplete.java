package com.bhyte.midas.AccountCreation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class SignUpComplete extends AppCompatActivity {

    MaterialButton hoorayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_complete);

        // Hooks
        hoorayButton = findViewById(R.id.button);

        // Click Listeners
        hoorayButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignIn.class)));

    }
}