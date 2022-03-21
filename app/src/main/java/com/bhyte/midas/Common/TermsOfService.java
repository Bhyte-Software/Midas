package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import com.bhyte.midas.R;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class TermsOfService extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);

        // Hooks
        textView = findViewById(R.id.txt1);
    }
}