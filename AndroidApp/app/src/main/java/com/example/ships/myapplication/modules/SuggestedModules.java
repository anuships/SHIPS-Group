package com.example.ships.myapplication.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;

/**
 * Created by Jyun on 2017/08/05.
 */

public class SuggestedModules extends DrawerActivity {
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
//        setContentView(R.layout.activity_suggested_modules);
        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_suggested_modules, null,false);
        frameLayout.addView(activityView);
    }

    public void viewLongTermProgram(View view) {
        Intent in = new Intent(this, LongTermProgramList.class).putExtras(createBundle());
        startActivity(in);
    }

    public void viewShortTermProgram(View view) {
        Intent in = new Intent(this, ShortTermProgramList.class).putExtras(createBundle());
        startActivity(in);
    }

    public void viewAllProgram(View view) {
        startActivity(new Intent(this, AllPrograms.class).putExtras(createBundle()));
    }

    public void goBack(View view) {
        Intent in = new Intent(this, UserProfile.class).putExtras(createBundle());
        startActivity(in);
    }
}
