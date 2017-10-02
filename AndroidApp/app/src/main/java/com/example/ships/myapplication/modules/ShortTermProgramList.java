package com.example.ships.myapplication.modules;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShortTermProgramList extends DrawerActivity {
//refer to https://goo.gl/MrPdIi

//testing comment to prevent force push op

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, String> expandableListDetail;
    ExpandableListDataPump dataDump = new ExpandableListDataPump();
    String programTitle;
    String programDetail;
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
        b.putString("typeOfTerm","short");//treatment term
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
//        setContentView(R.layout.activity_program_list);
        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_program_list, null,false);
        frameLayout.addView(activityView);
        TextView typeText = (TextView) findViewById(R.id.type_text);
        typeText.setText("Short Term Program Module List");
        expandableListView = (ExpandableListView) findViewById(R.id.programList);
        readIntent();
        //generate program title and program detail to the list
        dataDump.setData("Self Assessment", "Self Assessment is a tool that helps you to " +
                "understand more about yourself on fear of flying.");
        dataDump.setData("EMDR", "EMDR is a kind of therapeutic tool that helps you " +
                "to distract from the plane by focusing on a moving ball");


        expandableListDetail = dataDump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());


        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle,
                expandableListDetail, getIntent());
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
        Intent in = new Intent(this, SuggestedModules.class).putExtras(createBundle());
        in.putExtras(createBundle());
        startActivity(in);
    }

    public void addToMyProgram(MenuItem item) {

    }
    public void addAll(View view) {
        SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
        DBManager.insertTreatmentPlan(uid, DBManager.SHORTTERM, dataDump.getData().keySet().toArray(new String[0]), mySqlDB);

    }
}
