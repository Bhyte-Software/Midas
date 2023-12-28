package com.bhyte.midas.Transactions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.AccountCreation.SignUpEnterNumber;
import com.bhyte.midas.Database.ReadWriteAllTransactions;
import com.bhyte.midas.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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


public class WithdrawMoney extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    Context context;
    BottomSheetDialog bottomSheetDialog;
    Double userAmountDouble;
    MaterialButton withdrawButton;
    ImageView backspace, back;
    TextView amount, currentBalanceText, currency, one, two, three, four, five, six, seven, eight, nine, zero, dot;
    String phoneNumber ,userInputAmount;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_money);

        this.context = getApplicationContext();

        // Instance of FirebaseAuth and Database
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Hooks
        back = findViewById(R.id.back);
        amount = findViewById(R.id.amount);
        currency = findViewById(R.id.currency);
        currentBalanceText = findViewById(R.id.current_balance);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        dot = findViewById(R.id.dot);

        backspace = findViewById(R.id.backspace);
        withdrawButton = findViewById(R.id.withdraw_button);

        // Connect to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        assert firebaseUser != null;
        databaseReference.child(firebaseUser.getUid()).child("userMainBalance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentBalance = snapshot.getValue(String.class);
                assert currentBalance != null;
                currentBalanceText.setText("GH₵" + currentBalance + " AVAILABLE");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Print an error message
                System.out.println("Error retrieving user main balance: " + error.getMessage());
            }
        });

        // Withdraw Button
        withdrawButton.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(WithdrawMoney.this, R.style.BottomSheetTheme);
            View sheetView = LayoutInflater.from(context).inflate(R.layout.withdrawal_method_bottom_sheet, findViewById(R.id.withdrawal_method));
            RadioButton selectMobileMoney = sheetView.findViewById(R.id.withdraw_by_mobile_money);
            MaterialButton withdrawButtonBottom = sheetView.findViewById(R.id.withdraw_button_bottom_sheet);
            bottomSheetDialog.setContentView(sheetView);

            String text = "Mobile Money Transfer\n" + "(Instant)";
            SpannableString spannableString = new SpannableString(text);

            /* Bold Styling
            // This selects a specific text
            int startIndex = text.indexOf("Mobile Money Transfer");
            int endIndex = startIndex + "Mobile Money Transfer".length();

            // This sets the selected text to bold
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

             */

            // This gets the user input
            userInputAmount = amount.getText().toString();

            if (userInputAmount.equals("") || userInputAmount.equals("0")) {
                Toast.makeText(getApplicationContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
            } else {
                userAmountDouble = Double.parseDouble(userInputAmount);

                databaseReference.child(firebaseUser.getUid()).child("userMainBalance").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String currentBalance = snapshot.getValue(String.class);
                        assert currentBalance != null;
                        double currentBalanceDouble = Double.parseDouble(currentBalance);

                        if (userAmountDouble > currentBalanceDouble) {
                            Toast.makeText(getApplicationContext(), "Insufficient Balance", Toast.LENGTH_SHORT).show();
                        } else {
                            selectMobileMoney.setText(spannableString);
                            withdrawButtonBottom.setText("Withdraw GH₵" + userInputAmount);
                            bottomSheetDialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Print an error message
                        System.out.println("Error retrieving user main balance: " + error.getMessage());
                    }
                });
            }

            // When the second withdraw button is pressed
            withdrawButtonBottom.setOnClickListener(w -> {
                bottomSheetDialog = new BottomSheetDialog(WithdrawMoney.this, R.style.BottomSheetTheme);
                View withdrawalConfirmationSheetView = LayoutInflater.from(context).inflate(R.layout.withdrawal_confirmation_sheet, findViewById(R.id.withdrawal_confirmation_sheet_id));
                bottomSheetDialog.setContentView(withdrawalConfirmationSheetView);
                bottomSheetDialog.show();

                MaterialButton confirmWithdrawBtn = withdrawalConfirmationSheetView.findViewById(R.id.withdraw_confirm_btn);
                MaterialButton cancelWithdrawBtn = withdrawalConfirmationSheetView.findViewById(R.id.withdraw_cancel_btn);

                TextView withdrawTextDetail = withdrawalConfirmationSheetView.findViewById(R.id.withdraw_message_id);
                withdrawTextDetail.setText("Are you sure you would like to withdraw " + userInputAmount + "GHC from your account");
                confirmWithdrawBtn.setOnClickListener(v1 -> {
                    // TODO FINGERPRINT VERIFICATION BEFORE ANY OF THIS

                    assert firebaseUser != null;
                    databaseReference.child(firebaseUser.getUid()).child("transactions").child("withdrawalTransactions").child(Objects.requireNonNull(databaseReference.push().getKey())).child("amount").setValue(userInputAmount);

                    // Get a reference to the "transactions" collection
                    DatabaseReference transactionsRef = database.getReference("Transactions");

                    // Get a reference to the "withdrawalTransactions" sub-collection
                    DatabaseReference withdrawalTransactionsRef = transactionsRef.child("withdrawalTransactions");

                    // Generate a unique ID for the new transaction
                    String transactionUID = withdrawalTransactionsRef.push().getKey();

                    // Add the new transaction to the "withdrawalTransactions" sub-collection
                    assert transactionUID != null;
                    withdrawalTransactionsRef.child(transactionUID).child("amount").setValue(userInputAmount);

                    // Transaction Preferences
                    String transactionCurrency = "GH₵";
                    // Transaction Type
                    String transactionType = "Withdraw";
                    // Transaction Amount
                    String transactionAmount = "- " + userInputAmount;

                    // Generate Date in Wed, 4 Jul 2001 12:08 Format
                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                    String transactionDate = dateFormat.format(Calendar.getInstance().getTime());

                    // Save to all transactions
                    ReadWriteAllTransactions readWriteAllTransactions = new ReadWriteAllTransactions(transactionType, transactionDate, transactionCurrency, transactionAmount);
                    DatabaseReference allTransactionsRef = database.getReference("Users");
                    allTransactionsRef.child(firebaseUser.getUid()).child("All Transactions").child(transactionUID).setValue(readWriteAllTransactions);


                    // Get the users current phone number
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        DatabaseReference databasePhoneReference = database.getReference("Users").child(firebaseUser.getUid()).child("phone");
                        databasePhoneReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                phoneNumber = snapshot.getValue(String.class);
                                assert phoneNumber != null;
                                phoneNumber = phoneNumber.substring(4);
                                phoneNumber = "+" + "233" + phoneNumber;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    // Get current balance to be subtracted from
                    databaseReference.child(firebaseUser.getUid()).child("userMainBalance").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String currentBalance = snapshot.getValue(String.class);
                            DecimalFormat df = new DecimalFormat("0.00");
                            assert currentBalance != null;

                            // The current balance minus the amount withdrawn
                            double amountToSubtract = Double.parseDouble(currentBalance) - Double.parseDouble(userInputAmount);
                            String newBalance = Double.toString(Double.parseDouble(df.format(amountToSubtract)));

                            // Write the new balance to the database
                            databaseReference.child(firebaseUser.getUid()).child("userMainBalance").setValue(newBalance).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(context, WithdrawalSuccessPage.class));


                                    // Withdraw SMS notification
                                    new Thread(() -> {
                                        OkHttpClient client = new OkHttpClient();

                                        MediaType mediaType = MediaType.parse("application/json");
                                        JSONObject apiData = new JSONObject();

                                        try {
                                            apiData.put("api_key", "TLfITehl1SkhCoNHowco4ww1HvmLX2a2ovWbtqAu0UBv7F9UGOH2RtNoBOlJue");
                                            apiData.put("to", phoneNumber);
                                            apiData.put("from", "Midas Inc");
                                            apiData.put("sms", "Great, " + userAmountDouble + " GHS is on it's way to your mobile money account!");
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
                                            //System.out.println(phoneNumber); // This prints the response body to the console
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }).start();



                                    // Paystack Transfer API - Withdraw to user
                                    new Thread(() -> {
                                        OkHttpClient client = new OkHttpClient();

                                        MediaType mediaType = MediaType.parse("application/json");
                                        JSONObject params = new JSONObject();

                                        try {
                                            params.put("source", "balance");
                                            params.put("reason", "Android Test");
                                            params.put("amount", (int) (userAmountDouble * 100));
                                            params.put("recipient", "RCP_bcepcgbk3klxipe");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        RequestBody body = RequestBody.create(params.toString(), mediaType);
                                        Request request = new Request.Builder()
                                                .url("https://api.paystack.co/transfer")
                                                .post(body)
                                                .addHeader("Content-Type", "application/json")
                                                .addHeader("Authorization", "Bearer pk_live_406be5b6cd14a2897d865666a5060a177257050a")
                                                .build();

                                        try {
                                            Response response = client.newCall(request).execute();
                                            assert response.body() != null;
                                            //System.out.println(response.body().string()); // This prints the response body to the console
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }).start();


                                    finish();
                                } else {
                                    // Print an error message
                                    System.out.println("Error updating user main balance: " + task.getException());
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Print an error message
                            System.out.println("Error retrieving user main balance: " + error.getMessage());
                        }
                    });
                });
                cancelWithdrawBtn.setOnClickListener(v2 -> {
                    bottomSheetDialog.dismiss();
                });
            });
        });

        // Click Listeners
        back.setOnClickListener(v -> finish());
        zero.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("0");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "0");
            }
        });
        one.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("1");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "1");
            }
        });
        two.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("2");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "2");
            }
        });
        three.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("3");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "3");
            }
        });
        four.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("4");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "4");
            }
        });
        five.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("5");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "5");
            }
        });
        six.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("6");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "6");
            }
        });
        seven.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("7");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "7");
            }
        });
        eight.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("8");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "8");
            }
        });
        nine.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("9");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + "9");
            }
        });
        dot.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText(".");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
            } else {
                amount.setText(amount.getText().toString() + ".");
            }
        });
        backspace.setOnClickListener(v -> {
            if (amount.getText().toString().equals("") | amount.getText().toString().equals("0")) {
                amount.setText("0");
            } else {
                amount.setText(amount.getText().toString().substring(0, amount.getText().length() - 1));
            }
        });
    }
}