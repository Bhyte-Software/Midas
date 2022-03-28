package com.bhyte.midas.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bhyte.midas.AccountCreation.SignUpVerifyIdentity;
import com.bhyte.midas.R;
import com.bhyte.midas.Transactions.AddMoneyChooseMethod;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeFragment extends Fragment {

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public static String key;
    public static String usernameS;

    String val = "visible";
    CircleImageView profilePicture;
    BottomSheetDialog bottomSheetDialog;
    RelativeLayout currencyView, verificationStatus;
    MaterialButton addMoney, continueButton;
    ImageView toggleIcon;
    String fullName;
    Animation animation;
    TextView currency, username, totalAssets, accountBalance, greetingText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Hooks
        username = root.findViewById(R.id.username);
        currencyView = root.findViewById(R.id.currency_view);
        currency = root.findViewById(R.id.currency);
        addMoney = root.findViewById(R.id.add_money_button);
        accountBalance = root.findViewById(R.id.account_balance);
        toggleIcon = root.findViewById(R.id.toggle_icon);
        verificationStatus = root.findViewById(R.id.verification_status);
        totalAssets = root.findViewById(R.id.total_assets);
        profilePicture = root.findViewById(R.id.profile_picture);
        greetingText = root.findViewById(R.id.greetings);

        // Set Profile Picture
        assert firebaseUser != null;
        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(profilePicture);
        }

        database = FirebaseDatabase.getInstance();

        // Greeting
        greetUser();
        getName();


        profilePicture.setOnClickListener(v -> startActivity(new Intent(getContext(), Profile.class)));

        verificationStatus.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SignUpVerifyIdentity.class));
            key = "no skip";
        });

        currencyView.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.currency_bottom_sheet,
                    root.findViewById(R.id.currency_sheet));

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

        });

        addMoney.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.enter_amount_bottom_sheet,
                    root.findViewById(R.id.amount_sheet));

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

            // Hooks
            continueButton = bottomSheetDialog.findViewById(R.id.continue_button);

            // Click Listeners
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkInput();
                    startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class));
                }
            });
        });

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

    private void checkInput() {
        //TODO CHECK USER INPUT AMOUNT
    }

    private void getName() {

        firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = database.getReference("Users").child(firebaseUser.getUid()).child("name");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullName = snapshot.getValue(String.class);
                setName();

                if (!(fullName == null)) {
                    usernameS = fullName;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setName() {
        username.setText(fullName + " 👋");
        if (username != null) {
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            username.setAnimation(animation);
        } else {
            return;
        }
    }

    private void greetUser() {

        Calendar calendar = Calendar.getInstance();
        int timeOfDay;
        timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12) {
            greetingText.setText(R.string.morning);
            // Set animation
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else if (timeOfDay < 16) {
            greetingText.setText(R.string.afternoon);
            // Set Animation
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else if (timeOfDay < 21) {
            greetingText.setText(R.string.evening);
            // Set Animation
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else {
            greetingText.setText(R.string.night);
            // Set Animation
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            greetingText.setAnimation(animation);
        }

    }

}