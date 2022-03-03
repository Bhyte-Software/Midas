package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.R;

public class ChangePasswordOpenEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_open_email);
    }

    public void callBack(View view) {
        finish();
    }

    public void openEmailApp(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            this.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            // Custom Toast
            Toast toast = Toast.makeText(ChangePasswordOpenEmail.this, "There is no email client installed.", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));
            toast.show();
        }
    }

    public void callEnterNew(View view) {
        startActivity(new Intent(getApplicationContext(), ChangePasswordCreateNewPassword.class));
    }
}