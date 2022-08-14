package com.bhyte.midas.Transactions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewTransaction extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    TextView amountText, receiveText, numberText, providerText, transactionFeeText;
    String provider, phoneNumber, amount, transactionFee, youReceive;
    Double amountDouble;
    ImageView back;
    double youReceiveInt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_transaction);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Hooks
        back = findViewById(R.id.back);
        transactionFeeText = findViewById(R.id.transaction_fee);
        providerText = findViewById(R.id.provider);
        amountText = findViewById(R.id.amount);
        receiveText = findViewById(R.id.you_receive_amount);
        numberText = findViewById(R.id.phone_number);

        // Get Data from previous Activities
        amount = AddMoney.amountToDepositString;
        provider = AddMoneyChooseProvider.networkName;
        amountDouble = AddMoney.amountToDeposit;

        // Data from Firebase
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            DatabaseReference databaseReference = database.getReference("Users").child(firebaseUser.getUid()).child("phone");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    phoneNumber = snapshot.getValue(String.class);

                    if (!(numberText == null)) {
                        numberText.setText(phoneNumber);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        // Calculations
        // Transaction Fee
        double transactionFeeInt = 0.014 * amountDouble;
        transactionFee = String.valueOf(transactionFeeInt);

        // Amount you will receive
        youReceiveInt = amountDouble - transactionFeeInt;
        youReceive = String.valueOf(youReceiveInt);

        // Set TextView Texts
        providerText.setText(provider);
        amountText.setText("GHS " + amount);
        transactionFeeText.setText("GHS " + transactionFee);
        receiveText.setText("GHS " + youReceive);

        // Click Listeners
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}