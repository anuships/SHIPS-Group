package com.example.ships.myapplication.programlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.FAS.FAS;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.therapeutictools.TherapeuticTools;

public class MyProgram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_program);
    }

    public void goToFactSheet(View view) {
        //startActivity(new Intent(this, classname.class));
    }

    public void goToSelfAssessment(View view) {
        startActivity(new Intent(this, FAS.class));
    }

    public void goToTherapeuticTools(View view) {
        startActivity(new Intent(this, TherapeuticTools.class));
    }

    public void goToTreatment(View view) {
        //startActivity(new Intent(this, classname.class));
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    public void goToRecords(View view) {
        //startActivity(new Intent(this, classname.class));
    }


}
