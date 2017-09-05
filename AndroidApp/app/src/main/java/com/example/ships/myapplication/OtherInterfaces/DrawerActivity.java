package com.example.ships.myapplication.OtherInterfaces;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.ships.myapplication.R;

public class DrawerActivity extends AppCompatActivity {

    RelativeLayout leftRL;
    DrawerLayout drawerLayout;
    private String[] listTitles = {"My program", "Logout"};
    private ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        leftRL = (RelativeLayout)findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);


        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list, listTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }



}
