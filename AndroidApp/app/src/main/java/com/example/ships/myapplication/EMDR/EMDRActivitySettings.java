package com.example.ships.myapplication.EMDR;

/**
 * Lalit Prasad 2017
 * EMDR settings activity
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.R;

public class EMDRActivitySettings extends DrawerActivity {
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String uid;
    private static String typeOfTerm;

    private void readIntent(){
        Bundle b = getIntent().getExtras();
        firstName = b.getString("firstName");
        lastName = b.getString("lastName");
        email = b.getString("email");
        uid = b.getString("uid");
        typeOfTerm = b.getString("typeOfTerm");
    }

    private Bundle createBundle(){
        Bundle b = new Bundle();
        b.putString("firstName", firstName);
        b.putString("uid", uid);
        b.putString("lastName", lastName);
        b.putString("email", email);
        b.putString("typeOfTerm",typeOfTerm);//treatment term
        return b;
    }

    //emdr movement type, to be selected by user
    String emdrMovementType = null;
    String emdrSpeed = null;
    String emdrTotalDuration = null;
    String emdrShadow = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
//        setContentView(R.layout.activity_emdrsettings);

        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_emdrsettings, null,false);
        frameLayout.addView(activityView);


        //set and populate spinner with emdr movement types
        Spinner emdr_movement_type_spinner = (Spinner) findViewById(R.id.emdr_movement_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.emdr_movements_array, android.R.layout.simple_spinner_dropdown_item);
        emdr_movement_type_spinner.setAdapter(adapter);

        emdrMovementType = emdr_movement_type_spinner.getSelectedItem().toString();

        //set movement type upon (re)selection
        emdr_movement_type_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Object item = adapterView.getItemAtPosition(i);
                        emdrMovementType = item.toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        Spinner emdr_speed_spinner = (Spinner) findViewById(R.id.emdr_speed_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.emdr_speed_array, android.R.layout.simple_spinner_dropdown_item);
        emdr_speed_spinner.setAdapter(adapter1);

        emdr_speed_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Object item = adapterView.getItemAtPosition(i);
                        emdrSpeed = item.toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        Spinner emdr_duration_spinner = (Spinner) findViewById(R.id.emdr_duration_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.emdr_duration_array, android.R.layout.simple_spinner_dropdown_item);
        emdr_duration_spinner.setAdapter(adapter2);

        emdr_duration_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Object item = adapterView.getItemAtPosition(i);
                        emdrTotalDuration = item.toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        Spinner emdr_shadow_spinner = (Spinner) findViewById(R.id.emdr_shadow_spinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.emdr_shadow_array, android.R.layout.simple_spinner_dropdown_item);
        emdr_shadow_spinner.setAdapter(adapter3);

        emdr_shadow_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Object item = adapterView.getItemAtPosition(i);
                        emdrShadow = item.toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );


        //launch emdr activity on button click
        Button emdrStartButton = (Button) findViewById(R.id.emdrstartbutton);
        emdrStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendMessage(view);
            }
        });

        //return to previous page
        Button emdr_settings_back_button = (Button) findViewById(R.id.emdr_settings_back_button);
        emdr_settings_back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                finish();
            }
        });
    }

    public void sendMessage(View view) {
        Intent EMDRintent = new Intent(this, EMDRActivity.class).putExtras(createBundle());
        //tell emdr activity which movement type to run
        EMDRintent.putExtra("emdr_Movement_Type", emdrMovementType);
        EMDRintent.putExtra("emdr_Speed", emdrSpeed);
        EMDRintent.putExtra("emdr_Total_Duration", emdrTotalDuration);
        EMDRintent.putExtra("emdr_Shadow", emdrShadow);
        startActivity(EMDRintent);
    }
}
