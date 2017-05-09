package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.example.ships.myapplication.R;
import com.example.ships.myapplication.exposure.ExposureInfo;
import com.example.ships.myapplication.modules.MyProgram;

public class Treatments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);
    }

    public void goToSystematicDesensitisation(View view) {
        startActivity(new Intent(this, ExposureInfo.class));
    }

    public void goBack(View view) {
        startActivity(new Intent(this, MyProgram.class));
    }
}
