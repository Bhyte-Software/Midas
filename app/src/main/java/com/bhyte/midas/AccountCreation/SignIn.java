package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    ImageView fingerprintSignIn;
    TextView title, description, forgotPassword;
    EditText passwordField, emailField;
    LinearLayout signInTextLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        setContentView(R.layout.activity_sign_in);

        // Hooks
        forgotPassword = findViewById(R.id.forgot_password);
        signInTextLayout = findViewById(R.id.sign_in_layout);
        fingerprintSignIn = findViewById(R.id.fingerprint_image);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        passwordField = findViewById(R.id.input_password);
        emailField = findViewById(R.id.email_input_layout);

        final View activityRootView = findViewById(R.id.activity_root);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            //r will be populated with the coordinates of your view that area still visible.
            activityRootView.getWindowVisibleDisplayFrame(r);

            int heightDiff = activityRootView.getRootView().getHeight() - r.height();
            if (heightDiff > 0.25*activityRootView.getRootView().getHeight()) {
                // if more than 25% of the screen, its probably a keyboard...
                signInTextLayout.setVisibility(View.VISIBLE);
                fingerprintSignIn.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
            } else {
                signInTextLayout.setVisibility(View.GONE);
                fingerprintSignIn.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
            }
        });

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


        // Get Data
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
            finish();
        }).addOnFailureListener(e -> {
            // Sign In Error Custom Toast
            Toast toast = Toast.makeText(SignIn.this, R.string.sign_in_error, Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(ContextCompat.getColor(SignIn.this, R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(ContextCompat.getColor(SignIn.this, R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
        });
    }

    private boolean validateEmail() {
        String val = emailField.getText().toString().trim();
        String check_email = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";

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