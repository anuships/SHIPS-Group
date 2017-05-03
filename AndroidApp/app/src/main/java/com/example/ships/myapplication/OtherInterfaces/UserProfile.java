package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.modules.ExpandableListDataPump;
import com.example.ships.myapplication.modules.MyProgram;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
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
        startActivity(new Intent(this, MyProgram.class));
    }

    public void goToRecords(View view) {
        startActivity(new Intent(this, Records.class));
    }
}
