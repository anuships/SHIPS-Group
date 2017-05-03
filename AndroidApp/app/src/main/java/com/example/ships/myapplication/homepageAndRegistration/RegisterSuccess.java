package com.example.ships.myapplication.homepageAndRegistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.modules.ExpandableListDataPump;

public class RegisterSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
    }

    public void getStart(View v)
    {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
