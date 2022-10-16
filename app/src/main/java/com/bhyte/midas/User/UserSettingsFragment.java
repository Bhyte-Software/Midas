package com.bhyte.midas.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.Common.About;
import com.bhyte.midas.Common.AppTheme;
import com.bhyte.midas.Common.ContactSupport;
import com.bhyte.midas.Common.TermsOfService;
import com.bhyte.midas.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

public class UserSettingsFragment extends Fragment {

    RelativeLayout profile, signOut, contactSupport, aboutLayout, termsLayout, faqLayout, downloadLayout, themeLayout;
    BottomSheetDialog logoutDialog;
    Button positive, negative;
    String userFullName;
    ImageView themeIcon;
    TextView username;
    Context context;

    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_settings, container, false);

        this.context = getContext();

        // Hooks
        themeLayout = root.findViewById(R.id.theme_layout);
        profile = root.findViewById(R.id.profile_layout);
        signOut = root.findViewById(R.id.sign_out_layout);
        contactSupport = root.findViewById(R.id.contact_support);
        aboutLayout = root.findViewById(R.id.about_layout);
        termsLayout = root.findViewById(R.id.terms_and_conditions);
        faqLayout = root.findViewById(R.id.faq_layout);
        username = root.findViewById(R.id.user_name);
        downloadLayout = root.findViewById(R.id.download_layout);
        themeIcon = root.findViewById(R.id.theme);

        // Get Data
        userFullName = UserHomeFragment.usernameS;

        // Theme
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themeIcon.setImageResource(R.drawable.moon_icon);
        } else {
            themeIcon.setImageResource(R.drawable.sun_icon);
        }

        // Firebase Instance
        firebaseAuth = FirebaseAuth.getInstance();
        username.setText(userFullName);

        // Click Listeners
        themeLayout.setOnClickListener(v -> startActivity(new Intent(getContext(), AppTheme.class)));

        aboutLayout.setOnClickListener(v -> startActivity(new Intent(getContext(), About.class)));

        termsLayout.setOnClickListener(v -> startActivity(new Intent(getContext(), TermsOfService.class)));

        faqLayout.setOnClickListener(v -> startActivity(new Intent(getActivity(), FAQ.class)));

        downloadLayout.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getContext(), R.string.no_data, Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);
            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(ContextCompat.getColor(context, R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
        });

        profile.setOnClickListener(v -> startActivity(new Intent(context, Profile.class)));
        signOut.setOnClickListener(v -> {
            logoutDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);

            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.logout_popup,
                    root.findViewById(R.id.logout_popup));

            logoutDialog.setContentView(dialogView);
            logoutDialog.show();

            //Hooks
            positive = dialogView.findViewById(R.id.cancel);
            negative = dialogView.findViewById(R.id.logout);

            positive.setOnClickListener(v1 -> logoutDialog.dismiss());
            negative.setOnClickListener(v12 -> {
                logoutDialog.dismiss();
                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), GetStarted.class));
            });
        });

        contactSupport.setOnClickListener(v -> startActivity(new Intent(getContext(), ContactSupport.class)));

        return root;
    }

}