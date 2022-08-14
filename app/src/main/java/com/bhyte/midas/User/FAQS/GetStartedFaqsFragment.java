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

public class GetStartedFaqsFragment extends Fragment {

    Context context;

    TextView detailsText;
    CardView cardView;
    LinearLayout layout;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_get_started_faqs, container, false);

        context = this.getContext();

        // Hooks
        detailsText = root.findViewById(R.id.detail);
        cardView = root.findViewById(R.id.card_layout);
        layout = root.findViewById(R.id.layout);
        imageView = root.findViewById(R.id.expand_collapse_icon);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // Click Listeners
        cardView.setOnClickListener(v -> {
            int visibility = (detailsText.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

            TransitionManager.beginDelayedTransition(layout, new AutoTransition());
            detailsText.setVisibility(visibility);

            if(detailsText.getVisibility() == View.VISIBLE){
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.collapse));
            }
            if(detailsText.getVisibility() == View.GONE){
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.expand));
            }
        });

        return root;
    }
}