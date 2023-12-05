package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.Common.AcceptTermsOfService;
import com.bhyte.midas.Database.ReadWriteUserDetails;
import com.bhyte.midas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpCredentials extends AppCompatActivity {

    String fullName, email, password, phoneNumber, newPhoneNumber, completeNumber, newUserMainBalance, transaction;
    EditText fullNameField, emailField, passwordField;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_credentials);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        phoneNumber = SignUpEnterNumber.fullPhoneNumber;

        // Hooks
        fullNameField = findViewById(R.id.name_input_layout);
        emailField = findViewById(R.id.email_input_layout);
        passwordField = findViewById(R.id.password_input_layout);
    }

    public void callSignIn(View view) {
        startActivity(new Intent(getApplicationContext(), SignIn.class));
    }

    public void callAuthentication(View view) {
        boolean isFullNameValid = validateFullName();
        boolean isEmailValid = validateEmail();
        boolean isPasswordValid = validatePassword();

        if (isFullNameValid && isEmailValid && isPasswordValid) {
            // Get Data from Edit Text Fields
            fullName = fullNameField.getText().toString();
            email = emailField.getText().toString();
            password = passwordField.getText().toString();

            completeNumber = phoneNumber.substring(1);
            newPhoneNumber = "+" + "233" + completeNumber;
            newUserMainBalance = "0";
            transaction = "False";

            // Create User
            createUser();
        }
    }


    private void createUser() {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            // Save Data & move to next page
            saveData();
        });
               /* .addOnFailureListener(e -> {
            Toast toast = Toast.makeText(SignUpCredentials.this, R.string.error_try_again, Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            if (view1 == null) {
                //Gets the actual oval background of the Toast then sets the colour filter
                //assert false;
                view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpCredentials.this, R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(SignUpCredentials.this, R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            }
        });*/
    }

    private void saveData() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(phoneNumber, fullName, email, newUserMainBalance, transaction);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        assert firebaseUser != null;
        databaseReference.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(getApplicationContext(), AcceptTermsOfService.class));
                finish();
            } else {
                Toast toast = Toast.makeText(SignUpCredentials.this, R.string.registration_failed, Toast.LENGTH_LONG);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpCredentials.this, R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(SignUpCredentials.this, R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            }
        });
    }

    private boolean validateFullName() {
        String val = fullNameField.getText().toString().trim();
        String check_spaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            fullNameField.setError("FullName cannot be empty!");
            return false;
        } else if (val.matches(check_spaces)) {
            fullNameField.setError("Enter your full name!");
            return false;
        } else {
            fullNameField.setError(null);
            return true;
        }
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
                "(?=.*[!@#$%^&*()_+-=/?.>,<;:'{}`~0])" + // at least one special character
                "(?=\\S+$)" + // no white spaces
                ".{8,}" + //at least 8 characters
                "$";

        if (val.isEmpty()) {
            passwordField.setError("Password cannot be empty!");
            return false;
        } else if (!val.matches(check_password)) {
            passwordField.setError("Password should be at least 8 characters long, include at least one letter, one special character, and have no white spaces.");
            return false;
        } else {
            passwordField.setError(null);
            return true;
        }
    }

}