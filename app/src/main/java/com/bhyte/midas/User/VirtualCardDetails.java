package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.R;

public class VirtualCardDetails extends AppCompatActivity {

    ImageView copyName, copyNumber, copyCvv;
    TextView cardName, cardNumber, cardCvv;
    Toast toast1, toast2, toast3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_details);

        // Hooks
        copyName = findViewById(R.id.copy_cardholder_name);
        copyNumber = findViewById(R.id.copy_card_number);
        copyCvv = findViewById(R.id.copy_cvv);
        cardName = findViewById(R.id.cardholder_name);
        cardNumber = findViewById(R.id.card_number_);
        cardCvv = findViewById(R.id.cvv);

        copyToClipboard();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void copyToClipboard() {
        copyName.setOnClickListener(v -> {
            // Copy to Clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Cardholder Name", cardName.getText().toString());
            clipboard.setPrimaryClip(clip);

            // Display Message
            toast1 = Toast.makeText(VirtualCardDetails.this, "Cardholder Name copied to clipboard", Toast.LENGTH_SHORT);

            // Customize Toast
            View view1 = toast1.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(getResources().getColor(R.color.light_green), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));

            toast1.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);

            // Show
            toast1.show();


            // Change Image Resource
            copyName.setImageDrawable(getResources().getDrawable(R.drawable.check_small));
            copyNumber.setImageDrawable(getResources().getDrawable(R.drawable.copy));
            copyCvv.setImageDrawable(getResources().getDrawable(R.drawable.copy));
        });

        copyNumber.setOnClickListener(v -> {
            // Copy to Clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Cardholder Number", cardNumber.getText().toString());
            clipboard.setPrimaryClip(clip);

            // Display Message
            toast2 = Toast.makeText(VirtualCardDetails.this, "Card Number copied to clipboard", Toast.LENGTH_SHORT);

            // Customize Toast
            View view2 = toast2.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view2.getBackground().setColorFilter(getResources().getColor(R.color.light_green), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view2.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));

            toast2.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);


            // Show
            toast2.show();

            // Change Image Resource
            copyName.setImageDrawable(getResources().getDrawable(R.drawable.copy));
            copyNumber.setImageDrawable(getResources().getDrawable(R.drawable.check_small));
            copyCvv.setImageDrawable(getResources().getDrawable(R.drawable.copy));
        });

        copyCvv.setOnClickListener(v -> {
            // Copy to Clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("CVV Number", cardCvv.getText().toString());
            clipboard.setPrimaryClip(clip);

            // Display Message
            toast3 = Toast.makeText(VirtualCardDetails.this, "Card CVV copied to clipboard", Toast.LENGTH_SHORT);

            // Customize Toast
            View view3 = toast3.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view3.getBackground().setColorFilter(getResources().getColor(R.color.light_green), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view3.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));

            toast3.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);

            // Show
            toast3.show();

            // Change Image Resource
            copyName.setImageDrawable(getResources().getDrawable(R.drawable.copy));
            copyNumber.setImageDrawable(getResources().getDrawable(R.drawable.copy));
            copyCvv.setImageDrawable(getResources().getDrawable(R.drawable.check_small));
        });

    }

    public void goBack(View view) {
        finish();
    }
}