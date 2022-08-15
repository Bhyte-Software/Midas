package com.bhyte.midas.Transactions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

public class AddMoney extends AppCompatActivity {

    public static Double amountToDeposit;
    public static String amountToDepositString;
    BottomSheetDialog bottomSheetDialog;
    Double userAmountDouble;
    MaterialButton nextButton;
    ImageView backspace, back, help;
    TextView amount, currency, one, two, three, four, five, six, seven, eight, nine, zero, dot;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        // Hooks
        help = findViewById(R.id.deposit_help);
        back = findViewById(R.id.back);
        amount = findViewById(R.id.amount);
        currency = findViewById(R.id.currency);
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
        nextButton = findViewById(R.id.next);

        nextButton.setOnClickListener(v -> {

            // Get Input
            // Convert String to int
            String userInputAmount = amount.getText().toString();
            if (userInputAmount.equals("")) {
                // Custom Toast
                Toast toast = Toast.makeText(AddMoney.this, "Please enter valid amount", Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            } else {
                userAmountDouble = Double.parseDouble(userInputAmount);
                amountToDeposit = userAmountDouble;
                amountToDepositString = userInputAmount;

                if (userAmountDouble > 5000 | userAmountDouble < 20) {
                    // Custom Toast
                    Toast toast = Toast.makeText(AddMoney.this, R.string.right_amount, Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                } else {
                    // Check Input
                    startActivity(new Intent(getApplicationContext(), AddMoneyChooseProvider.class));
                }
            }
        });

        // Click Listeners
        help.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(AddMoney.this, R.style.BottomSheetTheme);
            View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_money_help_bottom_sheet, findViewById(R.id.add_money_help));
            bottomSheetDialog.setContentView(sheetView);

            bottomSheetDialog.show();

            // Hooks
        });
        back.setOnClickListener(v -> finish());
        zero.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("0");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "0");
            }
        });
        one.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("1");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "1");
            }
        });
        two.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("2");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "2");
            }
        });
        three.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("3");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "3");
            }
        });
        four.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("4");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "4");
            }
        });
        five.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("5");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "5");
            }
        });
        six.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("6");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "6");
            }
        });
        seven.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("7");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "7");
            }
        });
        eight.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("8");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "8");
            }
        });
        nine.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText("9");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
            } else {
                amount.setText(amount.getText().toString() + "9");
            }
        });
        dot.setOnClickListener(v -> {
            if (amount.getText().toString().equals("0")) {
                amount.setText(".");
            } else if (amount.getText().toString().length() == 7) {
                amount.setText(amount.getText().toString());
                maxLengthToast();
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

    private void maxLengthToast() {
        // Custom Toast
        Toast toast = Toast.makeText(AddMoney.this, R.string.max_characters, Toast.LENGTH_SHORT);
        View view1 = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be edited
        TextView text = view1.findViewById(android.R.id.message);
        text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
        toast.show();
    }
}