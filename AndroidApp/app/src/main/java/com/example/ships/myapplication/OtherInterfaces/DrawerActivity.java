package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.exposure.ExposureDes;
import com.example.ships.myapplication.exposure.ExposureTherapy;
import com.example.ships.myapplication.homepageAndRegistration.MainActivity;
import com.example.ships.myapplication.modules.MyProgramList;
import com.example.ships.myapplication.userRecord.Records;

public class DrawerActivity extends AppCompatActivity {

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



    RelativeLayout leftRL;
    DrawerLayout drawerLayout;
    private String[] listTitles = {"My program", "User profile","Records", "Logout"};
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        setContentView(R.layout.activity_drawer);
        leftRL = (RelativeLayout)findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);


        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list, listTitles));


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(getApplicationContext(), MyProgramList.class).putExtras(createBundle());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intent2 = new Intent(getApplicationContext(), UserProfile.class).putExtras(createBundle());
                            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                            break;
                        case 2:
                            Intent intent3 = new Intent(getApplicationContext(), Records.class).putExtras(createBundle());
                            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent3);
                            break;
                        case 3:
                            Intent intent4 = new Intent(getApplicationContext(), MainActivity.class).putExtras(createBundle());
                            intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent4);
                            break;
                    }

            }
        });
    }



}
