package com.example.ships.myapplication.OtherInterfaces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.MainActivity;
import com.example.ships.myapplication.modules.MyProgramList;
import com.example.ships.myapplication.modules.SuggestedModules;
import com.example.ships.myapplication.userRecord.Records;

public class UserProfile extends DrawerActivity {
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
        b.putString("typeOfTerm","none");//treatment term
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_user_profile, null,false);
        frameLayout.addView(activityView);
        readIntent();
        TextView userDetails = (TextView) findViewById(R.id.userDetail);
        userDetails.setText(email + "\n" + firstName + " " + lastName);

    }

    public void goToChangePassword(View view) {
        Intent in = new Intent(this, ChangePassword.class).putExtras(createBundle());
        startActivity(in);
    }

    public void goToUpdateUserInformation(View view) {
        startActivity(new Intent(this, UpdateUserDetail.class).putExtras(createBundle()));
    }

    public void goToSuggestedModule(View view) {
        Intent in = new Intent(this, SuggestedModules.class).putExtras(createBundle());
        startActivity(in);
    }

    public void goToMyProgram(View view) {
        Intent in = new Intent(this, MyProgramList.class).putExtras(createBundle());
        startActivity(in);

    }

    public void goToRecords(View view) {
        startActivity(new Intent(this, Records.class).putExtras(createBundle()));
    }

    public void logout(View view) {
        Intent in = new Intent(this, MainActivity.class).putExtras(createBundle());
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
    }
}
