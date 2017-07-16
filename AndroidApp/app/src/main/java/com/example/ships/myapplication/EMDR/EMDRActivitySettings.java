package com.example.ships.myapplication.EMDR;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ships.myapplication.R;

public class EMDRActivitySettings extends AppCompatActivity {

    String emdrMovementType = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdrsettings);

//        emdr_movement_type_spinner.setAdapter(adapter);


        Spinner emdr_movement_type_spinner = (Spinner) findViewById(R.id.emdr_movement_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.emdr_movements_array, android.R.layout.simple_spinner_dropdown_item);
        emdr_movement_type_spinner.setAdapter(adapter);

        emdrMovementType = emdr_movement_type_spinner.getSelectedItem().toString();

        emdr_movement_type_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Object item = adapterView.getItemAtPosition(i);
                        emdrMovementType = item.toString();
                        System.out.println(emdrMovementType);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        Button emdrStartButton = (Button) findViewById(R.id.emdrstartbutton);
        emdrStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendMessage(view);
            }
        });
    }

    public void sendMessage(View view) {
        Intent EMDRintent = new Intent(this, EMDRActivity.class);
        EMDRintent.putExtra("emdr_Movement_Type", emdrMovementType);
        startActivity(EMDRintent);
    }
}
