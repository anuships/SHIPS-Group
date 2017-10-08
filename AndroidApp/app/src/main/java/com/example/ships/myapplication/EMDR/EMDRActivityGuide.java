package com.example.ships.myapplication.EMDR;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.ships.myapplication.R;

public class EMDRActivityGuide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdrguide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //return to previous page
        Button emdr_guide_back_button = (Button) findViewById(R.id.emdr_guide_back_button);
        emdr_guide_back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                finish();
            }
        });
    }

    public void sendMessage(View view) {
        Intent EMDRSettingsintent = new Intent(this, EMDRActivitySettings.class);
        //tell emdr activity which movement type to run
        startActivity(EMDRSettingsintent);
    }

}
