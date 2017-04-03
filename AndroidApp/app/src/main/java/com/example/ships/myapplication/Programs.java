package com.example.ships.myapplication;

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
}
