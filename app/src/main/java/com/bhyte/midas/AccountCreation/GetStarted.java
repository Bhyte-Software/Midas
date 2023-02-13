package com.bhyte.midas.AccountCreation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import com.airbnb.lottie.LottieAnimationView;
import com.bhyte.midas.Common.NoInternet;
import com.bhyte.midas.Common.OnBoarding;
import com.bhyte.midas.R;
import com.bhyte.midas.Util.CheckInternetConnection;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;

public class GetStarted extends AppCompatActivity {


    private static final int SPLASH_TIMER = 1;
    SharedPreferences onBoardingScreen;

    LottieAnimationView signInButtonAnimation;
    MaterialButton createAccountButton, signInButton;
    FirebaseAnalytics firebaseAnalytics;
    BottomSheetDialog countryBottomSheet;
    RelativeLayout countryGhana, countryNigeria, closeLayout;

    Context context;
    private long pressedTime;


    private boolean keep = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        // Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_get_started);

        // Keep returning false to Should Keep On Screen until ready to begin.
        splashScreen.setKeepOnScreenCondition(() -> keep);

        // Splash
        new Handler().postDelayed(() -> {
            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
            boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

            // Check if it's users first time
            if (isFirstTime) {
                SharedPreferences.Editor editor = onBoardingScreen.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                keep = false;
                startActivity(intent);
                finish();
            } else {
                keep = false;
            }
        }, SPLASH_TIMER);
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

            signInButtonAnimation.setVisibility(View.VISIBLE);
            signInButtonAnimation.playAnimation();
            signInButton.setText("");


            new Thread(() -> {
                boolean isConnected = CheckInternetConnection.isConnected(getApplicationContext());

                try {
                    Thread.sleep(1000);
                    runOnUiThread(() -> {
                        signInButtonAnimation.setVisibility(View.GONE);
                        signInButtonAnimation.pauseAnimation();
                        signInButton.setText(R.string.sign_in);
                        if (isConnected) {
                            startActivity(new Intent(getApplicationContext(), SignIn.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), NoInternet.class));
                        }
                    });
                } catch (final Exception exception) {
                    Log.i("---", "Exception in thread");
                }
            }).start();

            /* Improper Implementation
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
             */
        });

        createAccountButton.setOnClickListener(v -> {
            countryBottomSheet.show();

            // Hooks
            closeLayout = countryBottomSheet.findViewById(R.id.close_layout);
            countryGhana = countryBottomSheet.findViewById(R.id.ghana);
            countryNigeria = countryBottomSheet.findViewById(R.id.nigeria);

            countryGhana.setOnClickListener(v12 -> {

                boolean isConnected = CheckInternetConnection.isConnected(getApplicationContext());

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