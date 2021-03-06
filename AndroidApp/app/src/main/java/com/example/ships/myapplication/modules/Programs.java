package com.example.ships.myapplication.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ships.myapplication.EMDR.EMDRActivity;
import com.example.ships.myapplication.EMDR.EMDRActivitySettings;
import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.relaxationAudio.RelaxationAudioActivity;

/**
 * Created by Jyun on 2017/04/03.
 */

public class Programs {
    String programName;
    int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    Programs(String programName, int value){
        this.programName = programName;
        this.value = value;
    }

    public String getName(){
        return this.programName;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public void setProgramName(String programName){
        this.programName = programName;
    }

}
