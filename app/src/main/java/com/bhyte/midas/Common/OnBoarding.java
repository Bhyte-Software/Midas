package com.bhyte.midas.Common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.HelperClasses.SliderAdapter;
import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    SliderAdapter sliderAdapter;
    MaterialButton skipButton;
    MaterialButton materialButton;
    ImageView dotIndicator;
    Animation animation;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        // Hooks
        viewPager = findViewById(R.id.slider);
        skipButton = findViewById(R.id.skip_button);
        materialButton = findViewById(R.id.slider_button);
        dotIndicator = findViewById(R.id.dot_indicator);

        // Call Adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        viewPager.addOnPageChangeListener(changeListener);

    }

    public void skip(View view){
        startActivity(new Intent(getApplicationContext(), GetStarted.class));
        finish();
    }

    public void next(View view){
        if (currentPosition <= 3){
            viewPager.setCurrentItem(currentPosition + 1);
        }
        else if (currentPosition == 4){
            startActivity(new Intent(getApplicationContext(), GetStarted.class));
            finish();
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            currentPosition = position;

            // Update View Elements Based On Current Page

            if(position == 0){
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_animation);
                skipButton.setAnimation(animation);
                skipButton.setVisibility(View.VISIBLE);

                // Change Slider Button Text
                materialButton.setText(R.string.next);

                // Change dot indicator
                dotIndicator.setImageResource(R.drawable.indicator_one);
            }
            else if(position == 1){
                skipButton.setVisibility(View.INVISIBLE);

                // Change Slider Button Text
                materialButton.setText(R.string.next);

                // Change dot indicator
                dotIndicator.setImageResource(R.drawable.indicator_two);
            }
            else if (position == 2){
                skipButton.setVisibility(View.INVISIBLE);

                // Change Slider Button Text
                materialButton.setText(R.string.next);

                // Change dot indicator
                dotIndicator.setImageResource(R.drawable.indicator_three);
            }
            else if (position == 3){
                skipButton.setVisibility(View.INVISIBLE);

                // Change dot indicator
                dotIndicator.setImageResource(R.drawable.indicator_four);

                // Change Slider Button Text
                materialButton.setText(R.string.next);
            }
            else if (position == 4){
                skipButton.setVisibility(View.INVISIBLE);

                // Change dot indicator
                dotIndicator.setImageResource(R.drawable.indicator_five);

                // Change Slider Button Text
                materialButton.setText(R.string.get_started);

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}