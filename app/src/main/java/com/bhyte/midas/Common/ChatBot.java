package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bhyte.midas.R;
import com.bhyte.midas.util.Common;

public class ChatBot extends AppCompatActivity {

    ImageView emojiIcon, attachmentIcon;
    EditText writeMessage;
    String userInputMessage;
    RelativeLayout sendMessage, mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        // Hooks
        mainLayout = findViewById(R.id.mainLayout);
        emojiIcon = findViewById(R.id.smile_icon);
        attachmentIcon = findViewById(R.id.attachment_icon);
        writeMessage = findViewById(R.id.write_message);
        sendMessage = findViewById(R.id.send_message);

        // Click Listener
        mainLayout.setOnClickListener(v -> {
            mainLayout.requestFocus();
            Common.hideKeyboard(ChatBot.this);
        });

        emojiIcon.setOnClickListener(v -> Toast.makeText(ChatBot.this, "Emoji Button Clicked", Toast.LENGTH_SHORT).show());

        attachmentIcon.setOnClickListener(v -> Toast.makeText(ChatBot.this, "Attachment Button Clicked", Toast.LENGTH_SHORT).show());

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