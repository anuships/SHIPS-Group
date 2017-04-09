package com.example.ships.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ModuleSuggestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_suggetion);


        EditText t
                = (EditText) findViewById(R.id.moduleSuggestionText);

        //place suggestions from database
        String s = "Text from database \nOR hardcode?";
        t.setText(s);

    }

}
