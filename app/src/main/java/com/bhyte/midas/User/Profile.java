package com.bhyte.midas.User;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    Dialog logoutDialog, dialog, rateDialog;
    private BottomSheetDialog bottomSheetDialog;
    RelativeLayout takePhoto, choosePhoto, removePhoto, rateMidas;
    CircleImageView profilePicture;
    Button positive, negative;
    MaterialButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Hooks
        logoutButton = findViewById(R.id.logout_button);
        profilePicture = findViewById(R.id.profile_picture);
        rateMidas = findViewById(R.id.rate_midas);

        // Click Listeners
        rateMidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateMidasPopup();
            }
        });

        logoutButton.setOnClickListener(v -> {
            logoutDialog = new Dialog(Profile.this, R.style.BottomSheetTheme);

            View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.logout_popup,
                    findViewById(R.id.logout_popup));

            logoutDialog.setContentView(dialogView);
            logoutDialog.show();

            //Hooks
            positive = dialogView.findViewById(R.id.cancel);
            negative = dialogView.findViewById(R.id.logout);

            positive.setOnClickListener(v1 -> logoutDialog.dismiss());
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(Profile.this, R.style.BottomSheetTheme);

                View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.profile_bottom_sheet,
                        findViewById(R.id.profile_sheet));

                bottomSheetDialog.setContentView(sheetView);

                bottomSheetDialog.show();

                // Hooks
                takePhoto = bottomSheetDialog.findViewById(R.id.take_photo);
                choosePhoto = bottomSheetDialog.findViewById(R.id.choose_photo);
                removePhoto = bottomSheetDialog.findViewById(R.id.remove_photo);

                takePhoto.setOnClickListener(v1 -> {
                    takePicture();
                });

                choosePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Open Gallery and choose photo
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, 2);

                        // Upload & Save
                        saveProfilePicture();

                        bottomSheetDialog.dismiss();
                    }
                });

                removePhoto.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onClick(View v) {

                        // Check if image is still default
                        if (profilePicture.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.profile_picture_default).getConstantState()) {
                            // Default
                            bottomSheetDialog.dismiss();
                            Toast.makeText(Profile.this, "Sorry, you cannot remove default profile picture", Toast.LENGTH_SHORT).show();
                        } else {
                            // New Image
                            showConfirmationDialog();
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
            }
        });

    }

    private void rateMidasPopup() {
        rateDialog = new Dialog(Profile.this, R.style.BottomSheetTheme);

        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.rate_popup,
                findViewById(R.id.rate_popup));

        rateDialog.setContentView(dialogView);
        rateDialog.show();

        //Hooks
        positive = dialogView.findViewById(R.id.rate);
        negative = dialogView.findViewById(R.id.cancel);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateMidas();
            }
        });
        negative.setOnClickListener(v1 -> rateDialog.dismiss());

    }

    private void rateMidas() {
        try {
            // Open App Page
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/midas")));
        } catch (ActivityNotFoundException e) {
            // Open Developer Page
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/")));
        }
    }

    private void saveProfilePicture() {
        // TODO SAVE PICTURE
    }

    private void takePicture() {
        // TODO TAKE PICTURE
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri mImageUri = data.getData();

            profilePicture.setImageURI(mImageUri);
        }

    }

    private void showConfirmationDialog() {

        dialog = new Dialog(Profile.this, R.style.BottomSheetTheme);

        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.remove_profile_picture_confirmation,
                findViewById(R.id.confirm_popup));

        dialog.setContentView(dialogView);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.show();

        // Hooks
        positive = dialog.findViewById(R.id.yes);
        negative = dialog.findViewById(R.id.cancel);

        // Click Listeners
        positive.setOnClickListener(v -> {
            // Set profile to default
            dialog.dismiss();
            profilePicture.setImageResource(R.drawable.profile_picture_default);
        });
        negative.setOnClickListener(v -> dialog.dismiss());
    }

    public void callUserMain(View view) {
        finish();
    }


    public void callInviteFriends(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String Body = "Download Midas";
        String Sub = "https://play.google.com";
        intent.putExtra(Intent.EXTRA_TEXT, Body);
        intent.putExtra(Intent.EXTRA_TEXT, Sub);
        startActivity(Intent.createChooser(intent, "Share midas using"));
    }

    public void callChangePassword(View view) {
        startActivity(new Intent(getApplicationContext(), ChangePasswordEnterEmail.class));
    }
}