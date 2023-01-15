package com.bhyte.midas.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.R;

import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> {

    Context context;
    ArrayList<TransactionsHelperClass> transactions;

    public TransactionsAdapter(Context context, ArrayList<TransactionsHelperClass> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.MyViewHolder holder, int position) {
        TransactionsHelperClass transactionsHelperClass = transactions.get(position);

        holder.transactionType.setText(transactionsHelperClass.getTransactionType());
        holder.transactionDate.setText(transactionsHelperClass.getTransactionDate());
        holder.transactionCurrency.setText(transactionsHelperClass.getTransactionCurrency());
        holder.transactionAmount.setText(transactionsHelperClass.getTransactionAmount());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transactionType, transactionDate, transactionCurrency, transactionAmount;
        //ImageView transactionImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //transactionImage = itemView.findViewById(R.id.transaction_icon);
            transactionType = itemView.findViewById(R.id.transaction_type);
            transactionDate = itemView.findViewById(R.id.transaction_date);
            transactionCurrency = itemView.findViewById(R.id.transaction_currency);
            transactionAmount = itemView.findViewById(R.id.transaction_amount);

        }
    }

}
