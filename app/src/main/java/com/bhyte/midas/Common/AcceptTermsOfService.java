package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class AcceptTermsOfService extends AppCompatActivity {

    Dialog acceptTermsDialog;
    MaterialButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_terms_of_service);
    }

    public void callPopup(View view) {
        acceptTermsOfServicePopup();
    }

    private void acceptTermsOfServicePopup() {
        acceptTermsDialog = new Dialog(AcceptTermsOfService.this, R.style.BottomSheetTheme);

        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.accept_terms_popup, findViewById(R.id.accept_terms_popup));

        acceptTermsDialog.setContentView(dialogView);
        acceptTermsDialog.show();

        // Hooks
        cancelButton = acceptTermsDialog.findViewById(R.id.cancel);

        // Click Listeners
        cancelButton.setOnClickListener(v -> {
            acceptTermsDialog.dismiss();
        });
    }
}