package com.example.ships.myapplication;

/**
 * Created by Jyun on 2017/04/03.
 */

public class Programs {
    private String programName;
    private boolean selected = false;

    Programs(String programName, boolean selected){
        this.programName = programName;
        this.selected = selected;
    }

    public String getName(){
        return this.programName;
    }

    public boolean getValue(){
        return this.selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isClick(){
        return  selected;
    }

    public void setProgramName(String programName){
        this.programName = programName;
    }
}
