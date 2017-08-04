package com.example.ships.myapplication.FAS;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.modules.AllPrograms;

import java.util.ArrayList;

public class FAS extends AppCompatActivity {

    static int score = 0;

    public static int getAFscore() {
        return AFscore;
    }

    public static int getIFscore() {
        return IFscore;
    }

    public static int getGFscore() {
        return GFscore;
    }

    static int AFscore = 0;
    static int IFscore = 0;
    static int GFscore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fas);
    }
    public void calculateScore(View v)
    {
        score = 0;
        AFscore = 0;
        IFscore = 0;
        GFscore = 0;
        ArrayList<RadioGroup> choice = new ArrayList<>();
        RadioGroup r1 = (RadioGroup) findViewById(R.id.quest1);
        RadioGroup r2 = (RadioGroup) findViewById(R.id.quest2);
        RadioGroup r3 = (RadioGroup) findViewById(R.id.quest3);
        RadioGroup r4 = (RadioGroup) findViewById(R.id.quest4);
        RadioGroup r5 = (RadioGroup) findViewById(R.id.quest5);
        RadioGroup r6 = (RadioGroup) findViewById(R.id.quest6);
        RadioGroup r7 = (RadioGroup) findViewById(R.id.quest7);
        RadioGroup r8 = (RadioGroup) findViewById(R.id.quest8);
        RadioGroup r9 = (RadioGroup) findViewById(R.id.quest9);
        RadioGroup r10 = (RadioGroup) findViewById(R.id.quest10);
        RadioGroup r11 = (RadioGroup) findViewById(R.id.quest11);
        RadioGroup r12 = (RadioGroup) findViewById(R.id.quest12);
        RadioGroup r13 = (RadioGroup) findViewById(R.id.quest13);
        RadioGroup r14 = (RadioGroup) findViewById(R.id.quest14);
        RadioGroup r15 = (RadioGroup) findViewById(R.id.quest15);
        RadioGroup r16 = (RadioGroup) findViewById(R.id.quest16);
        RadioGroup r17 = (RadioGroup) findViewById(R.id.quest17);
        RadioGroup r18 = (RadioGroup) findViewById(R.id.quest18);
        RadioGroup r19 = (RadioGroup) findViewById(R.id.quest19);
        RadioGroup r20 = (RadioGroup) findViewById(R.id.quest20);
        RadioGroup r21 = (RadioGroup) findViewById(R.id.quest21);
        RadioGroup r22 = (RadioGroup) findViewById(R.id.quest22);
        RadioGroup r23 = (RadioGroup) findViewById(R.id.quest23);
        RadioGroup r24 = (RadioGroup) findViewById(R.id.quest24);
        RadioGroup r25 = (RadioGroup) findViewById(R.id.quest25);
        RadioGroup r26 = (RadioGroup) findViewById(R.id.quest26);
        RadioGroup r27 = (RadioGroup) findViewById(R.id.quest27);
        RadioGroup r28 = (RadioGroup) findViewById(R.id.quest28);
        RadioGroup r29 = (RadioGroup) findViewById(R.id.quest29);
        RadioGroup r30 = (RadioGroup) findViewById(R.id.quest30);
        RadioGroup r31 = (RadioGroup) findViewById(R.id.quest31);
        RadioGroup r32 = (RadioGroup) findViewById(R.id.quest32);
        choice.add(r1);
        choice.add(r2);
        choice.add(r3);
        choice.add(r4);
        choice.add(r5);
        choice.add(r6);
        choice.add(r7);
        choice.add(r8);
        choice.add(r9);
        choice.add(r10);
        choice.add(r11);
        choice.add(r12);
        choice.add(r13);
        choice.add(r14);
        choice.add(r15);
        choice.add(r16);
        choice.add(r17);
        choice.add(r18);
        choice.add(r19);
        choice.add(r20);
        choice.add(r21);
        choice.add(r22);
        choice.add(r23);
        choice.add(r24);
        choice.add(r25);
        choice.add(r26);
        choice.add(r27);
        choice.add(r28);
        choice.add(r29);
        choice.add(r30);
        choice.add(r31);
        choice.add(r32);
        boolean showResult = true;
        for (RadioGroup r : choice) {
            int selected = r.getCheckedRadioButtonId();
            if (selected == -1) {
                Context context = getApplicationContext();
                CharSequence text = "Please fill in all the answers";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                showResult = false;
            } else {
                RadioButton radioSelected = (RadioButton) findViewById(selected);
                String buttonChoice = radioSelected.getText().toString();
                int buttonScore = 0;
                if (buttonChoice.equals("no anxiety")) {
                    buttonScore = 1;
                } else if (buttonChoice.equals("slight anxiety")) {
                    buttonScore = 2;
                } else if (buttonChoice.equals("moderate anxiety")) {
                    buttonScore = 3;
                } else if (buttonChoice.equals("considerable anxiety")) {
                    buttonScore = 4;
                } else if (buttonChoice.equals("overwhelming anxiety")) {
                    buttonScore = 5;
                }
                if (r.equals(r1) || r.equals(r2) || r.equals(r3) || r.equals(r4) || r.equals(r5) || r.equals(r12) || r.equals(r13))
                    GFscore += buttonScore;
                if (r.equals(r6) || r.equals(r7) || r.equals(r8) || r.equals(r9) || r.equals(r10) || r.equals(r11) || r.equals(r14) || r.equals(r15) || r.equals(r16) || r.equals(r17) || r.equals(r18) || r.equals(r20))
                    AFscore += buttonScore;
                if (r.equals(r22) || r.equals(r23) || r.equals(r24) || r.equals(r25) || r.equals(r27) || r.equals(r28) || r.equals(r29) || r.equals(r30) || r.equals(r31) || r.equals(r32))
                    IFscore += buttonScore;
                score += buttonScore;
            }
        }
        if(showResult) {
            startActivity(new Intent(this, ShowFASResult.class));
        }
    }

    public void back(View v)
    {
        startActivity(new Intent(this, AllPrograms.class));
    }

    public static int getScore() {
        return score;
    }

}
