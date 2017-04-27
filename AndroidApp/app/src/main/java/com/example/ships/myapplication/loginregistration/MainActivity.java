package com.example.ships.myapplication.loginregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.loginregistration.TermsAndConditions;
import com.example.ships.myapplication.programlist.XTermsModuelsList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if (getActionBar() != null){
            getActionBar().hide();
        }
    }

    public void clickLogin(View view) {
        startActivity(new Intent(this, XTermsModuelsList.class));
    }
    public void register(View view) {
        startActivity(new Intent(this, TermsAndConditions.class));
    }
}
