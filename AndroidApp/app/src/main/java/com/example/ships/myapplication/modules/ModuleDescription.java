package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ships.myapplication.EMDR.EMDRActivitySettings;
import com.example.ships.myapplication.FAS.FAS;
import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.OtherInterfaces.SystematicDesensitation;
import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.cognitiveTherapy.FactSheetContent;
import com.example.ships.myapplication.cognitiveTherapy.FactsheetSelect;
import com.example.ships.myapplication.exposure.ExposureInfo;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;
import com.example.ships.myapplication.relaxationAudio.RelaxationAudioActivity;

public class ModuleDescription extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        setContentView(R.layout.activity_module_description);
        TextView desc = (TextView) findViewById(R.id.moduleDesText);
        desc.setText(getIntent().getExtras().getString("desc"));
    }
    public void startTherapy(View v){
        Intent in;
        String title = getIntent().getExtras().getString("title");
        System.out.println(title);
        if (getIntent().getExtras().getString("title").equals(DBManager.BIOFEEDBACK)){
            in = new Intent(this, GSRGraphActivity.class).putExtras(createBundle());
        }else if (title.equals(DBManager.EMDR)){
            in = new Intent(this, EMDRActivitySettings.class).putExtras(createBundle());
        }else if (title.equals(DBManager.FACTSHEET)){
            in = new Intent(this, FactsheetSelect.class).putExtras(createBundle());
        }else if (title.equals(DBManager.FAS)){
            in = new Intent(this, FAS.class).putExtras(createBundle());
        }else if (title.equals(DBManager.SYSDESEN)){
            in = new Intent(this, ExposureInfo.class).putExtras(createBundle());
        }else{
            in = new Intent(this, RelaxationAudioActivity.class).putExtras(createBundle());
        }
        in.putExtras(getIntent().getExtras());
        startActivity(in);
    }
    public void back(View v){super.onBackPressed();}
}
