package com.example.ships.myapplication.OtherInterfaces;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.exposure.ExposureInfo;
import com.example.ships.myapplication.modules.AllPrograms;

public class Treatments extends DrawerActivity {
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
//        setContentView(R.layout.activity_treatments);
        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_treatments, null,false);
        frameLayout.addView(activityView);
    }

    public void goToSystematicDesensitisation(View view) {
        startActivity(new Intent(this, ExposureInfo.class).putExtras(createBundle()));
    }

    public void goBack(View view) {
        startActivity(new Intent(this, AllPrograms.class).putExtras(createBundle()));
    }
}
