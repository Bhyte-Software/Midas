package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;
import com.chaos.view.PinView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpEnterOTP extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    public String verificationId;
    public String fullPhoneNumber;
    public String smsNumber;
    public String completeNumber;
    String pinId = SignUpEnterNumber.pinID;

    TextView verify_description;
    PinView pinView;
    MaterialButton verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enter_otp);

        // FirebaseAuth Instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Hooks
        verify_description = findViewById(R.id.verification_description);
        pinView = findViewById(R.id.pin_view);
        verifyButton = findViewById(R.id.verify);

        // Get Data from Previous Activity
        fullPhoneNumber = SignUpEnterNumber.fullPhoneNumber;
        smsNumber = SignUpEnterNumber.fullPhoneNumber;
        completeNumber = SignUpEnterNumber.completeNumber;

        // Delete Country Code
        fullPhoneNumber = fullPhoneNumber.substring(4);

        // Append
        verify_description.append(" " + "+(233)" + fullPhoneNumber);

        verifyButton.setOnClickListener(v -> {
            if (pinView.getText() == null) {
                Toast toast = Toast.makeText(SignUpEnterOTP.this, R.string.enter_sms, Toast.LENGTH_SHORT);
                View view1 = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view1.getBackground().setColorFilter(ContextCompat.getColor(SignUpEnterOTP.this, R.color.red), PorterDuff.Mode.SRC_IN);

                //Gets the TextView from the Toast so it can be edited
                TextView text = view1.findViewById(android.R.id.message);
                text.setTextColor(ContextCompat.getColor(SignUpEnterOTP.this, R.color.white));

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                toast.show();
            } else if (pinView.length() == 6) {
                // The verification Code
                String code = pinView.getText().toString().trim();

                //Verifying code gotten from Termii Client
                // Initialize http client
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                JSONObject apiData = new JSONObject();

                try {
                    apiData.put("api_key", "TLFFfMS22bquNxA0cDHLrEkX7h0zbcZvD0fTmw0nWEiRWokOAykqlnQXnI3ds2");
                    apiData.put("pin_id", pinId);
                    apiData.put("pin", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(apiData.toString(), mediaType);
                Request request = new Request.Builder()
                        .url("https://api.ng.termii.com/api/sms/otp/verify")
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                    if (json.getString("verified").equals("true")) {
                        // Send to SignUpCredentials page
                        startActivity(new Intent(getApplicationContext(), SignUpCredentials.class));
                    } else {
                        System.out.println("That ain't it bub");
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
