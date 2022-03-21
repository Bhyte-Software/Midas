package com.bhyte.midas.User;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.AccountCreation.SignUpEnterNumber;
import com.bhyte.midas.Common.About;
import com.bhyte.midas.Common.ContactSupport;
import com.bhyte.midas.Common.FAQ;
import com.bhyte.midas.Common.TermsOfService;
import com.bhyte.midas.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserSettingsFragment extends Fragment {

    RelativeLayout profile, signOut, contactSupport, aboutLayout, termsLayout, faqLayout, downloadLayout;
    Dialog logoutDialog;
    Button positive, negative;
    TextView username;

    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_settings, container, false);

        // Hooks
        profile = root.findViewById(R.id.profile_layout);
        signOut = root.findViewById(R.id.sign_out_layout);
        contactSupport = root.findViewById(R.id.contact_support);
        aboutLayout = root.findViewById(R.id.about_layout);
        termsLayout = root.findViewById(R.id.terms_and_conditions);
        faqLayout = root.findViewById(R.id.faq_layout);
        username = root.findViewById(R.id.user_name);
        downloadLayout = root.findViewById(R.id.download_layout);

        // Firebase Instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Click Listeners
        aboutLayout.setOnClickListener(v -> startActivity(new Intent(getContext(), About.class)));

        termsLayout.setOnClickListener(v -> startActivity(new Intent(getContext(), TermsOfService.class)));

        faqLayout.setOnClickListener(v -> startActivity(new Intent(getContext(), FAQ.class)));

        downloadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "No data to download", Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.white));

                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 85);
                toast.show();
            }
        });

        profile.setOnClickListener(v -> startActivity(new Intent(getActivity(), Profile.class)));
        signOut.setOnClickListener(v -> {
            logoutDialog = new Dialog(getActivity(), R.style.BottomSheetTheme);

            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.logout_popup,
                    root.findViewById(R.id.logout_popup));

            logoutDialog.setContentView(dialogView);
            logoutDialog.show();

            //Hooks
            positive = dialogView.findViewById(R.id.cancel);
            negative = dialogView.findViewById(R.id.logout);

            positive.setOnClickListener(v1 -> logoutDialog.dismiss());
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutDialog.dismiss();
                    firebaseAuth.signOut();
                    startActivity(new Intent(getContext(), GetStarted.class));
                }
            });
        });

        contactSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ContactSupport.class));
            }
        });

        return root;
    }

}