package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.R;

import java.util.Calendar;

public class SignUpBirthdate extends AppCompatActivity {

    DatePicker datePicker;
    public static int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_birthdate);

        // Hooks
        datePicker = findViewById(R.id.birthday_picker);

    }

    public void callNext(View view) {
        // Get Birthdate
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
            Toast toast = Toast.makeText(SignUpBirthdate.this, "Sorry you are not eligible to Sign Up", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
            return false;
        } else
            return true;
    }

}