package com.bhyte.midas.User;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
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

import com.bhyte.midas.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    int TAKE_IMAGE_CODE = 1001;
    int PICK_IMAGE_CODE = 1002;

    Dialog logoutDialog, dialog, rateDialog;
    private BottomSheetDialog bottomSheetDialog;
    RelativeLayout takePhoto, choosePhoto, removePhoto, rateMidas;
    CircleImageView profilePicture;
    Button positive, negative;
    TextView userName, userEmail;
    String fullName, email;
    MaterialButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Hooks
        fullName = UserHomeFragment.usernameS;
        logoutButton = findViewById(R.id.logout_button);
        userName = findViewById(R.id.user_name);
        profilePicture = findViewById(R.id.profile_picture);
        rateMidas = findViewById(R.id.rate_midas);
        userEmail = findViewById(R.id.user_email);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Set Profile Picture
        assert firebaseUser != null;
        if (firebaseUser.getPhotoUrl() != null){
            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(profilePicture);
        }

        database = FirebaseDatabase.getInstance();

        getName();
        setTextView();

        // Get Data
        fullName = UserHomeFragment.usernameS;

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
                    bottomSheetDialog.dismiss();
                });

                choosePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choosePhoto();
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
                            Toast toast = Toast.makeText(Profile.this, "Sorry, you cannot remove default profile picture", Toast.LENGTH_SHORT);
                            View view1 = toast.getView();

                            //Gets the actual oval background of the Toast then sets the colour filter
                            view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

                            //Gets the TextView from the Toast so it can be edited
                            TextView text = view1.findViewById(android.R.id.message);
                            text.setTextColor(getResources().getColor(R.color.white));

                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 90);
                            toast.show();
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

    private void choosePhoto() {
        // Open Gallery and choose photo
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        if(intent.resolveActivity(getPackageManager()) != null){
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
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                profilePicture.setImageBitmap(bitmap);
                handleUpload(bitmap);
            }
        }
        else if (requestCode == PICK_IMAGE_CODE) {
            if (resultCode == RESULT_OK) {
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

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Profile Images")
                .child(uid + ".jpeg");

        storageReference.putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(storageReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       // TODO
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast toast = Toast.makeText(Profile.this, "Profile image updated successfully", Toast.LENGTH_SHORT);
                        View view1 = toast.getView();

                        //Gets the actual oval background of the Toast then sets the colour filter
                        view1.getBackground().setColorFilter(getResources().getColor(R.color.light_green), PorterDuff.Mode.SRC_IN);

                        //Gets the TextView from the Toast so it can be edited
                        TextView text = view1.findViewById(android.R.id.message);
                        text.setTextColor(getResources().getColor(R.color.white));

                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 90);
                        toast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(Profile.this, "Failed to update profile image...", Toast.LENGTH_SHORT);
                        View view1 = toast.getView();

                        //Gets the actual oval background of the Toast then sets the colour filter
                        view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

                        //Gets the TextView from the Toast so it can be edited
                        TextView text = view1.findViewById(android.R.id.message);
                        text.setTextColor(getResources().getColor(R.color.white));

                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 90);
                        toast.show();
                    }
                });
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
        startActivity(new Intent(getApplicationContext(), ChangePasswordEnterEmail.class));
    }
}