package com.example.ships.myapplication.modules;

/**
 * Created by Jyun on 2017/04/10.
 */
//reference: https://goo.gl/MrPdIi

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

}
