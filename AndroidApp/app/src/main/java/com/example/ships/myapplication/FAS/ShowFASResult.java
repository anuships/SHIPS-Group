package com.example.ships.myapplication.FAS;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.example.ships.myapplication.OtherInterfaces.UserProfile;
import com.example.ships.myapplication.modules.AllPrograms;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.modules.MyLongTermProgram;

public class ShowFASResult extends AppCompatActivity {
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

    TextView tv;
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        setContentView(R.layout.activity_show_fasresult);
        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView37);
        String scoreComment;
        String AFscoreComment;
        String IFscoreComment;
        String GFscoreComment;

        if (FAS.getScore() >= 82.74){

            scoreComment = "You are very likely suffered from fear of flying";
        } else if (FAS.getScore()>=62){
            scoreComment = "You are likely suffered from fear of flying";

        } else {
            scoreComment = "You are unlikely suffered from fear of flying";
        }

        if (FAS.getAFscore() >= 33){
            AFscoreComment = "You are very likely to have anticipatory flight anxiety";
        } else if (FAS.getAFscore()>=22){
            AFscoreComment = "You are likely to have anticipatory flight anxiety";
        } else {
            AFscoreComment = "You are unlikely to have anticipatory flight anxiety";
        }

        if (FAS.getIFscore() >= 34){
            IFscoreComment = "You are very likely to have in-flight anxiety";
        } else if (FAS.getIFscore()>=21){
            IFscoreComment = "You are likely to have in-flight anxiety";
        } else {
            IFscoreComment = "You are unlikely to have in-flight anxiety";
        }

        if (FAS.getGFscore() >= 13){
            GFscoreComment = "You are very likely to have generalized flight anxiety";
        } else if (FAS.getGFscore()>=7){
            GFscoreComment = "You are likely to have generalized flight anxiety";
        } else {
            GFscoreComment = "You are unlikely to have generalized flight anxiety";
        }

        tv.setText(
                "1. Your total score of fear of flying is " + Integer.toString(FAS.getScore()) + ": " + "\n" + Html.fromHtml(scoreComment) + "\n" + "\n" +
                "2. Your total score of anticipatory flight anxiety is " + Integer.toString(FAS.getAFscore()) + ": " + "\n" + AFscoreComment + "\n" + "\n" +
                "3. Your total score of in-flight anxiety is " + Integer.toString(FAS.getIFscore()) + ": " + "\n" + IFscoreComment + "\n" + "\n" +
                "4. Your total score of generalized flight anxiety is " + Integer.toString(FAS.getGFscore()) + ": " + "\n"  + GFscoreComment + "\n"
        );

        String suggestion;
        if (FAS.getScore()>=82.74){
            suggestion = "Based on your score, we recommend you to choose the following modules: " + "\n" +
                    "1. Systematic Desensitization" + "\n" +
                    "2. EMDR" + "\n" +
                    "3. Cognitive Therapy" + "\n" +
                    "4. Biofeedback" + "\n" +
                    "5. Relaxation Audio Training" + "\n";
        } else if (FAS.getScore() >=67){
            suggestion = "Based on your score, we recommend you to choose the following modules: " + "\n" +
                    "1. EMDR" + "\n" +
                    "2. Cognitive Therapy" + "\n" +
                    "3. Biofeedback" + "\n" +
                    "4. Relaxation Audio Training" + "\n";
        } else{
            suggestion = "Although you are unlikely to have fear of flying, the following modules can still be helpful to you: " + "\n" +
                    "1. Biofeedback" + "\n" +
                    "2. Relaxation Audio Training" + "\n";
        }

        tv2.setText(suggestion);
    }

    public void goBack(View v)
    {
        startActivity(new Intent(this, AllPrograms.class).putExtras(createBundle()));
    }
    public void goToTreatment(View v)
    {
        startActivity(new Intent(this, MyLongTermProgram.class));
    }
}
