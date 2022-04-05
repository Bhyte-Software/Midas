package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;
import com.bhyte.midas.User.ChangePasswordEnterEmail;
import com.bhyte.midas.User.SignInWithFingerprint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    String email, password;
    EditText passwordField, emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Hooks
        passwordField = findViewById(R.id.input_password);
        emailField = findViewById(R.id.email_input_layout);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        if (mUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainDashboard.class);
            startActivity(intent);
        }

    }

    public void callSignIn(View view) {
        if (!validatePassword() | !validateEmail()) {
            return;
        }

        // Get Data
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validateEmail() {
        String val = emailField.getText().toString().trim();
        String check_email = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            emailField.setError("Field cannot be empty!");
            return false;
        } else if (!val.matches(check_email)) {
            emailField.setError("Invalid Email!");
            return false;
        } else {
            emailField.setError(null);
            return true;
        }
    }


    private boolean validatePassword() {
        String val = passwordField.getText().toString().trim();
        String check_password = "^" +
                //"(?=.*[0-9])" +   // at least 1 digit
                //"(?=.*[a-z])" +   // at least 1 lower case letter
                //"(?=.*[A-Z])" +   // at least 1 upper case letter
                "(?=.*[a-zA-Z])" + // any letter
                "(?=.*[@#$%^&+=])" + // at least one special character
                "(?=\\S+$)" + // no white spaces
                ".{8,}" + //at least 8 characters
                "$";

        if (val.isEmpty()) {
            passwordField.setError("Field cannot be empty!");
            return false;
        } else if (!val.matches(check_password)) {
            passwordField.setError("Password should contain 8 characters!");
            return false;
        }
        else {
            passwordField.setError(null);
            //passwordField.setErrorEnabled(false);
            return true;
        }
    }

    public void callChangePassword(View view) {
        startActivity(new Intent(getApplicationContext(), ChangePasswordEnterEmail.class));
    }

    public void callSignInFingerprint(View view) {
        startActivity(new Intent(getApplicationContext(), SignInWithFingerprint.class));
    }
}