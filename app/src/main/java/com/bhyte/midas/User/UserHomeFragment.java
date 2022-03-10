package com.bhyte.midas.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.R;
import com.bhyte.midas.Transactions.AddMoneyChooseMethod;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeFragment extends Fragment {

    String val = "visible";
    CircleImageView profilePicture;
    BottomSheetDialog bottomSheetDialog;
    RelativeLayout currencyView;
    MaterialButton addMoney;
    ImageView toggleIcon;
    TextView currency, totalAssets, accountBalance, greetingText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Hooks
        currencyView = root.findViewById(R.id.currency_view);
        currency = root.findViewById(R.id.currency);
        addMoney = root.findViewById(R.id.add_money_button);
        accountBalance = root.findViewById(R.id.account_balance);
        toggleIcon = root.findViewById(R.id.toggle_icon);
        totalAssets = root.findViewById(R.id.total_assets);
        profilePicture = root.findViewById(R.id.profile_picture);
        greetingText = root.findViewById(R.id.greetings);

        // Greeting
        greetUser();

        profilePicture.setOnClickListener(v -> startActivity(new Intent(getContext(), Profile.class)));

        currencyView.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.currency_bottom_sheet,
                    root.findViewById(R.id.currency_sheet));

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

        });

        addMoney.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class)));

        toggleIcon.setOnClickListener(v -> {
            if (val.equals("visible")) {
                toggleIcon.setImageResource(R.drawable.toggle_balance_);
                accountBalance.setText("****");
                val = "invisible";
            } else if (val.equals("invisible")) {
                toggleIcon.setImageResource(R.drawable.toggle_balance);
                accountBalance.setText(R.string._0_00);
                val = "visible";
            }
        });

        return root;
    }

    private void greetUser() {

        Calendar calendar = Calendar.getInstance();
        int timeOfDay;
        timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12){
            greetingText.setText(R.string.morning);
            // Set animation
        }
        else if (timeOfDay < 16){
            greetingText.setText(R.string.afternoon);
        }
        else if (timeOfDay < 21){
            greetingText.setText(R.string.evening);
        }
        else {
            greetingText.setText(R.string.night);
        }

    }

}