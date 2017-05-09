package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.FAS.FAS;
import com.example.ships.myapplication.OtherInterfaces.Records;
import com.example.ships.myapplication.OtherInterfaces.ThereapyFactsheets;
import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;

public class MyProgram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_program);
    }

    public void goToFactSheet(View view) {
        startActivity(new Intent(this, ThereapyFactsheets.class));
    }

    public void goToSelfAssessment(View view) {
        startActivity(new Intent(this, FAS.class));
    }

    public void goToTherapeuticTools(View view) {
        startActivity(new Intent(this, Programs.TherapeuticTools.class));
    }

    public void goToTreatment(View view) {
        startActivity(new Intent(this, Treatments.class));
    }

    public void goBack(View view) {
        startActivity(new Intent(this, UserProfile.class));
    }

    public void goToRecords(View view) {
        startActivity(new Intent(this, Records.class));
    }


}
