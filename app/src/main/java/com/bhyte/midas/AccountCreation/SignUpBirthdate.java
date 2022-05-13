package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;

import java.lang.reflect.Field;
import java.util.Calendar;

public class SignUpBirthdate extends AppCompatActivity {

    public static int age;
    DatePicker datePicker;

    RelativeLayout bg, ripple;
    TextView title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_birthdate);

        // Hooks
        datePicker = findViewById(R.id.birthday_picker);
        bg = findViewById(R.id.bg);
        ripple = findViewById(R.id.ripple);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.description);

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
        ripple.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ripple_round_box_dark));
    }

    public void callNext(View view) {
        // Call Next Activity
        if (!validateAge()) {
            return;
        }

        startActivity(new Intent(getApplicationContext(), SignUpEnableFingerprint.class));
        finish();
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        age = currentYear - userAge;

        if (age < 18) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                Toast toast = Toast.makeText(SignUpBirthdate.this, R.string.ineligible, Toast.LENGTH_LONG);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpBirthdate.this, R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(SignUpBirthdate.this, R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();

            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                TextView textView = layout.findViewById(R.id.text);
                textView.setText(R.string.ineligible);

                Toast toast = new Toast(SignUpBirthdate.this);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            }
            return false;
        } else
            return true;
    }

}