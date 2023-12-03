package com.bhyte.midas.Transactions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bhyte.midas.Database.ReadWriteAllTransactions;
import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReviewTransaction extends AppCompatActivity {

    LottieAnimationView depositAnimation;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    TextView amountText, amountToPayText, numberText, providerText, transactionFeeText;
    String provider;
    String phoneNumber;
    String amount;
    String transactionFee;
    String amountToPay;
    String userEmail;
    Double amountDouble;
    ImageView back;
    MaterialButton deposit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_transaction);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Hooks
        depositAnimation = findViewById(R.id.deposit_animation);
        back = findViewById(R.id.back);
        deposit = findViewById(R.id.deposit);
        transactionFeeText = findViewById(R.id.transaction_fee);
        providerText = findViewById(R.id.provider);
        amountText = findViewById(R.id.amount);
        amountToPayText = findViewById(R.id.amount_to_pay_amount);
        numberText = findViewById(R.id.phone_number);

        // Get Data from previous Activities
        amount = AddMoney.amountToDepositString;
        provider = AddMoneyChooseProvider.networkName;
        amountDouble = AddMoney.amountToDeposit;

        // Data from Firebase
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            DatabaseReference databaseReference = database.getReference("Users").child(firebaseUser.getUid()).child("phone");
            DatabaseReference databaseEmailReference = database.getReference("Users").child(firebaseUser.getUid()).child("mail");
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

            databaseEmailReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userEmail = snapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //Formats Transaction Fee to only 2 decimal places
        DecimalFormat df = new DecimalFormat("0.00");

        // Calculations
        // Transaction Fee
        double transactionFeeInt = 0.009 * amountDouble;
        transactionFee = df.format(transactionFeeInt);


        // Amount you will have to pay
        double amountToPayDouble = amountDouble + transactionFeeInt; // The amount to pay displayed as a double
        amountToPay = df.format(amountToPayDouble);

        // Set TextView Texts
        providerText.setText(provider);
        amountText.setText("GHS " + amount);
        transactionFeeText.setText("GHS " + transactionFee);
        amountToPayText.setText("GHS " + amountToPay);

        // Click Listeners
        // Back Button
        back.setOnClickListener(v -> finish());

        // Deposit Button
        deposit.setOnClickListener(v -> {

            depositAnimation.setVisibility(View.VISIBLE);
            depositAnimation.playAnimation();
            deposit.setText("");

            // Deposit SMS Notifications
            new Thread(() -> {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                JSONObject apiData = new JSONObject();

                try {
                    apiData.put("api_key", "TLfITehl1SkhCoNHowco4ww1HvmLX2a2ovWbtqAu0UBv7F9UGOH2RtNoBOlJue");
                    apiData.put("to", "+233240369071"); // variable
                    apiData.put("from", "Midas Inc");
                    apiData.put("sms", "Great news, " + amount + " GHS has been added to your Midas balance"); //variable
                    apiData.put("type", "plain");
                    apiData.put("channel", "generic");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(apiData.toString(), mediaType);
                Request request = new Request.Builder()
                        .url("https://api.ng.termii.com/api/sms/send")
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    assert response.body() != null;
                    //System.out.println(response.body().string()); // This prints the response body to the console
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                // Api Request(Long Operation)

                // Initialize http client
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                JSONObject actualData = new JSONObject();
                JSONObject mobileMoney = new JSONObject();

                try {
                    mobileMoney.put("phone", "0551234987");
                    mobileMoney.put("provider", "mtn");

                    actualData.put("amount", (int) (amountToPayDouble * 100));
                    actualData.put("email", "femke@gmail.com");
                    actualData.put("currency", "GHS");
                    actualData.put("mobile_money", mobileMoney);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(actualData.toString(), mediaType);
                Request request = new Request.Builder()
                        .url("https://api.paystack.co/charge")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("Authorization", "Bearer sk_test_c87485f78655da06ffdd385fcda8f0a53bfa9f86")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    System.out.println(Objects.requireNonNull(response.body()).string());// This prints the response body to the console
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // TODO All this should be for when the deposit works/goes through

                // Set has Transactions to true
                String transaction = "True";

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                assert firebaseUser != null;
                // Get user main balance and add the deposit amount to it
                databaseReference.child(firebaseUser.getUid()).child("userMainBalance").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String currentBalance = snapshot.getValue(String.class);
                        assert currentBalance != null;

                        // The amount deposited plus the current balance
                        double amountToAdd = Double.parseDouble(currentBalance) + Double.parseDouble(amount);
                        String newBalance = Double.toString(Double.parseDouble(df.format(amountToAdd)));

                        // Write the new balance to the database
                        databaseReference.child(firebaseUser.getUid()).child("userMainBalance").setValue(newBalance).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), DepositSuccessPage.class));
                                finish();
                            } else {
                                // Print an error message
                                System.out.println("Error updating user main balance: " + task.getException());
                                // It could be a page that says; "There was a problem making a deposit, please try again"
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Print an error message
                        System.out.println("Error retrieving user main balance: " + error.getMessage());
                    }
                });

                // Transaction Preferences
                String transactionCurrency = "GH₵";
                // Transaction Type
                String transactionType = "Deposit";
                // Transaction Amount
                String transactionAmount = "+ " + amount;

                // Generate Date in Wed, 4 Jul 2001 12:08 Format
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                String transactionDate = dateFormat.format(Calendar.getInstance().getTime());

                // Write deposit amount to users database, as a Deposit Transaction
                databaseReference.child(firebaseUser.getUid()).child("transactions").child("depositTransactions").child(firebaseUser.getUid()).child("amount").setValue(amount);

                /* Write deposit amount to transactions database */
                // Get a reference to the "transactions" collection
                DatabaseReference transactionsRef = database.getReference("Transactions");

                // Get a reference to the "withdrawalTransactions" sub-collection
                DatabaseReference depositTransactionsRef = transactionsRef.child("depositTransactions");

                // Generate a unique ID for the new transaction
                String transactionUID = depositTransactionsRef.push().getKey();

                // Set transaction to True
                databaseReference.child(firebaseUser.getUid()).child("transaction").setValue(transaction);

                // Save to all transactions
                ReadWriteAllTransactions readWriteAllTransactions = new ReadWriteAllTransactions(transactionType, transactionDate, transactionCurrency, transactionAmount);
                DatabaseReference allTransactionsRef = database.getReference("Users");
                assert transactionUID != null;
                allTransactionsRef.child(firebaseUser.getUid()).child("All Transactions").child(transactionUID).setValue(readWriteAllTransactions);


                // Add the new transaction to the "withdrawalTransactions" sub-collection
                depositTransactionsRef.child(transactionUID).child("amount").setValue(amount);

                try{
                    runOnUiThread(() -> {
                        depositAnimation.setVisibility(View.GONE);
                        depositAnimation.pauseAnimation();
                        deposit.setText("Deposit");
                    });
                } catch (final Exception exception){
                    Log.i("---", "Exception in thread");
                }
            }).start();
        });
    }
}