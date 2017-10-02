package com.example.ships.myapplication.modules;

/**
 * Created by Jyun on 2017/04/10.
 */
//reference: https://goo.gl/MrPdIi

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapterMyProgram extends BaseExpandableListAdapter {
    private Context context;
    private Intent intent;
    ExpandableListDataPump dataDump = new ExpandableListDataPump();

    private List<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail;
    int position;
    ExpandableListView[] viewCache;
    public CustomExpandableListAdapterMyProgram(Context context, List<String> expandableListTitle,
                                                HashMap<String, String> expandableListDetail, Intent intent) {
        this.context = context;
        this.intent = intent;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        viewCache = new ExpandableListView[200];
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition));
    }

    public Object getChildTitle(int listPosition, int expandedListPosition){
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ExpandableListDataPump dataDump = new ExpandableListDataPump();

        List<String> expandableListTitle;
        HashMap<String, String> expandableListDetail;
        View v = null;
        if (listPosition > 0){
            return convertView;
        }
        System.out.println(parent);
        if (viewCache[expandedListPosition] != null){
            v = viewCache[expandedListPosition];
            viewCache[expandedListPosition].notifyAll();
        }else {
                ExpandableListView expandableListView = new ExpandableListView(context);
                SQLiteDatabase mySqlDB = DBManager.getInstance(context).getWritableDatabase();
                Cursor resTID = mySqlDB.rawQuery("SELECT TID FROM treatmentplan WHERE UID=? ;", new String[]{intent.getExtras().getString("uid")});
                resTID.moveToFirst();
                System.out.println("LIST POS " + listPosition + "expanded " + expandedListPosition);
                for (int i = 0; i < resTID.getCount(); i++) {
                    if (i >-1) {
                        Cursor resMID = mySqlDB.rawQuery("SELECT MID FROM user_modules WHERE TID=? ;", new String[]{Integer.toString(resTID.getInt(0))});
                        resMID.moveToFirst();
                        for (int j = 0; j < resMID.getCount(); j++) {
                            Cursor resDesName = mySqlDB.rawQuery("SELECT name, desc FROM modules WHERE MID=?", new String[]{Integer.toString(resMID.getInt(0))});
                            resDesName.moveToFirst();
                            dataDump.setData((i + 1) + ":" + (j + 1) + " " + resDesName.getString(0), resDesName.getString(1));
                            resMID.moveToNext();
                        }
                        resTID.moveToNext();

                        expandableListDetail = dataDump.getData();

                        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                        Collections.sort(expandableListTitle);
                        System.out.println(expandableListTitle);
                        CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(context, expandableListTitle,
                                expandableListDetail, intent);
                        expandableListView.setAdapter(expandableListAdapter);
                        viewCache[expandedListPosition] = expandableListView;
                        v = expandableListView;
                    }
                }

            }

        return v;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return listPosition;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (viewCache[listPosition] == null) {
            String listTitle ="HHH"; //(String) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandablelist_groups, null);
            }
            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.listTitle);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);

        }else {
            viewCache[listPosition].notifyAll();
            return convertView;
        }
        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
    class ChildHolder {
        Button secButton;
    }
    public void onClick(View v) {
        System.out.println("CLICKED!!!22");

    }
}
