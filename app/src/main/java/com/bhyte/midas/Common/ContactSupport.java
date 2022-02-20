package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;
import com.bhyte.midas.User.UserSettingsFragment;

public class ContactSupport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);
    }

    public void callClose(View view) {
        startActivity(new Intent(getApplicationContext(), MainDashboard.class));
    }
}