package com.bhyte.midas.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bhyte.midas.R;
import com.bhyte.midas.Transactions.AddMoneyChooseMethod;
import com.google.android.material.button.MaterialButton;

public class UserTransactionsFragment extends Fragment {

    MaterialButton addMoneyButton;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_transactions, container, false);

        // Hooks
        addMoneyButton = root.findViewById(R.id.add_money_button);
        this.context = getContext();

        // Click Listeners
        addMoneyButton.setOnClickListener(v -> {
           startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class));
        });

        return root;
    }
}