package com.example.ships.myapplication.OtherInterfaces;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.exposure.ExposureInfo;

public class UpdateUserDetail extends DrawerActivity {
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
//        setContentView(R.layout.activity_update_user_detail);
        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_update_user_detail, null,false);
        frameLayout.addView(activityView);
    }

    public void cencel(View view) {
        startActivity(new Intent(this, UserProfile.class).putExtras(createBundle()));
    }

    public void updateUserDetail(View view) {
        startActivity(new Intent(this, Updated.class).putExtras(createBundle()));
    }
}
