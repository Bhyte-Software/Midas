package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class ChangePasswordCreateNewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_create_new_password);
    }

    public void callBack(View view) {
        finish();
    }

    public void callSuccess(View view) {
        startActivity(new Intent(getApplicationContext(), ChangePasswordSuccess.class));
    }
}