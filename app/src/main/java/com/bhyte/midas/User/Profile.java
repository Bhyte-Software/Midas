package com.bhyte.midas.User;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    Dialog logoutDialog, dialog;
    private BottomSheetDialog bottomSheetDialog;
    RelativeLayout takePhoto, choosePhoto, removePhoto;
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

        // Click Listeners
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
                    //Open Camera app and take photo
                    //takePictureAndUpload();

                });

                choosePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Open Gallery and choose photo
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, 2);

                        // Upload & Save Picture

                        bottomSheetDialog.dismiss();
                    }
                });

                removePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //showConfirmationDialog();
                    }
                });
            }
        });

    }

    private void takePictureAndUpload() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri mImageUri = data.getData();

            profilePicture.setImageURI(mImageUri);
        }

    }

    private void showConfirmationDialog() {

        dialog = new Dialog(Profile.this, R.style.BottomSheetTheme);

        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.logout_popup,
                findViewById(R.id.logout_popup));

        dialog.setContentView(dialogView);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.show();

        // Hooks


        // Click Listeners
    }

    public void callUserMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainDashboard.class));
    }


}