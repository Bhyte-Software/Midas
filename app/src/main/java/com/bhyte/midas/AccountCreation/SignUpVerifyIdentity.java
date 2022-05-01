package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.R;
import com.bhyte.midas.User.UserHomeFragment;
import com.google.android.material.button.MaterialButton;
import com.passbase.passbase_sdk.PassbaseSDK;
import com.passbase.passbase_sdk.PassbaseSDKListener;

public class SignUpVerifyIdentity extends AppCompatActivity {

    public static String verificationStatus;
    String key;
    RelativeLayout nationalID, passport;
    MaterialButton skipButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_verify_identity);

        // Passbase SDK
        PassbaseSDK passbaseRef = new PassbaseSDK(this);
        passbaseRef.initialize("ka1pOP4fhtesbFKkSTFt0bgVC5r0MViPYMPsXIVPDL4PdYrU7Z9uanYSwhyO2InY");

        // Passbase Callbacks
        passbaseRef.callback(new PassbaseSDKListener() {
            @Override
            public void onStart() {
                System.out.println("MainActivity onStart");
            }

            @Override
            public void onFinish(@Nullable String identityAccessKey) {
                System.out.println("MainActivity onFinish: " + identityAccessKey);
                // Success
                key = "no skip";
            }

            @Override
            public void onSubmitted(@Nullable String identityAccessKey) {
                System.out.println("MainActivity onSubmitted: " + identityAccessKey);
            }

            @Override
            public void onError(@NonNull String errorCode) {
                System.out.println("MainActivity onError: " + errorCode);
            }
        });

        //Hooks
        nationalID = findViewById(R.id.national_relative_layout);
        passport = findViewById(R.id.passport_relative_layout);
        key = UserHomeFragment.key;
        skipButton = findViewById(R.id.skip_button);

        if(key == null){
            skipButton.setVisibility(View.VISIBLE);
        }
        else if (key.equals("no skip")){
            skipButton.setVisibility(View.GONE);
        }

        // Initialize Verification
        nationalID.setOnClickListener(v -> passbaseRef.startVerification());
        passport.setOnClickListener(v -> passbaseRef.startVerification());

    }

    public void callSkip(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpCredentials.class));
        verificationStatus = "not verified";
    }
}