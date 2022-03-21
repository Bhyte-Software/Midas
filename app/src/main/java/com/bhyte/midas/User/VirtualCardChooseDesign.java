package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;

public class VirtualCardChooseDesign extends AppCompatActivity {

    public static String chosenColor;

    MaterialButton continueButton;
    RadioGroup radioGroup;
    ImageView cardImage;
    MaterialRadioButton selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_choose_design);

        // Hooks
        continueButton = findViewById(R.id.next);
        radioGroup = findViewById(R.id.radio_group);
        cardImage = findViewById(R.id.card_image);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callChooseCardLabel();
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

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        // Change Image based on chosen color
        if (radioGroup.getCheckedRadioButtonId() == R.id.blue_radio) {
            cardImage.setImageResource(R.drawable.cards_blue);
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.red_radio) {
            cardImage.setImageResource(R.drawable.cards_red);
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.yellow_radio) {
            cardImage.setImageResource(R.drawable.cards_yellow);
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.black_radio) {
            cardImage.setImageResource(R.drawable.cards_black);
        }
    }

    private boolean validateColor() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast toast = Toast.makeText(VirtualCardChooseDesign.this, "Please choose a card design", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));

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