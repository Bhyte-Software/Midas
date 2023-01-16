package com.bhyte.midas.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

        String transactionType;
        transactionType = transactionsHelperClass.getTransactionType();

        switch (transactionType) {
            case "Deposit":
                holder.transactionCurrency.setTextColor(ContextCompat.getColor(holder.transactionCurrency.getContext(), R.color.green));
                holder.transactionAmount.setTextColor(ContextCompat.getColor(holder.transactionAmount.getContext(), R.color.green));
                holder.transactionImage.setImageResource(R.drawable.deposit_icon);
                break;
            case "Withdraw":
                holder.transactionCurrency.setTextColor(ContextCompat.getColor(holder.transactionCurrency.getContext(), R.color.red));
                holder.transactionAmount.setTextColor(ContextCompat.getColor(holder.transactionAmount.getContext(), R.color.red));
                holder.transactionImage.setImageResource(R.drawable.withdraw_icon);
                break;
            case "Send":
                holder.transactionImage.setImageResource(R.drawable.sent_icon);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView transactionImage;
        TextView transactionType, transactionDate, transactionCurrency, transactionAmount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            transactionImage = itemView.findViewById(R.id.transaction_icon);
            transactionType = itemView.findViewById(R.id.transaction_type);
            transactionDate = itemView.findViewById(R.id.transaction_date);
            transactionCurrency = itemView.findViewById(R.id.transaction_currency);
            transactionAmount = itemView.findViewById(R.id.transaction_amount);

        }
    }

}
