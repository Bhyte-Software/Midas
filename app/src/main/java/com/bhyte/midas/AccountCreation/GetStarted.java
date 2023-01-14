package com.bhyte.midas.AccountCreation;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bhyte.midas.Common.NoInternet;
import com.bhyte.midas.R;
import com.bhyte.midas.Util.CheckInternetConnection;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.Console;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetStarted extends AppCompatActivity {

    LottieAnimationView signInButtonAnimation;
    MaterialButton createAccountButton, signInButton;
    FirebaseAnalytics firebaseAnalytics;
    BottomSheetDialog countryBottomSheet;
    RelativeLayout countryGhana, countryNigeria, closeLayout;

    Context context;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_get_started);

        this.context = getApplicationContext();

        // Error Fix for Check Internet Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ExecutorService service = Executors.newSingleThreadExecutor();

        // Hooks
        signInButtonAnimation = findViewById(R.id.sign_in_button_animation);
        createAccountButton = findViewById(R.id.create_account_button);
        signInButton = findViewById(R.id.sign_in_button);

        // Initialize Bottom Sheet
        countryBottomSheet = new BottomSheetDialog(GetStarted.this, R.style.BottomSheetTheme);
        View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.country_bottom_sheet, findViewById(R.id.country_sheet));
        countryBottomSheet.setContentView(sheetView);

        // Click Listeners
        signInButton.setOnClickListener(v -> {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    //boolean isConnected = CheckInternetConnection.isConnected(context);
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            signInButtonAnimation.setVisibility(View.VISIBLE);
                            signInButtonAnimation.playAnimation();
                            signInButton.setText("");
                            Handler handler = new Handler();
                            if (isConnected) {
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        signInButtonAnimation.setVisibility(View.GONE);
                                        signInButtonAnimation.pauseAnimation();
                                        signInButton.setText(R.string.sign_in);
                                        startActivity(new Intent(getApplicationContext(), SignIn.class));
                                    }
                                }, 3500);   //5 seconds
                            } else {
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        signInButtonAnimation.setVisibility(View.GONE);
                                        signInButtonAnimation.pauseAnimation();
                                        signInButton.setText(R.string.sign_in);
                                        startActivity(new Intent(getApplicationContext(), NoInternet.class));
                                    }
                                }, 5000);   //5 seconds
                            }
                        }
                    });
                }
            });
        });

        createAccountButton.setOnClickListener(v -> {
            countryBottomSheet.show();

            // Hooks
            closeLayout = countryBottomSheet.findViewById(R.id.close_layout);
            countryGhana = countryBottomSheet.findViewById(R.id.ghana);
            countryNigeria = countryBottomSheet.findViewById(R.id.nigeria);

            countryGhana.setOnClickListener(v12 -> {
                /*if (CheckInternetConnection.isConnected(GetStarted.this)) {
                    countryBottomSheet.dismiss();
                    startActivity(new Intent(getApplicationContext(), SignUpCredentials.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), NoInternet.class));
                }*/

                // This can be refactored later
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {
                    // Do something here
                    countryBottomSheet.dismiss();
                    startActivity(new Intent(getApplicationContext(), SignUpEnterNumber.class));
                } else {
                    // No internet connection
                    startActivity(new Intent(getApplicationContext(), NoInternet.class));
                }
            });
            countryNigeria.setOnClickListener(v1 -> {
                countryBottomSheet.dismiss();
                // Custom Toast
                Toast toast = Toast.makeText(GetStarted.this, R.string.no_available_ng, Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            });
            closeLayout.setOnClickListener(v13 -> countryBottomSheet.dismiss());
        });
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            System.exit(0);
        } else {
            Toast toast = Toast.makeText(GetStarted.this, "Press back again to exit midas", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
        }
        pressedTime = System.currentTimeMillis();
    }

}