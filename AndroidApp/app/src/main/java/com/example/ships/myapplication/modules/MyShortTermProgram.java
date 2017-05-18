package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ships.myapplication.EMDR.EMDRActivity;
import com.example.ships.myapplication.FAS.FAS;
import com.example.ships.myapplication.OtherInterfaces.Records;
import com.example.ships.myapplication.OtherInterfaces.ThereapyFactsheets;
import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;

public class MyShortTermProgram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_short_term_program);
    }

    public void goToFactSheet(View view) {
        startActivity(new Intent(this, ThereapyFactsheets.class));
    }

    public void goToSelfAssessment(View view) {
        startActivity(new Intent(this, FAS.class));
    }


    public void goBack(View view) {
        startActivity(new Intent(this, UserProfile.class));
    }

    public void goToRecords(View view) {
        startActivity(new Intent(this, Records.class));
    }


    public void goToEMDR(View view) {
        startActivity(new Intent(this, EMDRActivity.class));
    }
}
