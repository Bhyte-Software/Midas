package com.bhyte.midas.User;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.R;
import com.bhyte.midas.Util.Common;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordEnterEmail extends AppCompatActivity {

    private static final int TIMER = 1500;

    EditText emailInputField;
    MaterialButton sendInstructions;
    public String userEmail;

    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_enter_email);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                // Do whatever you want with the UserId by firebaseUser.getUid()
                String userID = firebaseUser.getUid();
            }
        };

        // Hooks
        emailInputField = findViewById(R.id.input_email);
        sendInstructions = findViewById(R.id.send_instructions);

        sendInstructions.setOnClickListener(v -> startPasswordReset());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void startPasswordReset() {
        if(userEmail.contains("@gmail.com") | userEmail.contains("@yahoo.com") | userEmail.contains("@hotmail.com")){
            // Send Instructions
            sendPasswordResetEmail();
        } else if (emailInputField.getText().toString().trim().isEmpty()) {
            emailInputField.setError("Email cannot be empty!");
        } else if (!userEmail.contains("@gmail.com") | !userEmail.contains("@yahoo.com")){
            emailInputField.setError("Invalid Email!");
        }
    }

    private void sendPasswordResetEmail() {
        if(userEmail != null){
            mFirebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    // Success Toast & Next Activity
                    // Custom Toast
                    Toast toast = Toast.makeText(ChangePasswordEnterEmail.this, "A password reset email has been sent to you.", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(getResources().getColor(R.color.light_green), PorterDuff.Mode.SRC_IN);
                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.white));
                    toast.show();

                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(getApplicationContext(), ChangePasswordOpenEmail.class));
                        finish();
                    }, TIMER);
                }
                else {
                    // Custom Toast
                    Toast toast = Toast.makeText(ChangePasswordEnterEmail.this, task.getException().getMessage(), Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.white));
                    toast.show();
                }
            });
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        checkInputLayout();
    }

    public void checkInputLayout() {
        userEmail = emailInputField.getText().toString().trim();

        if(userEmail.contains("@gmail.com") | userEmail.contains("@yahoo.com")){
            emailInputField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email_icon, 0, R.drawable.green_tick, 0);
            // Hide Keyboard
            Common.hideKeyboard(ChangePasswordEnterEmail.this);
        } else {
            emailInputField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email_icon, 0, 0, 0);
        }

    }

    public void callClose(View view) {
        finish();
    }
}