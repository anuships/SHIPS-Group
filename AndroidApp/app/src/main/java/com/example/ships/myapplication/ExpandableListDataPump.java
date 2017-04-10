package com.example.ships.myapplication;

/**
 * Created by Jyun on 2017/04/10.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    static HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
    static HashMap<String, String> expandableListContent = new HashMap<String, String>();

    public  HashMap<String, String> setData(String title, String detail){
        expandableListContent.put(title, detail);
        return expandableListContent;
    }

    public HashMap<String, String> getContent(){
        return expandableListContent;
    }

    public static HashMap<String, List<String>> getData() {


        List<String> cricket = new ArrayList<String>();
        cricket.add("India");
        cricket.add("Pakistan");
        cricket.add("Australia");
        cricket.add("England");
        cricket.add("South Africa");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> basketball = new ArrayList<String>();
        basketball.add("United States");
        basketball.add("Spain");
        basketball.add("Argentina");
        basketball.add("France");
        basketball.add("Russia");

        expandableListDetail.put("CRICKET TEAMS", cricket);
        expandableListDetail.put("FOOTBALL TEAMS", football);
        expandableListDetail.put("BASKETBALL TEAMS", basketball);
        return expandableListDetail;
    }
}
