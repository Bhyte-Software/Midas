package com.bhyte.midas.Transactions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        //Get withdrawn amount from database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        assert firebaseUser != null;
        DatabaseReference transactionsRef = databaseReference.child(firebaseUser.getUid()).child("transactions");
        DatabaseReference withdrawalTransactionsRef = transactionsRef.child("withdrawalTransactions");
        String lastTransactionRef = withdrawalTransactionsRef.orderByKey().limitToLast(1).getRef().getKey();
        //String transactionUID = lastTransactionRef

        withdrawalTransactionsRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                    String lastTransactionKey = transactionSnapshot.getKey();

                    assert lastTransactionKey != null;
                    withdrawalTransactionsRef.child(lastTransactionKey).child("amount").addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String withdrawnAmount = snapshot.getValue(String.class);
                            withdrawSuccessText.setText("GH₵" + withdrawnAmount + " has been sent to your mobile money account");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Print an error message
                            System.out.println("Error retrieving user main balance: " + error.getMessage());
                        }
                    });

                    //withdrawSuccessText.setText("GH₵" + lastTransactionKey + " has been sent to your mobile money account");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        greatNextButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
        });
    }
}
