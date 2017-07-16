package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.MainActivity;
import com.example.ships.myapplication.modules.AllProgramList;
import com.example.ships.myapplication.modules.ExpandableListDataPump;
import com.example.ships.myapplication.modules.MyProgram;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView userDetails = (TextView) findViewById(R.id.userDetail);
        Bundle b = getIntent().getExtras();
        String firstName = b.getString("firstName");
        String lastName = b.getString("lastName");
        String email = b.getString("email");
        userDetails.setText(email + "\n" + firstName + " " + lastName);
    }

    public void goToChangePassword(View view) {
        startActivity(new Intent(this, ChangePassword.class));
    }

    public void goToUpdateUserInformation(View view) {
        startActivity(new Intent(this, UpdateUserDetail.class));
    }

    public void goToSuggestedModule(View view) {
        startActivity(new Intent(this, ExpandableListDataPump.SuggestedModules.class));
    }

    public void goToMyProgram(View view) {
        startActivity(new Intent(this, AllProgramList.class));
    }

    public void goToRecords(View view) {
        startActivity(new Intent(this, Records.class));
    }

    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
