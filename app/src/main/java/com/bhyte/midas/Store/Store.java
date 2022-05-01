package com.bhyte.midas.Store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.Common.MainDashboard;
import com.bhyte.midas.R;

public class Store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
    }

    public void callMainDashboard(View view) {
        startActivity(new Intent(getApplicationContext(), MainDashboard.class));
    }
}