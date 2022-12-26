package com.bhyte.midas.Transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class WithdrawalSuccessPage extends AppCompatActivity {
    MaterialButton greatNextButton;
    TextView withdrawSuccessText;
    Context context;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdrawal_success_page);

        // Instance of FirebaseAuth and Database
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        greatNextButton = findViewById(R.id.great_next_button);
        withdrawSuccessText = findViewById(R.id.withdraw_success);

        greatNextButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
        });
    }
}
