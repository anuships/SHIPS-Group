package com.example.ships.myapplication.programlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.R;

public class ModuleSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_selection);
    }

    public void selectModules(View view) {
        startActivity(new Intent(this, ModuleSuggestion.class));
    }
}
