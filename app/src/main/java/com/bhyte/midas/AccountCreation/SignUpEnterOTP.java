package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
import com.google.firebase.auth.FirebaseUser;
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

    TextView verify_description;
    PinView pinView;
    MaterialButton verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enter_otp);

        // FirebaseAuth Instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Hooks
        verify_description = findViewById(R.id.verification_description);
        pinView = findViewById(R.id.pin_view);
        verifyButton = findViewById(R.id.verify);

        // Get Data from Previous Activity
        fullPhoneNumber = SignUpEnterNumber.fullPhoneNumber;
        smsNumber = SignUpEnterNumber.fullPhoneNumber;
        completeNumber = SignUpEnterNumber.completeNumber;

        // Delete Country Code
        fullPhoneNumber = fullPhoneNumber.substring(4);

        // Append
        verify_description.append(" " + "+(233)" + fullPhoneNumber);

        // Send Code
        sendVerificationCode(smsNumber);

        verifyButton.setOnClickListener(v -> {
            if (pinView.getText() == null) {
                Toast toast = Toast.makeText(SignUpEnterOTP.this, R.string.enter_sms, Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpEnterOTP.this, R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(SignUpEnterOTP.this, R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            } else if (pinView.length() == 6) {
                // Verify Code
                String code = pinView.getText().toString().trim();
                verifyCode(code);
            }
        });

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
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            firebaseUser.delete().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    // if the code is correct and the task is successful
                                    // we are sending our user to new activity after deleting phone auth data from firebase
                                    Intent i = new Intent(SignUpEnterOTP.this, SignUpBirthdate.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                    } else {
                        //verification unsuccessful.. display an error message
                        showError();
                    }
                });
    }

    private void showError() {
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


