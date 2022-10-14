package com.bhyte.midas.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.R;
import com.bhyte.midas.Recycler.CardsAdapter;
import com.bhyte.midas.Recycler.CardsHelperClass;
import com.bhyte.midas.Recycler.PlatformsHelperClass;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class UserCardsFragment extends Fragment {

    RecyclerView.Adapter<?> cardsAdapter;
    ArrayList<CardsHelperClass> viewCards = new ArrayList<>();

    ImageView noCardsImg;
    TextView title, desc;
    RecyclerView cardsRecycler;
    MaterialButton createCardButton;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_cards, container, false);

        // Hooks
        noCardsImg = root.findViewById(R.id.no_cards_img);
        title = root.findViewById(R.id.title);
        desc = root.findViewById(R.id.description);
        cardsRecycler = root.findViewById(R.id.cards_recycler);
        createCardButton = root.findViewById(R.id.create_card_button);
        this.context = getContext();

        // Initialize Recycler View
        cardsRecycler.setHasFixedSize(true);
        cardsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        // Click Listeners
        createCardButton.setOnClickListener(v -> {
            createCard();
        });


        return root;
    }

    private void createCard() {
        if(cardsRecycler.getVisibility() == View.GONE){
            cardsRecycler.setVisibility(View.VISIBLE);
            // Hide Other items
            noCardsImg.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            desc.setVisibility(View.GONE);
        }
        viewCards.add(new CardsHelperClass(R.drawable.red_card, "John Doe", "2142 2131 1423", "21/04/2022"));
        cardsAdapter = new CardsAdapter(viewCards);
        cardsRecycler.setAdapter(cardsAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}