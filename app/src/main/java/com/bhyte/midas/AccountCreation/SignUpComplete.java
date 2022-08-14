package com.bhyte.midas.AccountCreation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

public class SignUpComplete extends AppCompatActivity {

    private static final int TIMER = 200;

    MaterialButton hoorayButton;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_complete);

        // Hooks
        hoorayButton = findViewById(R.id.button);
        lottieAnimationView = findViewById(R.id.confetti_animation);

        // Click Listeners
        hoorayButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignIn.class)));

        // Start Animation
        lottieAnimationView.playAnimation();

        new Handler().postDelayed(this::playConfettiSound, TIMER);

    }

    private void playConfettiSound() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.pop_sound);
        mediaPlayer.start();
    }
}