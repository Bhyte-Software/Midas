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

import java.text.DecimalFormat;
import java.util.Objects;

public class SendReceiverSuccessPage extends AppCompatActivity {
    MaterialButton greatNextButton;
    TextView sendSuccessText;

    public static String usersName;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendreceiver_success_page);

        // Instance of FirebaseAuth and Database
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        greatNextButton = findViewById(R.id.great_next_button);
        sendSuccessText = findViewById(R.id.send_success);

        //Get sent amount from database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        assert firebaseUser != null;
        DatabaseReference transactionsRef = databaseReference.child(firebaseUser.getUid()).child("transactions");
        DatabaseReference sendTransactionsRef = transactionsRef.child("sendTransactions");

        //
        sendTransactionsRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                    String lastTransactionKey = transactionSnapshot.getKey();

                    assert lastTransactionKey != null;
                    sendTransactionsRef.child(lastTransactionKey).child("amount").addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String sentAmount = snapshot.getValue(String.class);
                            sendSuccessText.setText("GH₵" + sentAmount + " has been sent to " + usersName);

                            //TODO This can also be where receiveTransactions document is created in the users database

                            // This sends the money to the chosen user by their name
                            databaseReference.orderByChild("name").equalTo(usersName).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                        // Get the main balance of the chosen user
                                        String mainBalance = userSnapshot.child("userMainBalance").getValue(String.class);
                                        DecimalFormat df = new DecimalFormat("0.00");

                                        // Convert the main balance and amount sent to doubles
                                        assert mainBalance != null;
                                        double mainBalanceDouble = Double.parseDouble(mainBalance);
                                        assert sentAmount != null;
                                        double amountSentDouble = Double.parseDouble(sentAmount);

                                        // Add the amount sent to the main balance
                                        double updatedMainBalance = mainBalanceDouble + amountSentDouble;

                                        // Convert the updated main balance back to a string
                                        String updatedMainBalanceString = Double.toString(Double.parseDouble(df.format(updatedMainBalance)));

                                        // Update the main balance in the database for the chosen user
                                        userSnapshot.getRef().child("userMainBalance").setValue(updatedMainBalanceString);

                                        userSnapshot.getRef().child("transactions").child("receivedTransactions").child(Objects.requireNonNull(databaseReference.push().getKey())).child("amount").setValue(sentAmount);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Print an error message
                            System.out.println("Error retrieving user main balance: " + error.getMessage());
                        }
                    });
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
