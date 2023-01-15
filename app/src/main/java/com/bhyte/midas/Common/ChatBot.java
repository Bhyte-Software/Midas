package com.bhyte.midas.Common;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bhyte.midas.R;
import com.bhyte.midas.Util.Common;

public class ChatBot extends AppCompatActivity {

    EditText writeMessage;
    String userInputMessage;
    RelativeLayout sendMessage, mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        // Hooks
        mainLayout = findViewById(R.id.mainLayout);
        writeMessage = findViewById(R.id.write_message);
        sendMessage = findViewById(R.id.send_message);

        // Click Listener
        mainLayout.setOnClickListener(v -> {
            mainLayout.requestFocus();
            Common.hideKeyboard(ChatBot.this);
        });

        sendMessage.setOnClickListener(v -> {
            // Send Message
            sendMessage();
            Common.hideKeyboard(ChatBot.this);
            mainLayout.requestFocus();
        });

    }

    private void sendMessage() {
        // Get Input
        userInputMessage = writeMessage.getText().toString();

        // Clear Input
        writeMessage.getText().clear();
    }

    public void callBack(View view) {
        finish();
    }
}