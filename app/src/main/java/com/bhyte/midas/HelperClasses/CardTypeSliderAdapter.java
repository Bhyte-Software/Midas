package com.bhyte.midas.HelperClasses;

import android.app.Dialog;
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
    Dialog infoDialog;
    MaterialButton cancelButton;
    LayoutInflater layoutInflater;
    int[] images = {
            R.drawable.v_card,
            R.drawable.g_card
    };
    int[] headings = {
            R.string.virtual_card,
            R.string.gift_card
    };
    int[] descriptions = {
            R.string.creation_fee_virtual_card,
            R.string.creation_fee_gift_card
    };
    int[] feature_one = {
            R.string.virtual_card_feature_one,
            R.string.gift_card_feature_one
    };
    int[] feature_two = {
            R.string.virtual_card_feature_two,
            R.string.gift_card_feature_two
    };
    int[] feature_three = {
            R.string.virtual_card_feature_three,
            R.string.gift_card_feature_three
    };
    int[] feature_four = {
            R.string.virtual_card_feature_four,
            R.string.gift_card_feature_four
    };

    public CardTypeSliderAdapter(Context context) {
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

        TextView featureOne, featureTwo, featureThree, featureFour;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.card_type_slides_layout, container, false);

        // Hooks
        ImageView cardTypeImage = view.findViewById(R.id.card_type_image);
        ImageView cardCreationFeeInfo = view.findViewById(R.id.card_creation_fee_info);
        TextView title = view.findViewById(R.id.card_type);
        TextView creationFee = view.findViewById(R.id.card_creation_fee);
        featureOne = view.findViewById(R.id.feature_one);
        featureTwo = view.findViewById(R.id.feature_two);
        featureThree = view.findViewById(R.id.feature_three);
        featureFour = view.findViewById(R.id.feature_four);

        // Set
        cardTypeImage.setImageResource(images[position]);
        title.setText(headings[position]);
        creationFee.setText(descriptions[position]);
        featureOne.setText(feature_one[position]);
        featureTwo.setText(feature_two[position]);
        featureThree.setText(feature_three[position]);
        featureFour.setText(feature_four[position]);

        // Click Listeners
        cardCreationFeeInfo.setOnClickListener(v -> {
            // Show Info Dialog Popup
            infoDialog = new Dialog(v.getContext(), R.style.BottomSheetTheme);

            View dialogView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.card_creation_info, view.findViewById(R.id.card_creation_info));

            infoDialog.setContentView(dialogView);
            infoDialog.show();

            // Hooks
            cancelButton = infoDialog.findViewById(R.id.cancel);

            // Click Listeners
            cancelButton.setOnClickListener(view1 -> infoDialog.dismiss());
        });

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
