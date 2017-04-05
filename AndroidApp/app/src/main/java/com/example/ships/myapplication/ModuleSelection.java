package com.example.ships.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ModuleSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_selection);

        EditText t
                = (EditText) findViewById(R.id.moduleSuggestionText);

        //place suggestions from database
        String s = "Text from database \nOR hardcode?";
        t.setText(s);
    }

    public void selectModules(View view) {
        startActivity(new Intent(this, ModuleSuggetion.class));
    }
}
