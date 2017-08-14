package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ships.myapplication.EMDR.EMDRActivity;
import com.example.ships.myapplication.EMDR.EMDRActivitySettings;
import com.example.ships.myapplication.FAS.FAS;
import com.example.ships.myapplication.OtherInterfaces.Records;
import com.example.ships.myapplication.OtherInterfaces.ThereapyFactsheets;
import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.cognitiveTherapy.FactsheetSelect;

public class MyShortTermProgram extends AppCompatActivity {
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
        b.putString("typeOfTerm","short");//treatment term
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        setContentView(R.layout.activity_my_short_term_program);
    }

    public void goToFactSheet(View view) {
        startActivity(new Intent(this, FactsheetSelect.class).putExtras(createBundle()));
    }

    public void goToSelfAssessment(View view) {
        startActivity(new Intent(this, FAS.class).putExtras(createBundle()));
    }


    public void goBack(View view) {
        Intent in = new Intent(this, UserProfile.class).putExtras(createBundle());
        startActivity(in);
    }

    public void goToRecords(View view) {
        startActivity(new Intent(this, Records.class).putExtras(createBundle()));
    }


    public void goToEMDR(View view) {
        startActivity(new Intent(this, EMDRActivitySettings.class).putExtras(createBundle()));
    }
}
