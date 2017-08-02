package com.example.ships.myapplication.exposure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.R;

public class ExposureInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposure_info);
    }
    public void toDescription(View v){
        startActivity(new Intent(this, ExposureDes.class));
    }
    public void back(View v){super.onBackPressed();}

}
