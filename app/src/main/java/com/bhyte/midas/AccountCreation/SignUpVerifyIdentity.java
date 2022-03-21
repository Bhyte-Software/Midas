package com.bhyte.midas.AccountCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;
import com.bhyte.midas.User.UserHomeFragment;
import com.google.android.material.button.MaterialButton;

public class SignUpVerifyIdentity extends AppCompatActivity {

    String key;
    MaterialButton skipButton;

    public static String verificationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_verify_identity);

        //Hooks
        key = UserHomeFragment.key;
        skipButton = findViewById(R.id.skip_button);

        if(key.equals("no skip")){
            skipButton.setVisibility(View.GONE);
        }

    }

    public void callSkip(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpCredentials.class));
        verificationStatus = "not verified";
    }
}