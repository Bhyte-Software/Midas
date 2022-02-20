package com.bhyte.midas.User;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bhyte.midas.Common.ContactSupport;
import com.bhyte.midas.R;

public class UserSettingsFragment extends Fragment {

    RelativeLayout profile, signOut, contactSupport;
    Dialog logoutDialog;
    Button positive, negative;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_settings, container, false);

        // Hooks
        profile = root.findViewById(R.id.profile_layout);
        signOut = root.findViewById(R.id.sign_out_layout);
        contactSupport = root.findViewById(R.id.contact_support);

        // Click Listeners
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