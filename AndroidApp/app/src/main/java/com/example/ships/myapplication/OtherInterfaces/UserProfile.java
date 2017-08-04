package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.MainActivity;
import com.example.ships.myapplication.modules.MyProgramList;
import com.example.ships.myapplication.modules.ExpandableListDataPump;

public class UserProfile extends AppCompatActivity {
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
        return b;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView userDetails = (TextView) findViewById(R.id.userDetail);
        readIntent();
        userDetails.setText(email + "\n" + firstName + " " + lastName);

    }

    public void goToChangePassword(View view) {
        Intent in = new Intent(this, ChangePassword.class);
        in.putExtras(createBundle());
        startActivity(in);
    }

    public void goToUpdateUserInformation(View view) {
        startActivity(new Intent(this, UpdateUserDetail.class));
    }

    public void goToSuggestedModule(View view) {
        Intent in = new Intent(this, ExpandableListDataPump.SuggestedModules.class);
        in.putExtras(createBundle());
        startActivity(in);
    }

    public void goToMyProgram(View view) {
        Intent in = new Intent(this, MyProgramList.class);
        in.putExtras(createBundle());
        startActivity(in);

    }

    public void goToRecords(View view) {
        startActivity(new Intent(this, Records.class));
    }

    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
