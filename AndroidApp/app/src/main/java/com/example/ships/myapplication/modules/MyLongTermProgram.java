package com.example.ships.myapplication.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ships.myapplication.FAS.FAS;
import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.cognitiveTherapy.FactsheetSelect;
import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.exposure.ExposureInfo;
import com.example.ships.myapplication.relaxationAudio.RelaxationAudioActivity;
import com.example.ships.myapplication.userRecord.Records;

public class MyLongTermProgram extends DrawerActivity {
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
        b.putString("typeOfTerm","long");//treatment term
        return b;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
//        setContentView(R.layout.activity_my_long_term_program);
        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_my_long_term_program, null,false);
        frameLayout.addView(activityView);
    }

    public void goToFactSheet(View view) {
        startActivity(new Intent(this, FactsheetSelect.class).putExtras(createBundle()));
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


    public void goToRelaxationTraning(View view) {
        startActivity(new Intent(this, RelaxationAudioActivity.class).putExtras(createBundle()));
    }

    public void goToBiofeedback(View view) {
        startActivity(new Intent(this, GSRGraphActivity.class).putExtras(createBundle()));
    }

    public void goToSystematicDesensitisation(View view) {
        Intent in = new Intent(this, ExposureInfo.class).putExtras(createBundle());
        in.putExtras(createBundle());
        startActivity(in);
    }

}
