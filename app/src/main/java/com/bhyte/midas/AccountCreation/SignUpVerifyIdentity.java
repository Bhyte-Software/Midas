package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;
import com.bhyte.midas.User.UserHomeFragment;
import com.google.android.material.button.MaterialButton;
import com.passbase.passbase_sdk.PassbaseSDK;
import com.passbase.passbase_sdk.PassbaseSDKListener;

public class SignUpVerifyIdentity extends AppCompatActivity {

    public static String verificationStatus;
    public static String passbaseIdentityAccessKey;

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

        // Passbase Callbacks
        passbaseRef.callback(new PassbaseSDKListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish(@Nullable String identityAccessKey) {
                key = "no skip";

                // Get Data from Passbase
                passbaseIdentityAccessKey = identityAccessKey;
            }

            @Override
            public void onSubmitted(@Nullable String identityAccessKey) {
                // Get Data from Passbase
                passbaseIdentityAccessKey = identityAccessKey;
            }

            @Override
            public void onError(@NonNull String errorCode) {
                // Custom Error Toast
                Toast toast = Toast.makeText(SignUpVerifyIdentity.this, errorCode, Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();

                // Restart Activity
                startActivity(new Intent(getApplicationContext(), SignUpVerifyIdentity.class));
            }
        });

        // Initialize Verification
        nationalID.setOnClickListener(v -> passbaseRef.startVerification());
        passport.setOnClickListener(v -> passbaseRef.startVerification());

    }

    public void callSkip(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpCredentials.class));
        verificationStatus = "not verified";
    }
}