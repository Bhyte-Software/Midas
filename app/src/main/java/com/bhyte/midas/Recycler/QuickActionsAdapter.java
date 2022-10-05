package com.bhyte.midas.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.R;

import java.util.ArrayList;


public class QuickActionsAdapter extends RecyclerView.Adapter<QuickActionsAdapter.QuickActionsViewHolder> {

    ArrayList<QuickActionsHelperClass> quickActions;
    private final OnNoteListener mOnNoteListener;

    public QuickActionsAdapter(ArrayList<QuickActionsHelperClass> quickActions, OnNoteListener onNoteListener) {
        this.quickActions = quickActions;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public QuickActionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_actions_layout, parent, false);
        return new QuickActionsViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickActionsViewHolder holder, int position) {

        QuickActionsHelperClass quickActionsHelperClass = quickActions.get(position);

        holder.image.setImageResource(quickActionsHelperClass.getImage());
        holder.title.setText(quickActionsHelperClass.getTitle());
    }

    @Override
    public int getItemCount() {
        return quickActions.size();
    }


    public static class QuickActionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        RelativeLayout relativeLayout;
        TextView title;
        OnNoteListener onNoteListener;

        public QuickActionsViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;

            //Hooks
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            relativeLayout = itemView.findViewById(R.id.round);

            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
