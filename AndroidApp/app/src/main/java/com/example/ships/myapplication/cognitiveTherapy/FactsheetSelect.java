package com.example.ships.myapplication.cognitiveTherapy;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ships.myapplication.OtherInterfaces.ThereapyFactsheets;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.modules.AllPrograms;
import com.example.ships.myapplication.modules.MyLongTermProgram;

import java.util.ArrayList;
import java.util.List;


public class FactsheetSelect extends AppCompatActivity {

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

        ContentHandler db;
        List<DetailedInfo> whatIfList;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            readIntent();
            setContentView(R.layout.factsheet_select);
            ContentHandler db = new ContentHandler(this);
            WhatIfScenario.initializeWhatIfScenario(db);
            whatIfList = new ArrayList<>();
            for(int i = 1; i <= db.getContentsCount(); i++){
                DetailedInfo di = new DetailedInfo();
                di.concerns = db.getContent(i).concerns;
                di.explanations = db.getContent(i).explanations;
                di.isExpanded = false;
                whatIfList.add(di);
            }
            AdapterForDetail adapter = getAdapter();
            ListView lv = (ListView) findViewById(R.id.listFactsheet);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   AdapterForDetail adapter = (AdapterForDetail) parent.getAdapter();

                    DetailedInfo item = (DetailedInfo) adapter.getItem(position);
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

    private AdapterForDetail getAdapter(){

        return new AdapterForDetail(this, whatIfList);
    }

    public void back(View v)
    {
       super.onBackPressed();
    }
    public void askMore(View v)
    {
        startActivity(new Intent(this, ThereapyFactsheets.class).putExtras(createBundle()));
    }

//testing
}

