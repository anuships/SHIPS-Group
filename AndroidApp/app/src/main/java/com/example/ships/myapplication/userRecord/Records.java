package com.example.ships.myapplication.userRecord;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.cognitiveTherapy.AdapterForDetail;
import com.example.ships.myapplication.cognitiveTherapy.ContentHandler;
import com.example.ships.myapplication.cognitiveTherapy.DetailedInfo;
import com.example.ships.myapplication.cognitiveTherapy.WhatIfScenario;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;
import com.example.ships.myapplication.modules.AllPrograms;

import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Records extends DrawerActivity {

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

    List<DetailedRecords> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
//        setContentView(R.layout.activity_records);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_records, null,false);
        frameLayout.addView(activityView);

        records = getRecords();
        AdapterForRecords adapter = getAdapter();
        ListView lv = (ListView) findViewById(R.id.listRecord);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterForRecords adapter = (AdapterForRecords) parent.getAdapter();
                DetailedRecords item = (DetailedRecords) adapter.getItem(position);
                if(item != null){
                    if(item.isExpanded){
                        item.isExpanded = false;

                    }else{
                        item.isExpanded = true;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public ArrayList<DetailedRecords> getRecords(){
        ArrayList<DetailedRecords> records = new ArrayList<>();
        try {
            SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
            mySqlDB.execSQL("CREATE TABLE IF NOT EXISTS userRecords(UID VARCHAR,TIME VARCHAR, MODULE VARCHAR, PRIMARY KEY (UID, TIME));");
            Cursor resultSet = mySqlDB.rawQuery("Select * from userRecords where uid=? order by TIME DESC", new String[]{uid});
            Map<String, String> infoMap = new HashMap<>();
            if (resultSet.getCount() > 0) {
                if (resultSet.moveToFirst()) {
                    do {
                        String mapValue = "";
                        if (infoMap.containsKey(resultSet.getString(2))){
                         mapValue = infoMap.get(resultSet.getString(2));
                        }
                        infoMap.put(resultSet.getString(2), mapValue + resultSet.getString(1) + '\n');
                    } while (resultSet.moveToNext());
                }
            }
            mySqlDB.close();
            Iterator<Map.Entry<String, String>> iterator = infoMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                DetailedRecords di = new DetailedRecords();
                di.modules = entry.getKey();
                di.details = entry.getValue();
                records.add(di);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    public void clear(View v){
        SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
        mySqlDB.execSQL("CREATE TABLE IF NOT EXISTS userRecords(UID VARCHAR,TIME VARCHAR, MODULE VARCHAR, PRIMARY KEY (UID, TIME));");
        mySqlDB.execSQL("DROP TABLE userRecords");
        mySqlDB.execSQL("CREATE TABLE IF NOT EXISTS userRecords(UID VARCHAR,TIME VARCHAR, MODULE VARCHAR, PRIMARY KEY (UID, TIME));");
        finish();
        startActivity(getIntent());
    }
    public void back(View v){
        startActivity(new Intent(this, AllPrograms.class).putExtras(createBundle()));
    }

    private AdapterForRecords getAdapter(){
        return new AdapterForRecords(this, records);
    }
}
