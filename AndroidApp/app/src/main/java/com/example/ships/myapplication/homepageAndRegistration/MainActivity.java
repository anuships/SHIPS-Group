package com.example.ships.myapplication.homepageAndRegistration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ships.myapplication.OtherInterfaces.ContactUs;
import com.example.ships.myapplication.OtherInterfaces.FindOutMore;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;

public class MainActivity extends AppCompatActivity {

    //testing used information
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String uid;
    private static String typeOfTerm;


    private Bundle createBundle(){
        Bundle b = new Bundle();
        b.putString("firstName", "SHIPS");
        b.putString("uid", "9999999");
        b.putString("lastName", "developer");
        b.putString("email", "tester@anu.edu.au");
        b.putString("typeOfTerm","none");//treatment term
        return b;
    }
    //end of testing used information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if (getActionBar() != null){
            getActionBar().hide();
        }
    }

    private boolean testMode = false;
    public void clickLogin(View view) {
        //testMode = true;//disable this if not using a test mode.

        if (!testMode) {
            //enable login function
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            //disable login function
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Developer Mode is enabled", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(new Intent(this, UserProfile.class).putExtras(createBundle()));
        }
    }
    public void register(View view) {
        startActivity(new Intent(this, TermsAndConditions.class));
    }

    public void goToFindOutMore(View view) {
        startActivity(new Intent(this, FindOutMore.class));
    }

    public void goToContactUs(View view) {
        startActivity(new Intent(this, ContactUs.class));
    }
}
