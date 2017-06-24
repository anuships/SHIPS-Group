package com.example.ships.myapplication.EMDR;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ships.myapplication.R;

public class EMDRActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdrsettings);

        Button emdrStartButton = (Button) findViewById(R.id.emdrstartbutton);
        emdrStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendMessage(view);
            }
        });

        Spinner emdr_movement_type_spinner = (Spinner) findViewById(R.id.emdr_movement_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.emdr_movements_array, android.R.layout.simple_spinner_dropdown_item);
        emdr_movement_type_spinner.setAdapter(adapter);
    }

    public void sendMessage(View view) {
        Intent EMDRintent = new Intent(this, EMDRActivity.class);
        startActivity(EMDRintent);
    }
}
