package com.bhyte.midas.User;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    Context context;

    int TAKE_IMAGE_CODE = 1001;
    int PICK_IMAGE_CODE = 1002;


    BottomSheetDialog rateDialog;
    Dialog logoutDialog, dialog;
    RelativeLayout takePhoto, choosePhoto, removePhoto, rateMidas, languageLayout;
    CircleImageView profilePicture;
    Button positive, negative;
    TextView userName, userEmail;
    String fullName, email;
    MaterialButton logoutButton;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Hooks
        fullName = UserHomeFragment.usernameS;
        logoutButton = findViewById(R.id.logout_button);
        languageLayout = findViewById(R.id.language_layout);
        userName = findViewById(R.id.user_name);
        profilePicture = findViewById(R.id.profile_picture);
        rateMidas = findViewById(R.id.rate_midas);
        userEmail = findViewById(R.id.user_email);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Set Profile Picture
        assert firebaseUser != null;
        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(profilePicture);
        }

        database = FirebaseDatabase.getInstance();
        this.context = getApplicationContext();

        getName();
        setTextView();

        // Get Data
        fullName = UserHomeFragment.usernameS;

        // Click Listeners
        rateMidas.setOnClickListener(v -> rateMidasPopup());

        languageLayout.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(Profile.this, R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(context).inflate(R.layout.language_bottom_sheet,
                    findViewById(R.id.language_sheet));

            bottomSheetDialog.setContentView(sheetView);

            bottomSheetDialog.show();
        });

        logoutButton.setOnClickListener(v -> {
            logoutDialog = new Dialog(Profile.this, R.style.BottomSheetTheme);

            View dialogView = LayoutInflater.from(context).inflate(R.layout.logout_popup,
                    findViewById(R.id.logout_popup));

            logoutDialog.setContentView(dialogView);
            logoutDialog.show();

            //Hooks
            positive = dialogView.findViewById(R.id.cancel);
            negative = dialogView.findViewById(R.id.logout);

            positive.setOnClickListener(v1 -> logoutDialog.dismiss());
            negative.setOnClickListener(v14 -> {
                logoutDialog.dismiss();
                firebaseAuth.signOut();
                startActivity(new Intent(Profile.this, GetStarted.class));
            });
        });

        profilePicture.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(Profile.this, R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(context).inflate(R.layout.profile_bottom_sheet,
                    findViewById(R.id.profile_sheet));

            bottomSheetDialog.setContentView(sheetView);

            bottomSheetDialog.show();

            // Hooks
            takePhoto = bottomSheetDialog.findViewById(R.id.take_photo);
            choosePhoto = bottomSheetDialog.findViewById(R.id.choose_photo);
            removePhoto = bottomSheetDialog.findViewById(R.id.remove_photo);

            takePhoto.setOnClickListener(v1 -> {
                takePicture();
                bottomSheetDialog.dismiss();
            });

            choosePhoto.setOnClickListener(v13 -> {
                choosePhoto();
                bottomSheetDialog.dismiss();
            });

            removePhoto.setOnClickListener(v12 -> {
                // Check if image is still default
                if (profilePicture.getDrawable().getConstantState() == Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.profile_picture_default)).getConstantState()) {
                    // Default
                    bottomSheetDialog.dismiss();
                    Toast toast = Toast.makeText(Profile.this, "You cannot remove default picture", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(context, R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                } else {
                    // New Image
                    showConfirmationDialog();
                    bottomSheetDialog.dismiss();
                }
            });
        });

    }

    private void choosePhoto() {
        // Open Gallery and choose photo
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_IMAGE_CODE);
        }
    }

    private void getName() {

        firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = database.getReference("Users").child(firebaseUser.getUid()).child("mail");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.getValue(String.class);

                if (!(email == null)) {
                    userEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setTextView() {
        userName.setText(fullName);
    }

    private void rateMidasPopup() {
        rateDialog = new BottomSheetDialog(Profile.this, R.style.BottomSheetTheme);

        View sheetView = LayoutInflater.from(context).inflate(R.layout.rate_popup,
                findViewById(R.id.rate_popup));

        rateDialog.setContentView(sheetView);
        rateDialog.show();

        //Hooks
        positive = sheetView.findViewById(R.id.rate);
        negative = sheetView.findViewById(R.id.cancel);

        positive.setOnClickListener(v -> rateMidas());
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

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_IMAGE_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                profilePicture.setImageBitmap(bitmap);
                handleUpload(bitmap);
            }
        } else if (requestCode == PICK_IMAGE_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri mImageUri = data.getData();
                profilePicture.setImageURI(mImageUri);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert bitmap != null;
                handleUpload(bitmap);

            }
        }

    }

    // Upload Image to Firebase
    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        String uid = firebaseUser.getUid();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Profile Images")
                .child(uid + ".jpeg");

        storageReference.putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(taskSnapshot -> getDownloadUrl(storageReference))
                .addOnFailureListener(e -> {
                    // Toast
                    Toast toast = Toast.makeText(Profile.this, "Error uploading picture", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(context, R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                });
    }

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(this::setUserProfileUrl);
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        assert user != null;
        user.updateProfile(request)
                .addOnSuccessListener(unused -> {
                    Toast toast = Toast.makeText(Profile.this, "Profile updated successfully", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.light_green), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(context, R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                })
                .addOnFailureListener(e -> {
                    Toast toast = Toast.makeText(Profile.this, "Failed to update profile", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(context, R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();

                });
    }

    private void showConfirmationDialog() {

        dialog = new Dialog(Profile.this, R.style.BottomSheetTheme);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.remove_profile_picture_confirmation,
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

            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.profile_picture_default);
            profilePicture.setImageResource(R.drawable.profile_picture_default);
            handleUpload(bitmap);

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
        startActivity(new Intent(context, ChangePasswordEnterEmail.class));
    }

    public void callEditProfile(View view) {
        startActivity(new Intent(context, EditProfile.class));
    }

}