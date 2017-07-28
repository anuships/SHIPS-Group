package com.example.ships.myapplication.modules;

/**
 * Created by Jyun on 2017/04/10.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        private static String firstName;
        private static String lastName;
        private static String email;
        private static String uid;

        private void readIntent(){
            Bundle b = getIntent().getExtras();
            firstName = b.getString("firstName");
            lastName = b.getString("lastName");
            email = b.getString("email");
            uid = b.getString("uid");
        }
        private Bundle createBundle(){
            Bundle b = new Bundle();
            b.putString("firstName", firstName);
            b.putString("uid", uid);
            b.putString("lastName", lastName);
            b.putString("email", email);
            return b;
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_suggested_modules);
            readIntent();
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

            startActivity(new Intent(this, AllPrograms.class));
        }

        public void goBack(View view) {
            Intent in = new Intent(this, UserProfile.class);
            in.putExtras(createBundle());
            startActivity(in);
        }

        public void viewLongTermProgram(View view) {
            Intent in = new Intent(this, LongTermProgramList.class);
            in.putExtras(createBundle());
            startActivity(in);
        }

        public void viewShortTermProgram(View view) {
            Intent in = new Intent(this, ShortTermProgramList.class);
            in.putExtras(createBundle());
            startActivity(in);
        }
    }
}
