package com.example.ships.myapplication.therapeutictools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.EMDR.EMDRActivity;
import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.relaxaudio.RelaxationAudioActivity;

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
