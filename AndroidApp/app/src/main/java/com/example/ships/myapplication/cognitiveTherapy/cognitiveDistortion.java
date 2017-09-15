package com.example.ships.myapplication.cognitiveTherapy;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.OtherInterfaces.ThereapyFactsheets;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cognitiveDistortion extends DrawerActivity {

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


    DistortionHandler db;
    List<DetailedInfo> distortionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cognitive_distortion);
        readIntent();
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_cognitive_distortion, null,false);
        frameLayout.addView(activityView);
        DistortionHandler db = new DistortionHandler(this);
        CongitiveInfo.initializeCognitiveInfo(db);
        addRecords();
        distortionList = new ArrayList<>();
        for(int i = 1; i <= db.getContentsCount(); i++){
            DetailedInfo di = new DetailedInfo();
            di.concerns = db.getContent(i).concerns;
            di.explanations = db.getContent(i).explanations;
            di.isExpanded = false;
            distortionList.add(di);
        }
        AdapterForDetail adapter = getAdapter();
        ListView lv = (ListView) findViewById(R.id.listDistortionInfo);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterForDetail adapter = (AdapterForDetail) parent.getAdapter();

                DetailedInfo item = (DetailedInfo) adapter.getItem(position);
                if(item != null){
                    if(item.isExpanded){
                        item.isExpanded = false;

                    }else{
                        item.isExpanded = true;
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    private AdapterForDetail getAdapter(){

        return new AdapterForDetail(this, distortionList);
    }

    public void back(View v)
    {
        super.onBackPressed();
    }
    public void solutions(View v)
    {
        startActivity(new Intent(this, ThereapyFactsheets.class).putExtras(createBundle()));
    }



    public void addRecords(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
            mySqlDB.execSQL("CREATE TABLE IF NOT EXISTS userRecords(UID VARCHAR,TIME VARCHAR, MODULE VARCHAR,PRIMARY KEY (UID, TIME));");
            String insertQuery = "INSERT INTO userRecords (uid, TIME, MODULE) VALUES(?,?,?)";
            mySqlDB.execSQL(insertQuery, new String[]{uid, dateFormat.format(date), "Cognitive therapy"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//testing
}
