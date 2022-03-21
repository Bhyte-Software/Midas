package com.bhyte.midas.Transactions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;

public class AddMoneyChooseMethod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_choose_method);
    }

    public void callBack(View view) {
        finish();
    }

    public void addPaymentMethod(View view) {
        startActivity(new Intent(getApplicationContext(), AddPaymentMethod.class));
    }
}