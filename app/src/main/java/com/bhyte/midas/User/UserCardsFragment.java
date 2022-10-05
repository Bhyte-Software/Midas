package com.bhyte.midas.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class UserCardsFragment extends Fragment {

    MaterialButton createCardButton;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_cards, container, false);

        // Hooks
        createCardButton = root.findViewById(R.id.create_card_button);
        this.context = getContext();

        // Click Listeners
        createCardButton.setOnClickListener(v -> startActivity(new Intent(context, CreateCard.class)));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}