package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ships.myapplication.EMDR.EMDRActivitySettings;
import com.example.ships.myapplication.FAS.FAS;
import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.exposure.ExposureInfo;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;
import com.example.ships.myapplication.relaxationAudio.RelaxationAudioActivity;

public class ModuleDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_description);
        TextView desc = (TextView) findViewById(R.id.moduleDesText);
        desc.setText(getIntent().getExtras().getString("desc"));
    }
    public void startTherapy(View v){
        Intent in;
        String title = getIntent().getExtras().getString("title");
        System.out.println(title);
        if (getIntent().getExtras().getString("title").equals(DBManager.BIOFEEDBACK)){
            in = new Intent(this, GSRGraphActivity.class);

        }else if (title.equals(DBManager.EMDR)){
            in = new Intent(this, EMDRActivitySettings.class);
        }else if (title.equals(DBManager.FACTSHEET)){
            in = new Intent(this, EMDRActivitySettings.class);
        }else if (title.equals(DBManager.FAS)){
            in = new Intent(this, FAS.class);
        }else if (title.equals(DBManager.SYSDESEN)){
            in = new Intent(this, ExposureInfo.class);
        }else{
            in = new Intent(this, RelaxationAudioActivity.class);
        }
        in.putExtras(getIntent().getExtras());
        startActivity(in);
    }
    public void back(View v){startActivity(new Intent(this, Treatments.class));}
}
