package com.bhyte.midas.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class UserCardsFragment extends Fragment {

    MaterialButton createCardButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_cards, container, false);

        // Hooks
        createCardButton = root.findViewById(R.id.create_card_button);

        // Click Listeners
        createCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateCard.class));
            }
        });

        return root;
    }
}