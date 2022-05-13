package com.bhyte.midas.HelperClasses;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    int[] images = {
            R.drawable.onboarding_image_one,
            R.drawable.onboarding_image_two,
            R.drawable.onboarding_image_three,
            R.drawable.onboarding_image_four,
            R.drawable.onboarding_image_five
    };
    int[] headings = {
            R.string.onboarding_title_one,
            R.string.onboarding_title_two,
            R.string.onboarding_title_three,
            R.string.onboarding_title_four,
            R.string.onboarding_title_five
    };
    int[] descriptions = {
            R.string.onboarding_description_one,
            R.string.onboarding_description_two,
            R.string.onboarding_description_three,
            R.string.onboarding_description_four,
            R.string.onboarding_description_five
    };

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout, container, false);

        // Hooks
        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView title = view.findViewById(R.id.slider_title);
        TextView description = view.findViewById(R.id.slider_description);
        MaterialButton button = view.findViewById(R.id.slider_button);
        ImageView dot_indicator = view.findViewById(R.id.dot_indicator);

        // Switch Theme Based on Mode
        int nightModeFlags = context.getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                title.setTextColor(ContextCompat.getColor(context.getApplicationContext(), R.color.white));
                description.setTextColor(ContextCompat.getColor(context.getApplicationContext(), R.color.white_light));
                break;

            case Configuration.UI_MODE_NIGHT_NO | Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;

        }

        // Set
        imageView.setImageResource(images[position]);
        title.setText(headings[position]);
        description.setText(descriptions[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
