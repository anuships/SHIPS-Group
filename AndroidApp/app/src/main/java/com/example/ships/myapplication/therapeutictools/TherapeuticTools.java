package com.example.ships.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.EMDR.EMDRActivity;

public class TherapeuticTools extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapeutic_tools);
    }

    public void goToEMDR(View view) {
        startActivity(new Intent(this, EMDRActivity.class));
    }

    public void goToRelaxationTraning(View view) {
        startActivity(new Intent(this, RelaxationAudioActivity.class));
    }

    public void goToBiofeedback(View view) {
        startActivity(new Intent(this, GSRGraphActivity.class));
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
