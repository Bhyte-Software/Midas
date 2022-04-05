package com.bhyte.midas.AccountCreation;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.R;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void StartAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an Authentication Error " + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Authentication failed", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You can now access the app", true);
    }

    public void update(String s, boolean b){
        TextView dataText = ((Activity) context).findViewById(R.id.data_text);
        ImageView imageView = ((Activity) context).findViewById(R.id.fingerprint_icon);
        ProgressBar progressBar = ((Activity) context).findViewById(R.id.progress);
        int progress;
        progress = progressBar.getProgress();

        dataText.setText(s);

        if(!b){
            imageView.setImageResource(R.drawable.fingerprint_error);
        } else {
            imageView.setImageResource(R.drawable.fingerprint_success);
            // Increase Progress Smoothly to 100%
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progress);
            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        }

    }

}
