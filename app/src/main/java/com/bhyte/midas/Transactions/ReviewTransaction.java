package com.bhyte.midas.Transactions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
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

public class ReviewTransaction extends AppCompatActivity {

    LottieAnimationView depositAnimation;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    TextView amountText, amountToPayText, numberText, providerText, transactionFeeText;
    String provider, phoneNumber, amount, transactionFee, amountToPay, userEmail, date;
    Double amountDouble;
    ImageView back;
    MaterialButton deposit;

    BottomSheetDialog depositConfirmationBottomSheet;

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
        transactionFee = String.valueOf(df.format(transactionFeeInt));


        // Amount you will have to pay
        double amountToPayDouble = amountDouble + transactionFeeInt; // The amount to pay displayed as a double
        amountToPay = String.valueOf(df.format(amountToPayDouble));

        // Set TextView Texts
        providerText.setText(provider);
        amountText.setText("GHS " + amount);
        transactionFeeText.setText("GHS " + transactionFee);
        amountToPayText.setText("GHS " + amountToPay);

        // Click Listeners
        // Back Button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Deposit Button
        deposit.setOnClickListener(v -> {
            depositConfirmationBottomSheet = new BottomSheetDialog(ReviewTransaction.this, R.style.BottomSheetTheme);
            View depositSheetView = LayoutInflater.from(this).inflate(R.layout.deposit_comfirmation_sheet, null);
            setContentView(depositSheetView);
            depositConfirmationBottomSheet.show();
        });
    }
}