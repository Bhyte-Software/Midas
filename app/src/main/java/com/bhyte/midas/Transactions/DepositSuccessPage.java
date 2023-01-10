package com.bhyte.midas.Transactions;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class DepositSuccessPage extends AppCompatActivity {

    MaterialButton greatNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposit_success_page);

        greatNextButton = findViewById(R.id.great_next_button);

        greatNextButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
        });
    }
}
