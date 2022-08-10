package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;
import com.bhyte.midas.User.ChangePasswordEnterEmail;
import com.bhyte.midas.User.SignInWithFingerprint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
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
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null) {
            // Sign In Automatically
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
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
            // Sign In Error Custom Toast
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                Toast toast = Toast.makeText(SignIn.this, R.string.sign_in_error, Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(SignIn.this, R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(SignIn.this, R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                TextView textView = layout.findViewById(R.id.text);
                textView.setText(R.string.sign_in_error);

                Toast toast = new Toast(SignIn.this);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
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
        } else {
            passwordField.setError(null);
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