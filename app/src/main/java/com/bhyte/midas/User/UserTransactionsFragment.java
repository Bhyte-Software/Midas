package com.bhyte.midas.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.R;
import com.bhyte.midas.Recycler.TransactionsAdapter;
import com.bhyte.midas.Recycler.TransactionsHelperClass;
import com.bhyte.midas.Transactions.AddMoneyChooseMethod;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserTransactionsFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference2;
    TransactionsAdapter transactionsAdapter;
    ArrayList<TransactionsHelperClass> list;

    ShimmerFrameLayout shimmerFrameLayout;
    RelativeLayout headerLayout;
    ImageView noTransactionsImage;
    TextView title, desc;

    String doesUserHaveTransaction;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    MaterialButton addMoneyButton;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_transactions, container, false);

        this.context = getContext();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Hooks
        recyclerView = root.findViewById(R.id.transactions_recycler);
        headerLayout = root.findViewById(R.id.header);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout);
        noTransactionsImage = root.findViewById(R.id.no_trans_img);
        title = root.findViewById(R.id.title);
        addMoneyButton = root.findViewById(R.id.add_money_button);
        desc = root.findViewById(R.id.description);

        databaseReference2 = firebaseDatabase.getReference("Users").child(firebaseUser.getUid()).child("All Transactions");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        list = new ArrayList<>();
        transactionsAdapter = new TransactionsAdapter(getContext(), list);
        recyclerView.setAdapter(transactionsAdapter);

        // Does user have any transactions
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseUser.getUid()).child("transaction");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doesUserHaveTransaction = snapshot.getValue(String.class);
                assert doesUserHaveTransaction != null;
                if(doesUserHaveTransaction.equals("True")){
                    // Show default view and hide transactions
                    noTransactionsImage.setVisibility(View.GONE);
                    title.setVisibility(View.GONE);
                    desc.setVisibility(View.GONE);
                    addMoneyButton.setVisibility(View.GONE);
                    headerLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.startShimmer();

                }else if (doesUserHaveTransaction.equals("False")) {
                    // Hide default view and show transactions
                    headerLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    noTransactionsImage.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    desc.setVisibility(View.VISIBLE);
                    addMoneyButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    TransactionsHelperClass transactionsHelperClass = dataSnapshot.getValue(TransactionsHelperClass.class);
                    list.add(transactionsHelperClass);

                    // Wait before showing transactions
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        recyclerView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                    }, 2000);   //5 seconds

                }
                transactionsAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Click Listeners
        addMoneyButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class)));


        return root;
    }

}