package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.LoginActivity;

public class FindPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
    }

    public void sendTempararyCode(View view) {
        //not yet implemented
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
