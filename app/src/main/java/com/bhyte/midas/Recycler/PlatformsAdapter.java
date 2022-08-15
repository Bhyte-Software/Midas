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


public class PlatformsAdapter extends RecyclerView.Adapter<PlatformsAdapter.PlatformsViewHolder> {

    ArrayList<PlatformsHelperClass> platforms;

    public PlatformsAdapter(ArrayList<PlatformsHelperClass> platforms) {
        this.platforms = platforms;
    }

    @NonNull
    @Override
    public PlatformsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.platforms_layout, parent, false);
        return new PlatformsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatformsViewHolder holder, int position) {

        PlatformsHelperClass platformsHelperClass = platforms.get(position);

        holder.image.setImageResource(platformsHelperClass.getImage());
        holder.platform_name.setText(platformsHelperClass.getName());
        holder.platform_category.setText(platformsHelperClass.getCategory());
    }

    @Override
    public int getItemCount() {
        return platforms.size();
    }


    public static class PlatformsViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        RelativeLayout relativeLayout;
        TextView platform_name, platform_category;

        public PlatformsViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.platform_image);
            platform_name = itemView.findViewById(R.id.platform_name);
            platform_category = itemView.findViewById(R.id.platform_category);
            relativeLayout = itemView.findViewById(R.id.platform);
        }
    }

}
