package com.example.ships.myapplication;

/**
 * Created by Jyun on 2017/04/10.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    private HashMap<String, String> expandableListContent = new HashMap<String, String>();

    public  void setData(String title, String detail){
        expandableListContent.put(title, detail);
    }

    public HashMap<String, String> getData(){
        return expandableListContent;
    }

}
