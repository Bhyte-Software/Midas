package com.bhyte.midas.Transactions;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.HelperClasses.CustomAdapter;
import com.bhyte.midas.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddMoneyChooseProvider extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    EditText autoPhoneNumber;
    String phoneNumber;
    public static String networkName;
    BottomSheetDialog bottomSheetDialog;
    String[] networkNames ={"MTN GH","Vodafone GH"};
    int[] logos = {R.drawable.mtn, R.drawable.vodaphone};
    ImageView back, help;
    MaterialButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_choose_provider);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Hooks
        autoPhoneNumber = findViewById(R.id.none_field);
        back = findViewById(R.id.back);
        help = findViewById(R.id.deposit_help);
        nextButton = findViewById(R.id.next);

        // Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),logos,networkNames);
        spin.setAdapter(customAdapter);

        // Data from Firebase
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            DatabaseReference databaseReference = database.getReference("Users").child(firebaseUser.getUid()).child("phone");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    phoneNumber = snapshot.getValue(String.class);
                    assert phoneNumber != null;
                    phoneNumber = phoneNumber.substring(4);
                    if (!(autoPhoneNumber == null)) {
                        phoneNumber = "+" + "233" + phoneNumber;
                        autoPhoneNumber.setText(phoneNumber);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        // Click Listeners
        autoPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Contact Support
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    Toast toast = Toast.makeText(AddMoneyChooseProvider.this, R.string.update_number, Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view1.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
                    TextView textView = layout.findViewById(R.id.text);
                    textView.setText(R.string.update_number);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        back.setOnClickListener(v -> finish());

        nextButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ReviewTransaction.class)));

        help.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(AddMoneyChooseProvider.this, R.style.BottomSheetTheme);
            View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_money_help_bottom_sheet, findViewById(R.id.add_money_help));
            bottomSheetDialog.setContentView(sheetView);

            bottomSheetDialog.show();

            // Hooks
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        networkName = networkNames[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}