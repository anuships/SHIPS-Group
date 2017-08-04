package com.example.ships.myapplication.exposure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ships.myapplication.OtherInterfaces.SystematicDesensitation;
import com.example.ships.myapplication.OtherInterfaces.Treatments;
import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.modules.AllPrograms;
import com.example.ships.myapplication.modules.MyLongTermProgram;
import com.example.ships.myapplication.modules.MyShortTermProgram;

public class ExposureInfo extends AppCompatActivity {
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
        setContentView(R.layout.activity_exposure_info);
    }
    public void toDescription(View v){
        startActivity(new Intent(this, ExposureDes.class).putExtras(createBundle()));
    }
    public void back(View v){
        try {
            String popMsg = "Go Back to My "+ typeOfTerm + " Term Program";
            if (typeOfTerm.equals("long")) {
                Toast.makeText(this, popMsg,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MyLongTermProgram.class).putExtras(createBundle()));
            } else if(typeOfTerm.equals("all")) {
                Toast.makeText(this, "Go Back to "+ typeOfTerm + " Program",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, Treatments.class).putExtras(createBundle()));
            }else if(typeOfTerm.equals("short")) {
                Toast.makeText(this, popMsg,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MyShortTermProgram.class).putExtras(createBundle()));
            }else {
                Toast.makeText(this, "Cannot find program type",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, UserProfile.class).putExtras(createBundle()));
            }
        }catch (Exception e){
            Toast.makeText(this, "Sorry the APP may have some errors",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, UserProfile.class).putExtras(createBundle()));
        }
    }

}
