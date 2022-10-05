package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.R;

public class ChangePasswordOpenEmail extends AppCompatActivity {

    ImageView emailImage;
    LinearLayout bg;
    TextView status, title, desc, resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_open_email);

        // Hooks
        bg = findViewById(R.id.bg);
        title = findViewById(R.id.check_mail);
        desc = findViewById(R.id.check_mail_description);
        status = findViewById(R.id.status);
        resend = findViewById(R.id.resend);
        emailImage = findViewById(R.id.email_image);

        // Switch Theme Based on Mode
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                darkMode();
                break;

            case Configuration.UI_MODE_NIGHT_NO | Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }

    }

    private void darkMode() {
        bg.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dark_bg));
        emailImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.email_image_dark));
        // Change Text Colors
        title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        desc.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_light));
        status.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        resend.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(getApplicationContext(), ChangePasswordSuccess.class));
    }

    public void callBack(View view) {
        finish();
    }

    public void openEmailApp(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            this.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            // Custom Toast
            Toast toast = Toast.makeText(ChangePasswordOpenEmail.this, "There is no email client installed.", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            //Gets the TextView from the Toast so it can be edited
            TextView text = view1.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));
            toast.show();
        }
    }

    public void resendEmail(View view) {
    }
}