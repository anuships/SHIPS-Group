package com.example.ships.myapplication.modules;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.cognitiveTherapy.CognitiveDistortion;
import com.example.ships.myapplication.cognitiveTherapy.FactsheetSelect;
import com.example.ships.myapplication.cognitiveTherapy.Flight_Factsheet;

public class CognitiveTherapyList extends DrawerActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
//        setContentView(R.layout.activity_cognitive_therapy_list);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_cognitive_therapy_list, null,false);
        frameLayout.addView(activityView);
    }

    public void goBack(View view) {
        Intent in = new Intent(this, AllPrograms.class).putExtras(createBundle());
        in.putExtras(createBundle());
        startActivity(in);
    }

    public void goToFlightFactSheet(View view){
        Intent in = new Intent(this, Flight_Factsheet.class).putExtras(createBundle());
        in.putExtras(createBundle());
        startActivity(in);
    }

    public void goToWhatIf(View view){
        Intent in = new Intent(this, FactsheetSelect.class).putExtras(createBundle());
        in.putExtras(createBundle());
        startActivity(in);
    }

    public void goToCogntiveFactSheet(View view){
        Intent in = new Intent(this, CognitiveDistortion.class).putExtras(createBundle());
        in.putExtras(createBundle());
        startActivity(in);
    }
}
