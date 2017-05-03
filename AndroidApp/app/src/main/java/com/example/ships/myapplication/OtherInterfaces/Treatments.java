package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.R;

public class Treatments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);
    }

    public void goToSystematicDesensitisation(View view) {
        startActivity(new Intent(this, SystematicDesensitation.class));
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
