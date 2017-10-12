package com.example.ships.myapplication.modules;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MyProgramListV2 extends DrawerActivity {
//refer to https://goo.gl/MrPdIi

//testing comment to prevent force push op

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

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, String> expandableListDetail;
    ExpandableListDataPump dataDump = new ExpandableListDataPump();
    String programTitle;
    String programDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
//        setContentView(R.layout.activity_all_program_list);
        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_all_program_list, null,false);
        frameLayout.addView(activityView);
        expandableListView = (ExpandableListView) findViewById(R.id.programList);
        readIntent();
        SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
        Cursor resTID = mySqlDB.rawQuery("SELECT TID FROM treatmentplan WHERE UID=? ;", new String[]{uid});
        resTID.moveToFirst();
        System.out.println(resTID.getCount());
        if (resTID.getCount() == 0){
            expandableListView.setVisibility(View.GONE);
            TextView spaceText = (TextView) findViewById(R.id.space_text);
            TextView emptyText = (TextView) findViewById(R.id.empty_text);
            Button suggestedButton = (Button) findViewById(R.id.suggested_m_but);
            spaceText.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);
            suggestedButton.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < resTID.getCount();i++){
                dataDump.setData((i+1) + " : ", "hi");
        }

       expandableListDetail = dataDump.getData();

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        Collections.sort(expandableListTitle);
        System.out.println(expandableListTitle);
        expandableListAdapter = new CustomExpandableListAdapterMyProgram(this, expandableListTitle,
                expandableListDetail, getIntent());

        expandableListView.setAdapter(expandableListAdapter);
    }

    public void onClick(View v) {
        System.out.println(v.toString());

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.delete_from_program, popup.getMenu());
        popup.show();
    }
    public void onClickInside(View v) {
        AppCompatButton but = (AppCompatButton) v;
        System.out.println(but.toString());
        ExpandableListView ex = (ExpandableListView) but.getParent().getParent().getParent();
        TextView title = (TextView) ex.findViewById(R.id.listTitle);
        System.out.println(title.getText().toString());
    }

    public void delFromProgram(MenuItem item){

    }
    public void goBack(View view) {
        //go back to the previous page
        super.onBackPressed();
    }

    public void addToMyProgram(MenuItem item) {
        startActivity(new Intent(this, AllPrograms.class).putExtras(createBundle()));
    }

    public void goToMangement(View view) {
        startActivity(new Intent(this, AllPrograms.class).putExtras(createBundle()));
    }
    public void goToSuggestModules(View view){
        startActivity(new Intent(this, SuggestedModules.class).putExtras(createBundle()));
    }
}
