package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ships.myapplication.R;

import java.util.ArrayList;

public class XTermsModuelsList extends AppCompatActivity {
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

    ListView lv;
    ArrayList<Programs> programsItems = new ArrayList<Programs>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        setContentView(R.layout.program_list_container);

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
                Log.d("Function ", "ture");
            }
        });}catch (Exception e){
            e.printStackTrace();
        }
    }


    ArrayList<String> selectedPrograms = new ArrayList<String>();
    public void addToMyProgram(View view) {
        //get programs' name and all to user's list
/*        lv = (ListView) findViewById(R.id.programList);
        for(Programs p: programsItems){
            if(p.getValue()==1){
                Log.d("Selected Programs: ", p.getName());
                //selectedPrograms.add(p.getName());
            }
        }*/
        startActivity(new Intent(this, AllPrograms.class));
    }

    public void goBack(View view) {
        //go back to the previous page
        super.onBackPressed();
    }

    public void goToMangement(View view) {
        //go to management page

        //demo test
        startActivity(new Intent(this, MyProgramList.class));
    }


    public void selectedProgram(View view) {
    }
}
