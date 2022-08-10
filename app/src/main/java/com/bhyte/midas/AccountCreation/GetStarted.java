package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.Common.NoInternet;
import com.bhyte.midas.R;
import com.bhyte.midas.User.FAQ;
import com.bhyte.midas.util.CheckInternetConnection;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;

public class GetStarted extends AppCompatActivity {

    MaterialButton createAccountButton, signInButton;
    FirebaseAnalytics firebaseAnalytics;
    BottomSheetDialog countryBottomSheet;
    RelativeLayout countryGhana, countryNigeria, closeLayout;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        // Error Fix for Check Internet Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Hooks
        createAccountButton = findViewById(R.id.create_account_button);
        signInButton = findViewById(R.id.sign_in_button);

        // Initialize Bottom Sheet
        countryBottomSheet = new BottomSheetDialog(GetStarted.this, R.style.BottomSheetTheme);
        View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.country_bottom_sheet,
                findViewById(R.id.country_sheet));
        countryBottomSheet.setContentView(sheetView);

        // Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Click Listeners
        signInButton.setOnClickListener(v -> {
            if (CheckInternetConnection.isConnected(GetStarted.this)) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            } else {
                startActivity(new Intent(getApplicationContext(), NoInternet.class));
            }
        });
        createAccountButton.setOnClickListener(v -> {

            countryBottomSheet.show();

            // Hooks
            closeLayout = countryBottomSheet.findViewById(R.id.close_layout);
            countryGhana = countryBottomSheet.findViewById(R.id.ghana);
            countryNigeria = countryBottomSheet.findViewById(R.id.nigeria);

            countryGhana.setOnClickListener(v12 -> {
                if (CheckInternetConnection.isConnected(GetStarted.this)) {
                    countryBottomSheet.dismiss();
                    startActivity(new Intent(getApplicationContext(), SignUpEnterNumber.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), NoInternet.class));
                }
            });
            countryNigeria.setOnClickListener(v1 -> {
                countryBottomSheet.dismiss();
                // Custom Toast
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    Toast toast = Toast.makeText(GetStarted.this, R.string.no_available_ng, Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                    TextView textView = layout.findViewById(R.id.text);
                    textView.setText(R.string.no_available_ng);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            });
            closeLayout.setOnClickListener(v13 -> countryBottomSheet.dismiss());
        });
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(0);
        } else {
            // Make Custom Toast Instead
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                Toast toast = Toast.makeText(GetStarted.this, "Press back again to exit midas", Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                TextView textView = layout.findViewById(R.id.text);
                textView.setText(R.string.back_to_exit);

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        }
        pressedTime = System.currentTimeMillis();
    }

}