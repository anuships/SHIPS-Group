package com.example.ships.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModuleSuggestion extends AppCompatActivity {
//refer to https://goo.gl/MrPdIi

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
        setContentView(R.layout.activity_module_suggetions);
        expandableListView = (ExpandableListView) findViewById(R.id.programList);
        //expandableListDetail = ExpandableListDataPump.getData();
        for(int i = 0; i < 20; i++) {
            programTitle = "Program" + i;
            programDetail = "Program detail " + i;
            dataDump.setData(programTitle, programDetail);
        }
        expandableListDetail = dataDump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());


        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle,
                expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(
                new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(
                new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }

}
