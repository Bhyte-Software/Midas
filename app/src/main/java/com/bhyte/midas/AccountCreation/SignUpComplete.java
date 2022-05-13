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
    LinearLayout bg;
    TextView title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_complete);

        // Hooks
        bg = findViewById(R.id.bg);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.description);
        hoorayButton = findViewById(R.id.button);

        // Switch Theme Based on Mode
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                darkMode();
                break;

            case Configuration.UI_MODE_NIGHT_NO | Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;

        }

        // Click Listeners
        hoorayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            }
        });

    }

    private void darkMode() {
        bg.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dark_bg));
        // Change Text Colors
        title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        desc.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_light));
    }

}