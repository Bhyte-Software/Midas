package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class ChangePasswordEnterEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_enter_email);
    }

    public void callSendInstructions(View view) {
        startActivity(new Intent(getApplicationContext(), ChangePasswordOpenEmail.class));
    }

    public void callClose(View view) {
        finish();
    }
}