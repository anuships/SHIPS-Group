package com.example.ships.myapplication.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ships.myapplication.FAS.FAS;
import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.cognitiveTherapy.FactsheetSelect;
import com.example.ships.myapplication.cognitiveTherapy.Flight_Factsheet;
import com.example.ships.myapplication.cognitiveTherapy.cognitiveDistortion;
import com.example.ships.myapplication.userRecord.Records;

public class AllPrograms extends DrawerActivity {
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String uid;

    private void readIntent(){
        Bundle b = getIntent().getExtras();
        firstName = b.getString("firstName");
        lastName = b.getString("lastName");
        email = b.getString("email");
        uid = b.getString("uid");
    }
    private Bundle createBundle(){
        Bundle b = new Bundle();
        b.putString("firstName", firstName);
        b.putString("uid", uid);
        b.putString("lastName", lastName);
        b.putString("email", email);
        b.putString("typeOfTerm","all");//treatment term
        return b;
    }

/*    private Bundle termInfo(){
        Bundle b = new Bundle();
        b.putString("typeOfTerm","all");
        return b;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
//        setContentView(R.layout.activity_all_program);

        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_all_program, null,false);
        frameLayout.addView(activityView);
    }

    public void goToFactSheet(View view) {
        startActivity(new Intent(this, FactsheetSelect.class).putExtras(createBundle()));
    }

    public void goToCognitiveTherapy(View view) {
        startActivity(new Intent(this, cognitiveDistortion.class).putExtras(createBundle()));
    }

    public void goToFlightFactSheet(View view) {
        startActivity(new Intent(this, Flight_Factsheet.class).putExtras(createBundle()));
    }

    public void goToSelfAssessment(View view) {
        startActivity(new Intent(this, FAS.class).putExtras(createBundle()));
    }

    public void goToTherapeuticTools(View view) {
        startActivity(new Intent(this, TherapeuticTools.class).putExtras(createBundle()));
    }

    public void goToTreatment(View view) {
        startActivity(new Intent(this, Treatments.class).putExtras(createBundle()));
    }

    public void goBack(View view) {
        Intent in = new Intent(this, UserProfile.class).putExtras(createBundle());
        in.putExtras(createBundle());
        startActivity(in);
    }

    public void goToRecords(View view) {
        startActivity(new Intent(this, Records.class).putExtras(createBundle()));
    }
}
