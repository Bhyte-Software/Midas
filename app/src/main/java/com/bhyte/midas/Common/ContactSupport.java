package com.bhyte.midas.Common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.R;

public class ContactSupport extends AppCompatActivity {

    RelativeLayout phoneLayout, contactChatBot;
    LinearLayout emailLayout;
    LinearLayout websiteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);

        // Hooks
        websiteLayout = findViewById(R.id.website_layout);
        phoneLayout = findViewById(R.id.phone_layout);
        emailLayout = findViewById(R.id.email_layout);
        contactChatBot = findViewById(R.id.contact_chat_bot);

        // Click Listeners
        contactChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatBot.class));
            }
        });
        websiteLayout.setOnClickListener(v -> openWebsite());
        phoneLayout.setOnClickListener(v -> callPhone());
        emailLayout.setOnClickListener(v -> callEmail());
    }

    private void openWebsite() {
        Uri uri = Uri.parse("https://www.usemidas.app");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+233202280564"));
        startActivity(intent);
    }

    public void callEmail() {
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

    public void finish(View view) {
        finish();
    }
}