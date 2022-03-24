package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bhyte.midas.R;
import com.bhyte.midas.User.UserSettingsFragment;

public class ContactSupport extends AppCompatActivity {

    RelativeLayout phoneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);

        // Hooks
        phoneLayout = findViewById(R.id.phone_layout);

        phoneLayout.setOnClickListener(v -> callPhone());

    }

    private void callPhone() {
    }

    public void callClose(View view) {
        startActivity(new Intent(getApplicationContext(), MainDashboard.class));
    }

    public void callCLose(View view) {
        finish();
    }

    public void callEmail(View view) {
        String to, subject, message;
        to = "midas@gmail.com";
        message = "Please enter what you need help with";
        subject = "Need Help With Midas";
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}