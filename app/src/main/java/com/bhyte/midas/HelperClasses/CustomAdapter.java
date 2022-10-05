package com.bhyte.midas.HelperClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhyte.midas.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int[] logos;
    String[] networkNames;
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, int[] logos, String[] networkNames) {
        this.context = applicationContext;
        this.logos = logos;
        this.networkNames = networkNames;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return logos.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spinner_items, null);
        ImageView icon = view.findViewById(R.id.imageView);
        TextView names = view.findViewById(R.id.textView);
        icon.setImageResource(logos[i]);
        names.setText(networkNames[i]);
        return view;
    }
}