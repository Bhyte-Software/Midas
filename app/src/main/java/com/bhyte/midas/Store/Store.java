package com.bhyte.midas.Store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;

public class Store extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        context = getApplicationContext();

        // Hooks

    }

    public void callMainDashboard(View view) {
        startActivity(new Intent(getApplicationContext(), MainDashboard.class));
    }
}