package com.bhyte.midas.Recycler;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.DataModels.SearchedUsersModel;
import com.bhyte.midas.R;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bhyte.midas.Transactions.SendReceiverSuccessPage;
import com.google.android.material.button.MaterialButton;

public class SearchedUsersAdapter extends RecyclerView.Adapter<SearchedUsersAdapter.SearchedUsersViewHolder>{

    ArrayList<SearchedUsersModel> searchedUsers;
    private MaterialButton finalSendButton;
    private SearchView searchView;
    private OnUserSelectedListener listener;


    public SearchedUsersAdapter(ArrayList<SearchedUsersModel> searchedUsers, MaterialButton finalSendButton, SearchView searchView, OnUserSelectedListener listener) {
        this.searchedUsers = searchedUsers;
        this.finalSendButton = finalSendButton;
        this.searchView = searchView;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchedUsersAdapter.SearchedUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searcheduser_list_item, parent, false);
        return new SearchedUsersAdapter.SearchedUsersViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull SearchedUsersAdapter.SearchedUsersViewHolder holder, int position) {
        SearchedUsersModel searchedUsersModel = searchedUsers.get(position);

        holder.tvUsersName.setText(searchedUsersModel.getName());
        holder.tvUsersEmail.setText(searchedUsersModel.getMail());


        // Initialize a list to store the selected items
        ArrayList<SearchedUsersModel> selectedItems = new ArrayList<>(searchedUsers);

        holder.clLayout.setOnClickListener(view -> {
            // Invoke the listener when a user is selected
            if(listener != null) {
                listener.onUserSelected(searchedUsersModel.getName(), searchedUsersModel.getPhone());
            }

            // Reset the selected flag for all items in the selectedItems list
            for (SearchedUsersModel item : selectedItems) {
                item.setSelected(false);
            }

            // Clear the selectedItems list
            selectedItems.clear();

            // Add the current item to the selectedItems list
            selectedItems.add(searchedUsersModel);

            // Set the selected flag for the current item
            searchedUsersModel.setSelected(true);

            // Notify the adapter that the data has changed
            notifyDataSetChanged();

            // Enable the send money button if selected
            finalSendButton.setEnabled(true);

            //Hide keyboard
            searchView.clearFocus();
        });

        if (searchedUsersModel.isSelected()) {
            holder.clLayout.setBackgroundColor(Color.parseColor("#70AEFF"));
            SendReceiverSuccessPage.usersName = searchedUsersModel.getName();
        } else {
            holder.clLayout.setBackgroundColor(Color.parseColor("#00000000"));
        }
}

    @Override
    public int getItemCount() {
        return searchedUsers.size();
    }

    public static class SearchedUsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnNoteListener onNoteListener;
        TextView tvUsersName, tvUsersEmail;
        ConstraintLayout clLayout;

        public SearchedUsersViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            tvUsersName = itemView.findViewById(R.id.tvUsersName);
            tvUsersEmail = itemView.findViewById(R.id.tvUsersEmail);
            clLayout = itemView.findViewById(R.id.clLayout);
        }

        @Override
        public void onClick(View view) {
            //onNoteListener.onNoteClick(getAbsoluteAdapterPosition());
        }

    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    //An interface to pass the selected users name
    public interface OnUserSelectedListener {
        void onUserSelected(String userName, String phoneNumber);
    }

}
