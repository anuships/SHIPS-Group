package com.example.ships.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowFASResult extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fasresult);
        tv = (TextView) findViewById(R.id.textView);
        tv.setText(Integer.toString(FAS.getScore()));
    }
}
