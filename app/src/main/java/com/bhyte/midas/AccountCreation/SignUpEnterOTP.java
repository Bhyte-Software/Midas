package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;
import com.chaos.view.PinView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpEnterOTP extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    public String verificationId;
    public String fullPhoneNumber;
    public String smsNumber;
    public String completeNumber;

    RelativeLayout bg, ripple;
    TextView verify_description, title;
    PinView pinView;
    MaterialButton verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enter_otp);

        // FirebaseAuth Instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Hooks
        ripple = findViewById(R.id.ripple);
        title = findViewById(R.id.title);
        bg = findViewById(R.id.bg);
        verify_description = findViewById(R.id.verification_description);
        pinView = findViewById(R.id.pin_view);
        verifyButton = findViewById(R.id.verify);

        // Switch Theme Based on Mode
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                darkMode();
                break;

            case Configuration.UI_MODE_NIGHT_NO | Configuration.UI_MODE_NIGHT_UNDEFINED:
                lightMode();
                break;

        }


        // Get Data from Previous Activity
        fullPhoneNumber = SignUpEnterNumber.fullPhoneNumber;
        smsNumber = SignUpEnterNumber.fullPhoneNumber;
        completeNumber = SignUpEnterNumber.completeNumber;

        // Delete Country Code
        fullPhoneNumber = fullPhoneNumber.substring(4);

        // Append
        verify_description.append(" " + "+ (233)" + fullPhoneNumber);

        sendVerificationCode(smsNumber);

        verifyButton.setOnClickListener(v -> {
            if (pinView.getText() == null) {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
                    Toast toast = Toast.makeText(SignUpEnterOTP.this, R.string.enter_sms, Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpEnterOTP.this, R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(SignUpEnterOTP.this, R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                }
                else{
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                    TextView textView = layout.findViewById(R.id.text);
                    textView.setText(R.string.enter_sms);

                    Toast toast = new Toast(SignUpEnterOTP.this);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            } else if (pinView.length() == 6) {
                // Verify Code
                String code = pinView.getText().toString().trim();
                verifyCode(code);
            }
        });

    }

    private void darkMode() {
        bg.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dark_bg));
        // Change Text Colors
        title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        verify_description.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_light));
        ripple.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ripple_round_box_dark));

        pinView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    }

    private void lightMode() {
    }


    private void sendVerificationCode(String smsNumber) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(smsNumber)         // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)         // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // if the code is correct and the task is successful
                        // we are sending our user to new activity.
                        Intent i = new Intent(SignUpEnterOTP.this, SignUpBirthdate.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        //verification unsuccessful.. display an error message
                        showError();
                    }
                });
    }

    private void showError() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
            Toast toast = Toast.makeText(SignUpEnterOTP.this, R.string.incorrect_code, Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpEnterOTP.this, R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(ContextCompat.getColor(SignUpEnterOTP.this, R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
        }
        else{
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
            TextView textView = layout.findViewById(R.id.text);
            textView.setText(R.string.incorrect_code);

            Toast toast = new Toast(SignUpEnterOTP.this);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

    }

    // callback method is called on Phone auth provider.
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                pinView.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verify code method.
                verifyCode(code);
            }
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }


        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            // displaying error message with firebase exception.
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
                Toast toast = Toast.makeText(SignUpEnterOTP.this, R.string.code_verification_error, Toast.LENGTH_LONG);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpEnterOTP.this, R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(SignUpEnterOTP.this, R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            }
            else{
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                TextView textView = layout.findViewById(R.id.text);
                textView.setText(R.string.code_verification_error);

                Toast toast = new Toast(SignUpEnterOTP.this);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}


