package com.bhyte.midas.Transactions;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

public class AddMoneyChooseMethod extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton mobileMoneyRadio, bankTransferRadio;
    ImageView back, help;
    MaterialButton nextButton;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_choose_method);

        // Hooks
        radioGroup = findViewById(R.id.radio_group);
        mobileMoneyRadio = findViewById(R.id.mobile_money);
        bankTransferRadio = findViewById(R.id.bank_transfer);
        back = findViewById(R.id.back);
        help = findViewById(R.id.deposit_help);
        nextButton = findViewById(R.id.next);

        // Click Listeners
        nextButton.setOnClickListener(v -> {
            if(!validateInput()){
                return;
            }
            // Check Input
            if(mobileMoneyRadio.isChecked()){
                startActivity(new Intent(getApplicationContext(), AddMoney.class));
            }
            else {
                // Bank Transfer not available yet
                // Custom Toast
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    Toast toast = Toast.makeText(AddMoneyChooseMethod.this, R.string.bank_transfer_not_available, Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                    TextView textView = layout.findViewById(R.id.text);
                    textView.setText(R.string.bank_transfer_not_available);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }

        });

        back.setOnClickListener(v -> finish());

        help.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(AddMoneyChooseMethod.this, R.style.BottomSheetTheme);
            View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_money_help_bottom_sheet, findViewById(R.id.add_money_help));
            bottomSheetDialog.setContentView(sheetView);

            bottomSheetDialog.show();

            // Hooks
        });

    }

    private boolean validateInput() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            // Custom Toast
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                Toast toast = Toast.makeText(AddMoneyChooseMethod.this, R.string.select_deposit_method, Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                TextView textView = layout.findViewById(R.id.text);
                textView.setText(R.string.select_deposit_method);

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
            return false;
        } else {
            return true;
        }
    }
}