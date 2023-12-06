package com.bhyte.midas.Transactions;

import static com.bhyte.midas.Transactions.SendReceiverSuccessPage.usersName;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.DataModels.SearchedUsersModel;
import com.bhyte.midas.Database.ReadWriteAllTransactions;
import com.bhyte.midas.R;
import com.bhyte.midas.Recycler.SearchedUsersAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendReceiver extends AppCompatActivity implements SearchedUsersAdapter.OnUserSelectedListener{
    private String selectedUserName;
    private String selectedUserPhoneNumber;
    private String theSendersName;

    @Override
    public void onUserSelected(String userName, String phoneNumber) {
        this.selectedUserName = userName;
        this.selectedUserPhoneNumber = phoneNumber;
    }

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    Context context;

    ArrayList<SearchedUsersModel> searchedUsersModel;
    RecyclerView searchedUsersRecycler;
    SearchView svSearch;
    MaterialButton finalSendBtn;
    String phoneNumber;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendreceiver);

        // Retrieve the value of userInputAmount from the Intent
        Intent intent = getIntent();
        String userInputAmount = intent.getStringExtra("USER_INPUT_AMOUNT");

        this.context = getApplicationContext();
        getCurrentUserName();

        // Instance of FirebaseAuth and Database
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Hooks
        searchedUsersRecycler = findViewById(R.id.recyclerUsers);
        svSearch = findViewById(R.id.svSearch);
        finalSendBtn = findViewById(R.id.final_send_button);

        // On click listener for when you hit the Send button
        finalSendBtn.setOnClickListener(v -> {
            DatabaseReference databaseReference = database.getReference("Users");
            assert firebaseUser != null;
            databaseReference.child(firebaseUser.getUid()).child("transactions").child("sendTransactions").child(Objects.requireNonNull(databaseReference.push().getKey())).child("amount").setValue(userInputAmount);

            // Get a reference to the "transactions" collection
            DatabaseReference transactionsRef = database.getReference("Transactions");

            // Get a reference to the "sendTransactions" sub-collection
            DatabaseReference sendTransactionsRef = transactionsRef.child("sendTransactions");

            // Generate a unique ID for the new transaction
            String transactionUID = sendTransactionsRef.push().getKey();

            // Add the new transaction to the "sendTransactions" sub-collection
            assert transactionUID != null;
            sendTransactionsRef.child(transactionUID).child("amount").setValue(userInputAmount);

            // Transaction Preferences
            String transactionCurrency = "GH₵";
            // Transaction Type
            String transactionType = "Send";
            // Transaction Amount
            String transactionAmount = "- " + userInputAmount;

            // Generate Date in Wed, 4 Jul 2001 12:08 Format
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
            String transactionDate = dateFormat.format(Calendar.getInstance().getTime());

            // Save to all transactions
            ReadWriteAllTransactions readWriteAllTransactions = new ReadWriteAllTransactions(transactionType, transactionDate, transactionCurrency, transactionAmount);
            DatabaseReference allTransactionsRef = database.getReference("Users");
            allTransactionsRef.child(firebaseUser.getUid()).child("All Transactions").child(transactionUID).setValue(readWriteAllTransactions);

            // Get the users current phone number


            // This deducts the same amount from the current users main balance
            databaseReference.child(firebaseUser.getUid()).child("userMainBalance").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String currentBalance = snapshot.getValue(String.class);
                    DecimalFormat df = new DecimalFormat("0.00");
                    assert currentBalance != null;

                    // The current balance minus the amount withdrawn
                    double amountToSubtract = Double.parseDouble(currentBalance) - Double.parseDouble(userInputAmount);
                    String newBalance = Double.toString(Double.parseDouble(df.format(amountToSubtract)));

                    // Write the new balance to the database
                    databaseReference.child(firebaseUser.getUid()).child("userMainBalance").setValue(newBalance).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            System.out.println("Error updating user main balance: " + task.getException());
                        } else {
                            // Print an error message
                            System.out.println("Error updating user main balance: " + task.getException());
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Print an error message
                    System.out.println("Error retrieving user main balance: " + error.getMessage());
                }
            });

            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                DatabaseReference databasePhoneReference = database.getReference("Users").child(firebaseUser.getUid()).child("phone");
                databasePhoneReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        phoneNumber = snapshot.getValue(String.class);
                        String userOrSendersPhoneNumber = phoneNumber;

                        // Send SMS' after a successful transaction
                        sendSmsToRecipient(selectedUserPhoneNumber, userInputAmount, theSendersName);
                        //System.out.println("Selected Users Number: " + selectedUserPhoneNumber);

                        sendConfirmationSmsToSender(userOrSendersPhoneNumber, userInputAmount, selectedUserName);
                        //System.out.println("My Number: " + userOrSendersPhoneNumber);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            finish();
            startActivity(new Intent(getApplicationContext(), SendReceiverSuccessPage.class));
        });


        // Connect to the database
        if (svSearch != null) {
            svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            if (!newText.isEmpty()) {
                                // Connect to the database
                                String currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                assert firebaseUser != null;
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            searchedUsersModel = new ArrayList<>();
                                            for (DataSnapshot ds : snapshot.getChildren()) {

                                                // Removes current user from list
                                                SearchedUsersModel user = ds.getValue(SearchedUsersModel.class);
                                                assert user != null;
                                                if (!user.getMail().equals(currentUserId)) {  // check if user's ID does not match current user's ID
                                                    searchedUsersModel.add(user);  // add this user to the list of searched users
                                                }
                                            }

                                            //Filter through the users based on Name or Email
                                            ArrayList<SearchedUsersModel> myList = new ArrayList<>();
                                            for(SearchedUsersModel object : searchedUsersModel) {
                                                if(object.getMail().toLowerCase().contains(newText.toLowerCase())) {
                                                    myList.add(object);
                                                }
                                                else if(object.getName().toLowerCase().contains(newText.toLowerCase())) {
                                                    myList.add(object);
                                                }
                                            }
                                            SearchedUsersAdapter searchedUsersAdapter = new SearchedUsersAdapter(myList, finalSendBtn, svSearch, SendReceiver.this);
                                            searchedUsersRecycler.setAdapter(searchedUsersAdapter);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else {
                                // Clear the data from the RecyclerView
                                searchedUsersModel = new ArrayList<>();
                                SearchedUsersAdapter searchedUsersAdapterClass = new SearchedUsersAdapter(searchedUsersModel, finalSendBtn, svSearch, SendReceiver.this);
                                searchedUsersRecycler.setAdapter(searchedUsersAdapterClass);

                                //Set button to disabled
                                finalSendBtn.setEnabled(false);
                            }
                            return false;
                        }
                    });


                    return true;
                }
            });
        }
    }

    //Method for sending SMS to user when money is sent
    private  void sendConfirmationSmsToSender(String senderPhoneNumber, String amount, String selectedUserToSendTo) {
        new Thread(() -> {

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            JSONObject apiData = new JSONObject();

            try {
                apiData.put("api_key", "TLfITehl1SkhCoNHowco4ww1HvmLX2a2ovWbtqAu0UBv7F9UGOH2RtNoBOlJue");
                apiData.put("to", senderPhoneNumber);
                apiData.put("from", "Midas Inc");
                apiData.put("sms", amount + " GHS has been sent to " + selectedUserToSendTo);
                apiData.put("type", "plain");
                apiData.put("channel", "generic");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(apiData.toString(), mediaType);
            Request request = new Request.Builder()
                    .url("https://api.ng.termii.com/api/sms/send")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                 // This prints the response body to the console
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    };

    //Method for sending SMS to recipient
    private void sendSmsToRecipient(String recipientPhoneNumber, String amount, String senderName) {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject apiData = new JSONObject();

            try {
                apiData.put("api_key", "TLfITehl1SkhCoNHowco4ww1HvmLX2a2ovWbtqAu0UBv7F9UGOH2RtNoBOlJue");
                apiData.put("to", recipientPhoneNumber);
                apiData.put("from", "Midas Inc");
                apiData.put("sms", "You've received " + amount + " GHS from " + senderName);
                apiData.put("type", "plain");
                apiData.put("channel", "generic");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(apiData.toString(), mediaType);
            Request request = new Request.Builder()
                    .url("https://api.ng.termii.com/api/sms/send")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                // Handle the response
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // This is a method to instantly retrieve the UserID and Name from Firebase
    private void getCurrentUserName() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("name");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullName = snapshot.getValue(String.class);
                if (fullName != null && !fullName.isEmpty()) {
                    theSendersName = (fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Handle the error
            }
        });
    }

    //Formatting the users name to eg: Name N.
    private String formatName(String fullName) {
        String[] parts = fullName.split(" ");
        if (parts.length > 1) {
            // If the name has more than one part, format it to "First L."
            return parts[0] + " " + parts[1].charAt(0) + ".";
        }
        return fullName; // Return the full name if it's a single word
    }

}