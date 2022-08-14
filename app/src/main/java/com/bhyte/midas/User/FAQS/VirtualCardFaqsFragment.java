package com.bhyte.midas.User.FAQS;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhyte.midas.R;

public class VirtualCardFaqsFragment extends Fragment {

    Context context;

    TextView detailsText, detailsText2;
    CardView cardView, cardView2;
    LinearLayout layout, layout2;
    ImageView imageView, imageView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_virtual_card_faqs, container, false);

        context = this.getContext();

        // Hooks
        detailsText = root.findViewById(R.id.detail);
        detailsText2 = root.findViewById(R.id.detail2);
        cardView = root.findViewById(R.id.card_layout);
        cardView2 = root.findViewById(R.id.card_layout2);
        layout = root.findViewById(R.id.layout);
        layout2 = root.findViewById(R.id.layout2);
        imageView = root.findViewById(R.id.expand_collapse_icon);
        imageView2 = root.findViewById(R.id.expand_collapse_icon2);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // Click Listeners
        cardView.setOnClickListener(v -> {
            int visibility = (detailsText.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

            TransitionManager.beginDelayedTransition(layout, new AutoTransition());
            detailsText.setVisibility(visibility);
            detailsText2.setVisibility(View.GONE);
            imageView2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.expand));


            if(detailsText.getVisibility() == View.VISIBLE){
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.collapse));
            }
            if(detailsText.getVisibility() == View.GONE){
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.expand));
            }
        });

        cardView2.setOnClickListener(v -> {
            int visibility = (detailsText2.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

            TransitionManager.beginDelayedTransition(layout2, new AutoTransition());
            detailsText2.setVisibility(visibility);
            detailsText.setVisibility(View.GONE);
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.expand));


            if(detailsText2.getVisibility() == View.VISIBLE){
                imageView2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.collapse));
            }
            if(detailsText2.getVisibility() == View.GONE){
                imageView2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.expand));
            }
        });

        return root;
    }
}