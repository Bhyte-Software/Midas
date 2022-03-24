package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.R;
import com.bhyte.midas.User.ProcessingCardCreation;
import com.bhyte.midas.User.UserCardsFragment;
import com.bhyte.midas.User.UserHomeFragment;
import com.bhyte.midas.User.UserSettingsFragment;
import com.bhyte.midas.User.UserTransactionsFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainDashboard extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    String keyOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        // Hooks
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        keyOne = ProcessingCardCreation.keyOne;

        // Show UserCardsFragment after successful card creation
        if (keyOne == null) {
            chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserHomeFragment()).commit();
        } else {
            chipNavigationBar.setItemSelected(R.id.bottom_nav_cards, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserCardsFragment()).commit();
        }

        bottomNavMenu();
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            startActivity(new Intent(getApplicationContext(), GetStarted.class));
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast toast = Toast.makeText(MainDashboard.this, "Please press back again to exit midas", Toast.LENGTH_SHORT);
        View view1 = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be edited
        TextView text = view1.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.white));

        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
        toast.show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void bottomNavMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.bottom_nav_home:
                        fragment = new UserHomeFragment();
                        break;
                    case R.id.bottom_nav_transactions:
                        fragment = new UserTransactionsFragment();
                        break;
                    case R.id.bottom_nav_cards:
                        fragment = new UserCardsFragment();
                        break;
                    case R.id.bottom_nav_settings:
                        fragment = new UserSettingsFragment();
                        break;
                }
                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }
}