package com.bhyte.midas.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;

public class VirtualCardChooseDesign extends AppCompatActivity {

    public static String chosenColor;
    Context context;

    RadioButton blackRadio;
    MaterialButton continueButton;
    RadioGroup radioGroup;
    ImageView cardImage;
    MaterialRadioButton selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_choose_design);

        context = getApplicationContext();

        // Hooks
        blackRadio = findViewById(R.id.black_radio);
        continueButton = findViewById(R.id.next);
        radioGroup = findViewById(R.id.radio_group);
        cardImage = findViewById(R.id.card_image);

        // Set Black default
        blackRadio.setChecked(true);

        continueButton.setOnClickListener(v -> callChooseCardLabel());

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int i = radioGroup.getCheckedRadioButtonId();
            if (i == R.id.blue_radio) {
                cardImage.setImageResource(R.drawable.cards_blue);
            }
            if (i == R.id.red_radio) {
                cardImage.setImageResource(R.drawable.cards_red);
            }
            if (i == R.id.yellow_radio) {
                cardImage.setImageResource(R.drawable.cards_yellow);
            }
            if (i == R.id.black_radio) {
                cardImage.setImageResource(R.drawable.cards_black);
            }
        });

    }

    private void callChooseCardLabel() {
        if (!validateColor()) {
            return;
        }

        selectedColor = findViewById(radioGroup.getCheckedRadioButtonId());

        if (radioGroup.getCheckedRadioButtonId() == R.id.black_radio) {
            chosenColor = "Black";
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.blue_radio) {
            chosenColor = "Blue";
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.red_radio) {
            chosenColor = "Red";
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.yellow_radio) {
            chosenColor = "Yellow";
        }

        startActivity(new Intent(getApplicationContext(), VirtualCardChooseLabel.class));

    }

    private boolean validateColor() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast toast = Toast.makeText(VirtualCardChooseDesign.this, "Please choose a card design", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
            return false;
        } else {
            return true;
        }
    }

    public void callBack(View view) {
        finish();
    }

}