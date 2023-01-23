package com.bhyte.midas.AccountCreation;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bhyte.midas.Common.NoInternet;
import com.bhyte.midas.R;
import com.bhyte.midas.Util.CheckInternetConnection;
import com.bhyte.midas.Util.Common;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpEnterNumber extends AppCompatActivity {

    private static final int TIMER = 1500;
    public static String fullPhoneNumber;
    public static String completeNumber;
    public static String country;
    public static String pinID;
    public String countryCode;
    public String phoneNumber;
    public int lengthOfVal;
    public Context context;
    EditText enterNumberLayout;
    RelativeLayout getCode;
    ImageView registerImage;
    LottieAnimationView lottieAnimationView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enter_number);

        // Error Fix for Check Internet Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Hooks
        registerImage = findViewById(R.id.register_image);
        enterNumberLayout = findViewById(R.id.input_number_field);
        getCode = findViewById(R.id.button);
        lottieAnimationView = findViewById(R.id.button_animation);
        textView = findViewById(R.id.button_text);

        getCode.setOnClickListener(v -> {
            if (CheckInternetConnection.isConnected(SignUpEnterNumber.this)) {
                // Make animation visible
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                // Make text invisible
                textView.setVisibility(View.GONE);
                // Handler
                new Handler().postDelayed(this::resetButton, TIMER);
            } else {
                startActivity(new Intent(getApplicationContext(), NoInternet.class));
            }
        });

        /* Detect soft keyboard and hide
        final View activityRootView = findViewById(R.id.main_layout);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            //r will be populated with the coordinates of your view that area still visible.
            activityRootView.getWindowVisibleDisplayFrame(r);

            int heightDiff = activityRootView.getRootView().getHeight() - r.height();
            if (heightDiff > 0.25*activityRootView.getRootView().getHeight()) {
                // if more than 25% of the screen, its probably a keyboard...
                registerImage.setVisibility(View.GONE);
            } else {
                registerImage.setVisibility(View.VISIBLE);
            }
        });
         */

    }

    private void resetButton() {
        // Make animation invisible
        lottieAnimationView.pauseAnimation();
        lottieAnimationView.setVisibility(View.GONE);
        // Make text visible
        textView.setVisibility(View.VISIBLE);
        // Start New Activity
        getCode();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        checkInputLayout();
    }

    public void checkInputLayout() {
        phoneNumber = enterNumberLayout.getText().toString().trim();
        lengthOfVal = phoneNumber.length();

        if (lengthOfVal == 10) {
            enterNumberLayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ghana_icon, 0, R.drawable.green_tick, 0);
            // Hide Keyboard
            Common.hideKeyboard(SignUpEnterNumber.this);
        } else {
            enterNumberLayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ghana_icon, 0, 0, 0);
            // Show Keyboard
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void getCode() {
        if (lengthOfVal == 10) {
            countryCode = "233";
            completeNumber = phoneNumber.substring(1);
            fullPhoneNumber = "+" + countryCode + completeNumber;

            country = "Ghana";

            // Initialize http client
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            JSONObject apiData = new JSONObject();

            try {
                apiData.put("api_key", "TLFFfMS22bquNxA0cDHLrEkX7h0zbcZvD0fTmw0nWEiRWokOAykqlnQXnI3ds2");
                apiData.put("message_type", "NUMERIC");
                apiData.put("to", fullPhoneNumber);
                apiData.put("from", "Midas Inc");
                apiData.put("channel", "generic");
                apiData.put("pin_attempts", "1");
                apiData.put("pin_time_to_live", "5");
                apiData.put("pin_length", "6");
                apiData.put("pin_placeholder", "< 1234 >");
                apiData.put("message_text", "Your Midas verification code is: < 1234 > Do not share it with anyone.");
                apiData.put("pin_type", "NUMERIC");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(apiData.toString(), mediaType);
            Request request = new Request.Builder()
                    .url("https://api.ng.termii.com/api/sms/otp/send")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                Response response = client.newCall(request).execute();

                JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                pinID = json.getString("pinId");

                //System.out.println(Objects.requireNonNull(response.body()).string());
                //System.out.println(pinID);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(getApplicationContext(), SignUpEnterOTP.class));
            finish();
        } else if (lengthOfVal == 0) {
            // Custom Toast for Android Versions < 11
            Toast toast = Toast.makeText(SignUpEnterNumber.this, R.string.enter_number_to_continue, Toast.LENGTH_SHORT);
            LinearLayout layout = (LinearLayout) toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            layout.getBackground().setColorFilter(ContextCompat.getColor(SignUpEnterNumber.this, R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = layout.findViewById(android.R.id.message);
            text.setTextColor(ContextCompat.getColor(SignUpEnterNumber.this, R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
        } else {
            Toast toast = Toast.makeText(SignUpEnterNumber.this, R.string.enter_valid_number, Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpEnterNumber.this, R.color.red), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(ContextCompat.getColor(SignUpEnterNumber.this, R.color.white));

            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
            toast.show();
        }
    }

}