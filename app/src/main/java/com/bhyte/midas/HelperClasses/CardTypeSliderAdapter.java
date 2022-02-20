package com.bhyte.midas.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class CardTypeSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public CardTypeSliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.virtual_card,
            R.drawable.gift_card
    };

    int headings[] = {
            R.string.virtual_card,
            R.string.gift_card
    };


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
        View view = layoutInflater.inflate(R.layout.card_type_slides_layout, container, false);

        // Hooks
        ImageView cardTypeImage = view.findViewById(R.id.card_type_image);
        TextView title = view.findViewById(R.id.card_type);

        // Set
        cardTypeImage.setImageResource(images[position]);
        title.setText(headings[position]);

        container.addView(view);

        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
