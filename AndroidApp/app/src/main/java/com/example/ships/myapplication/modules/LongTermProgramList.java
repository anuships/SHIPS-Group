package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.ships.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LongTermProgramList extends AppCompatActivity {
//refer to https://goo.gl/MrPdIi

//testing comment to prevent force push op

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


        expandableListDetail = dataDump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());


        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle,
                expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }

    public void onClick(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.add_to_my_program, popup.getMenu());
        popup.show();
    }

    public void goBack(View view) {
        //go back to the previous page
        super.onBackPressed();
    }

    public void addToMyProgram(MenuItem item) {
        startActivity(new Intent(this, MyProgram.class));
    }

    public void goToMangement(View view) {
        startActivity(new Intent(this, MyProgram.class));
    }
}
