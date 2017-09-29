package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.MainActivity;

public class FAQs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
    }

    public void goToHomePage(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void goToContactUs(View view) {
        startActivity(new Intent(this, ContactUs.class));
    }
}
