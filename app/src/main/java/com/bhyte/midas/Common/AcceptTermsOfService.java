package com.bhyte.midas.Common;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.AccountCreation.SignUpComplete;
import com.bhyte.midas.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

public class AcceptTermsOfService extends AppCompatActivity {

    BottomSheetDialog acceptTermsDialog;
    MaterialButton cancelButton, declineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_terms_of_service);

        // Hooks
        declineButton = findViewById(R.id.decline_button);
    }

    public void callPopup(View view) {
        acceptTermsOfServicePopup();
    }

    private void acceptTermsOfServicePopup() {
        acceptTermsDialog = new BottomSheetDialog(AcceptTermsOfService.this, R.style.BottomSheetTheme);

        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.accept_terms_popup, findViewById(R.id.accept_terms_popup));

        acceptTermsDialog.setContentView(dialogView);
        acceptTermsDialog.show();

        // Hooks
        cancelButton = acceptTermsDialog.findViewById(R.id.cancel);

        // Click Listeners
        assert cancelButton != null;
        cancelButton.setOnClickListener(v -> acceptTermsDialog.dismiss());
    }

    public void callSignIn(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpComplete.class));
    }
}