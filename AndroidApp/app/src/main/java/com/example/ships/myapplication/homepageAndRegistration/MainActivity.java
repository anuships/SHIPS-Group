package com.example.ships.myapplication.homepageAndRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.ships.myapplication.OtherInterfaces.ContactUs;
import com.example.ships.myapplication.OtherInterfaces.FindOutMore;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.modules.MyProgram;

public class MainActivity extends AppCompatActivity {

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

    public void clickLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
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
