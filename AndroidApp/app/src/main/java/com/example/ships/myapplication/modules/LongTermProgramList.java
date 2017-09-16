package com.example.ships.myapplication.modules;

//reference: https://goo.gl/MrPdIi

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

public class LongTermProgramList extends AppCompatActivity {

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
        b.putString("typeOfTerm","long");//treatment term
        return b;
    }

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, String> expandableListDetail;
    ExpandableListDataPump dataDump = new ExpandableListDataPump();
    String programTitle;
    String programDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        setContentView(R.layout.activity_program_list);
        expandableListView = (ExpandableListView) findViewById(R.id.programList);
        //generate program title and program detail to the list
        dataDump.setData("Self Assessment", "Self Assessment is a tool that helps you to " +
                "understand more about yourself on fear of flying.");
        dataDump.setData("Relaxation Audio Training", "Relaxation audio is kind of therapeutic tool" +
                "that helps you to learn techniques to relax yourself.");
        dataDump.setData("Biofeedback", "Biofeedback is kind of the therapeutic tool " +
                "that helps you to understand whether you become more relax or anxious. " +
                "At the beginning, some data will be collected from your body and then " +
                "generate a baseline in BLUE. After that a RED real time monitoring line " +
                "reflects your current feeling. If the red line goes up means you feel relax. " +
                "If the red line goes down that means you feel anxious.");
        dataDump.setData("Systematic Desensitisation", "Systematic Desensitisation is kind " +
                "of therapy that allows you to go through some airline like scenarios. " +
                "You have to apply techniques learned from the provided therapeutic tools " +
                "and try to relax your self. There are 5 levels of scenarios provided in " +
                "this version.");
        dataDump.setData("Factsheet", "The factsheet contains WhatIf scenarios about " +
                        " airplanes, with details as to what occurs in these scenarios.");


        expandableListDetail = dataDump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());

        readIntent();
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle,
                expandableListDetail, getIntent());

        expandableListView.setAdapter(expandableListAdapter);
    }

    public void onClick(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.add_to_my_program, popup.getMenu());
        popup.show();
    }

    public void goBack(View view) {
        Intent in = new Intent(this, SuggestedModules.class).putExtras(createBundle());
        in.putExtras(createBundle());
        startActivity(in);

    }
    public void addAll(View view) {
        SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Cursor resTCat = mySqlDB.rawQuery("SELECT treatmentplan_category.TCID FROM treatmentplan_category WHERE treatmentplan_category.NAME = \"LONG FLIGHT\"", null);
        resTCat.moveToFirst();
        String sqlInsertTP = "INSERT INTO treatmentplan (UID, TCID, date_added) VALUES(?,?,?)";
        SQLiteStatement stat = mySqlDB.compileStatement(sqlInsertTP);
        stat.bindString(1,uid);
        stat.bindLong(2,resTCat.getInt(0));
        stat.bindString(3, dateFormat.format(date));
        stat.executeInsert();
        Cursor resTID = mySqlDB.rawQuery("SELECT TID FROM treatmentplan WHERE date_added=? ;", new String[]{dateFormat.format(date)});
        resTID.moveToFirst();
        Cursor resSID = mySqlDB.rawQuery("SELECT status.SID FROM status WHERE status.name = \"NOT STARTED\"", null);
        resSID.moveToFirst();
        Cursor resTEID = mySqlDB.rawQuery("SELECT * FROM modules;", null);
        resTEID.moveToFirst();
        try {
            int index = 0;
            mySqlDB.beginTransaction();
            String sqlInsertUM = "INSERT INTO user_modules (TID, INDX, SID, MID, progress, last_updated) VALUES(?,?,?,?,0,?)";
            SQLiteStatement statement = mySqlDB.compileStatement(sqlInsertUM);
            for (String s: dataDump.getData().keySet()){
                System.out.println(s);
                if (!s.contains("Relax")){
                    if (s.contains("Self")){
                        s = "FAS";
                    }else if (s.contains("Fact")){
                        s = "FACTSHEET";
                    }
                    s = s.trim();
                    Cursor resMID = mySqlDB.rawQuery("SELECT MID FROM modules WHERE name=?;", new String[]{s});
                    System.out.println("here3");
                    try {
                        resMID.moveToFirst();
                        int i = resMID.getInt(0);
                    }catch(Exception e){
                        s = "Factsheet";
                        resMID = mySqlDB.rawQuery("SELECT MID FROM modules WHERE name=?;", new String[]{s});
                        resMID.moveToFirst();

                        e.printStackTrace();
                    }

                        statement.clearBindings();
                    statement.bindLong(1,resTID.getInt(0));
                    statement.bindLong(2, index);
                    statement.bindLong(3, resSID.getInt(0));
                    statement.bindLong(4, resMID.getInt(0));
                    statement.bindString(5, dateFormat.format(date));
                    statement.executeInsert();
                    index++;
                }
            }
            mySqlDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySqlDB.endTransaction();

        }
    }
    public void addToMyProgram(MenuItem item) {
        startActivity(new Intent(this, MyLongTermProgram.class).putExtras(createBundle()));
    }

    public void goToMangement(View view) {
        startActivity(new Intent(this, MyLongTermProgram.class).putExtras(createBundle()));
    }
}
