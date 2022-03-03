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

    TextToSpeech textToSpeech;
    MaterialButton readButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);

        // Hooks
        readButton = findViewById(R.id.read_button);
        textView = findViewById(R.id.txt1);

        readButton.setOnClickListener(v -> textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS){
                textToSpeech.setLanguage(Locale.ENGLISH);
                textToSpeech.setSpeechRate(1.0f);
                textToSpeech.speak(textView.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }));

    }
}