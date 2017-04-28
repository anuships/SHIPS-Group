package com.example.ships.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExposureDes extends AppCompatActivity {
//Kevin's protection test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposure_des);
    }
    public void startTherapy(View v){
        startActivity(new Intent(this, ExposureTherapy.class));
    }

}
