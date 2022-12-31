package com.bhyte.midas.Transactions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.DataModels.SearchedUsersModel;
import com.bhyte.midas.R;
import com.bhyte.midas.Recycler.QuickActionsAdapter;
import com.bhyte.midas.Recycler.QuickActionsHelperClass;
import com.bhyte.midas.Recycler.SearchedUsersAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class SendReceiver extends AppCompatActivity implements QuickActionsAdapter.OnNoteListener{
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    Context context;

    RecyclerView.Adapter<?> adapter;
    RecyclerView.Adapter<?> platformsAdapter;
    ArrayList<SearchedUsersAdapter> viewSearchedUsers = new ArrayList<>();
    ArrayList<SearchedUsersModel> searchedUsersModel;
    RecyclerView searchedUsersRecycler;
    SearchView svSearch;
    MaterialButton finalSendBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendreceiver);

        this.context = getApplicationContext();

        // Instance of FirebaseAuth and Database
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        searchedUsersRecycler = findViewById(R.id.recyclerUsers);
        svSearch = findViewById(R.id.svSearch);
        finalSendBtn = findViewById(R.id.final_send_button);

        svSearch.setQueryHint("Enter name or email of user");

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Connect to the database

        if (svSearch != null) {
            //svSearch.setQueryHint("Enter name or email of user");
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
                                            SearchedUsersAdapter searchedUsersAdapter = new SearchedUsersAdapter(myList, finalSendBtn);
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
                                SearchedUsersAdapter searchedUsersAdapterClass = new SearchedUsersAdapter(searchedUsersModel, finalSendBtn);
                                searchedUsersRecycler.setAdapter(searchedUsersAdapterClass);

                                //Set buttoo to disabled
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

    @Override
    public void onNoteClick(int position) {

    }
}