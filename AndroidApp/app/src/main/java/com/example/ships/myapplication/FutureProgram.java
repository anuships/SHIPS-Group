package com.example.ships.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FutureProgram extends AppCompatActivity {
    ListView lv;
    ArrayList<Programs> programsItems = new ArrayList<Programs>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_program);

        lv = (ListView) findViewById(R.id.programList);

        //add item to the list from database
        programsItems.add(new Programs("Program 1", 0));
        programsItems.add(new Programs("Program 2", 1));
        programsItems.add(new Programs("Program 3", 1));
        ProgramListAdapter adapter = new ProgramListAdapter(this, programsItems);
        lv.setAdapter(adapter);

        try{
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Log.d("Function ", "ture");
/*                Programs p = programsItems.get(position);
                int checked = p.getValue();
                if(checked == 1){
                    p.setValue(0);
                }else
                    p.setValue(1);*/
            }
        });}catch (Exception e){
            e.printStackTrace();git
        }
    }


    ArrayList<String> selectedPrograms = new ArrayList<String>();
    public void addToMyProgram(View view) {
        //get programs' name and all to user's list
        lv = (ListView) findViewById(R.id.programList);
        for(Programs p: programsItems){
            if(p.getValue()==1){
                Log.d("Selected Programs: ", p.getName());
                //selectedPrograms.add(p.getName());
            }
        }
    }

    public void goBack(View view) {
        //go back to the previous page
    }

    public void goToMangement(View view) {
        //go to management page
    }

    public void selectedProgram(View view) {
    }
}
