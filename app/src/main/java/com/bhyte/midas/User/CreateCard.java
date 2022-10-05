package com.bhyte.midas.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bhyte.midas.HelperClasses.CardTypeSliderAdapter;
import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class CreateCard extends AppCompatActivity {

    ViewPager viewPager;
    CardTypeSliderAdapter cardTypeSliderAdapter;
    MaterialButton materialButton;
    ImageView dotIndicator;
    int currentPosition;

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;

            // Update View Elements Based On Current Page
            if (position == 0) {
                materialButton.setText(R.string.create_virtual_card);
                materialButton.setBackgroundColor(ContextCompat.getColor(CreateCard.this, R.color.blue_200));
                dotIndicator.setImageResource(R.drawable.indicator1);
            } else if (position == 1) {
                dotIndicator.setImageResource(R.drawable.indicator2);
                materialButton.setText(R.string.create_gift_card);
                materialButton.setBackgroundColor(ContextCompat.getColor(CreateCard.this, R.color.orange));
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        // Hooks
        viewPager = findViewById(R.id.slider);
        materialButton = findViewById(R.id.slider_button);
        dotIndicator = findViewById(R.id.dot_indicator);

        // Call Adapter
        cardTypeSliderAdapter = new CardTypeSliderAdapter(this);
        viewPager.setAdapter(cardTypeSliderAdapter);

        viewPager.addOnPageChangeListener(changeListener);

        dotIndicator.setOnClickListener(v -> {
            if (currentPosition == 0) {
                viewPager.setCurrentItem(currentPosition + 1);
            } else {
                viewPager.setCurrentItem(0);
            }
        });

    }

    public void createCard(View view) {
        if (currentPosition == 0) {
            startActivity(new Intent(getApplicationContext(), VirtualCardChooseDesign.class));
        } else if (currentPosition == 1) {
            startActivity(new Intent(getApplicationContext(), GiftCardChooseDesign.class));
        }
    }
}