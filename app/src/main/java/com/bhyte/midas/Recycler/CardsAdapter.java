package com.bhyte.midas.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.R;

import java.util.ArrayList;


public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {

    ArrayList<CardsHelperClass> cards;

    public CardsAdapter(ArrayList<CardsHelperClass> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new CardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsViewHolder holder, int position) {

        CardsHelperClass cardsHelperClass = cards.get(position);

        holder.card_name.setText(cardsHelperClass.getCardName());
        holder.card_number.setText(cardsHelperClass.getCardNumber());
        holder.card_name.setText(cardsHelperClass.getCardName());
        holder.date.setText(cardsHelperClass.getDate());

       // holder.relativeLayout.setBackgroundDrawable(cardsHelperClass.getCardImage());

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }


    public static class CardsViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView card_number, card_name, date;

        public CardsViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            card_number = itemView.findViewById(R.id.card_number);
            card_name = itemView.findViewById(R.id.card_name);
            date = itemView.findViewById(R.id.date);
            relativeLayout = itemView.findViewById(R.id.card);
        }
    }

}
