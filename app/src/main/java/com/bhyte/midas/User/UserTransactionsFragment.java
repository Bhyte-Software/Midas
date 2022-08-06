package com.bhyte.midas.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.bhyte.midas.R;
import com.bhyte.midas.Transactions.AddMoneyChooseMethod;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

public class UserTransactionsFragment extends Fragment {
    public String amountEntered;
    public int lengthOfVal;

    MaterialButton addMoneyButton, continueButton;
    BottomSheetDialog bottomSheetDialog;
    EditText enterAmount;
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
            bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(context).inflate(R.layout.enter_amount_bottom_sheet,
                    root.findViewById(R.id.amount_sheet));

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

            // Hooks
            continueButton = bottomSheetDialog.findViewById(R.id.continue_button);
            enterAmount = bottomSheetDialog.findViewById(R.id.amount_input_layout);

            // Click Listeners
            assert continueButton != null;
            continueButton.setOnClickListener(v1 -> checkInput());
        });

        return root;
    }

    private void checkInput() {
        amountEntered = enterAmount.getText().toString().trim();
        lengthOfVal = amountEntered.length();

        if (lengthOfVal >= 2) {
            startActivity(new Intent(context, AddMoneyChooseMethod.class));
        } else if (lengthOfVal == 0) {
            enterAmount.setError("Please enter amount");
        } else {
            enterAmount.setError("Amount should be 10 or more");
        }
    }
}