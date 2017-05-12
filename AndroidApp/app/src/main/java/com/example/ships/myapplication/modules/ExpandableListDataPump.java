package com.example.ships.myapplication.modules;

/**
 * Created by Jyun on 2017/04/10.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;

import java.util.HashMap;

public class ExpandableListDataPump {
    private HashMap<String, String> expandableListContent = new HashMap<String, String>();

    public  void setData(String title, String detail){
        expandableListContent.put(title, detail);
    }

    public HashMap<String, String> getData(){
        return expandableListContent;
    }

    public static class SuggestedModules extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_suggested_modules);
        }

        public void viewProgram(View view) {
/*            Button button = (Button) view;
            String selection = button.getText().toString();
            Log.d("selected as", selection);
            switch (selection){
                case "Long Term":
                    //save result as long_term;
                    Log.d("User selected ", selection);
                case "Mid Term":
                    //save result as mid_term;
                    Log.d("User selected ", selection);
                case "Short Term":
                    //save result as short_term;
                    Log.d("User selected ", selection);
            }*/

            startActivity(new Intent(this, AllProgramList.class));
        }

        public void goBack(View view) {
            startActivity(new Intent(this, UserProfile.class));
        }

        public void viewLongTermProgram(View view) {
            startActivity(new Intent(this, LongTermProgramList.class));
        }

        public void viewShortTermProgram(View view) {
            startActivity(new Intent(this, ShortTermProgramList.class));
        }
    }
}
