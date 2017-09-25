package com.example.ships.myapplication.cognitiveTherapy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CognitiveReplacement extends DrawerActivity {

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

    static AdapterForReplacement adapter;
    ReplacementHandler db;
    List<ReplacedInfo> replaceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cognitive_replacement);
        readIntent();
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_cognitive_replacement, null,false);
        frameLayout.addView(activityView);

        ReplacementHandler db = new ReplacementHandler(this);
        ReplacementData.initializeReplacement(db);
        replaceList = new ArrayList<>();

        for(int i = 1; i <= db.getContentsCount(); i++){
            ReplacedInfo di = new ReplacedInfo();
            di.concerns = db.getContent(i).concerns;
            di.positiveSummary = db.getContent(i).positiveSummary;
            di.negativeSummary = db.getContent(i).negativeSummary;
            di.isExpanded = false;
            replaceList.add(di);
        }

        Log.d("TAG",Integer.toString(db.getContentsCount()));
        adapter = getAdapter();
        ListView lv = (ListView) findViewById(R.id.irrationalThinking);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ReplacedInfo item = (ReplacedInfo) adapter.getItem(position);
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


    public void back(View v){
        super.onBackPressed();
    }

    public AdapterForReplacement getAdapter() {

        return new AdapterForReplacement(this, replaceList);

    }

    public void refreshList()
    {
        adapter.notifyDataSetChanged();
    }
}
