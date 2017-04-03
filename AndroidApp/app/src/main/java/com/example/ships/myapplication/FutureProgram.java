package com.example.ships.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FutureProgram extends AppCompatActivity {
    ListView lv;
    ArrayList<Programs> programsItems = new ArrayList<Programs>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_program);
        lv = (ListView) findViewById(R.id.programList);

        //add item to the list
        programsItems.add(new Programs("Program 1", 0));
        programsItems.add(new Programs("Program 2", 1));
        programsItems.add(new Programs("Program 3", 1));
        programsItems.add(new Programs("Program 4", 0));
        programsItems.add(new Programs("Program 5", 1));
        CustomAdapter adapter = new CustomAdapter(this, programsItems);
        lv.setAdapter(adapter);
    }

    public void addToMyProgram(View view) {
        //get programs' name and all to user's list
    }
}
