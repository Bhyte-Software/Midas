package com.bhyte.midas.Transactions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.AccountCreation.GetStarted;
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

import java.text.DecimalFormat;
import java.util.Objects;

public class SendMoney extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    Context context;
    Double userAmountDouble;
    MaterialButton sendMoneyButton;
    ImageView backspace, back;
    TextView amount, currentBalanceText, currency, one, two, three, four, five, six, seven, eight, nine, zero, dot;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

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
        sendMoneyButton = findViewById(R.id.send_money_button);
        sendMoneyButton.setText("Send GH₵0");

        // Display send amount on button, in realtime
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //sendMoneyButton.setText("Send GH₵0");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called whenever the text in the EditText changes
                String userInputAmount = amount.getText().toString();
                sendMoneyButton.setText("Send GH₵" + userInputAmount);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //
            }
        });

        // Connect to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        assert firebaseUser != null;
        // Get and show available balance
        databaseReference.child(firebaseUser.getUid()).child("userMainBalance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentBalance = snapshot.getValue(String.class);
                assert currentBalance != null;
                currentBalanceText.setText("GH₵"+ currentBalance + " AVAILABLE");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Print an error message
                System.out.println("Error retrieving user main balance: " + error.getMessage());
            }
        });

        // Send Money Button
        sendMoneyButton.setOnClickListener(v -> {
            String userInputAmount = amount.getText().toString();

            // Pass the user input amount to the SendReceiver class
            Intent sendReceiverIntent = new Intent(getApplicationContext(), SendReceiver.class);
            SendReceiver.userInputAmount = userInputAmount;

            if (userInputAmount.equals("") || userInputAmount.equals("0")) {
                Toast.makeText(getApplicationContext(),"Please enter an amount",Toast.LENGTH_SHORT).show();
            } else {
                userAmountDouble = Double.parseDouble(userInputAmount);

                databaseReference.child(firebaseUser.getUid()).child("userMainBalance").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String currentBalance = snapshot.getValue(String.class);
                        assert currentBalance != null;
                        double currentBalanceDouble = Double.parseDouble(currentBalance);

                        if (userAmountDouble > currentBalanceDouble) {
                            Toast.makeText(getApplicationContext(),"Insufficient Balance",Toast.LENGTH_SHORT).show();
                        } else {
                            //startActivity(sendReceiverIntent);
                            startActivity(new Intent(getApplicationContext(), SendReceiver.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Print an error message
                        System.out.println("Error retrieving user main balance: " + error.getMessage());
                    }
                });
            }
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