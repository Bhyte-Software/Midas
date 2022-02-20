package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.bhyte.midas.R;
import com.bhyte.midas.User.UserCardsFragment;
import com.bhyte.midas.User.UserHomeFragment;
import com.bhyte.midas.User.UserSettingsFragment;
import com.bhyte.midas.User.UserTransactionsFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainDashboard extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        // Hooks
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserHomeFragment()).commit();
        bottomNavMenu();
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