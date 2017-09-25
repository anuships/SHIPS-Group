package com.example.ships.myapplication.modules;

/**
 * Created by Jyun on 2017/04/10.
 */
//reference: https://goo.gl/MrPdIi

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ships.myapplication.R;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Intent intent;
    private List<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail;
    int position;
    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, String> expandableListDetail, Intent intent) {
        this.context = context;
        this.intent = intent;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
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
         String expandedListText = (String) getChild(listPosition, expandedListPosition);
        String expandedTitle = (String) getChildTitle(listPosition, listPosition);
        ChildHolder ch = new ChildHolder();
        if (convertView == null) {
            TextView listTitleTextView = (TextView) parent.findViewById(R.id.listTitle);
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandablelist_items, null);
            TextView listTextView = (TextView) convertView.findViewById(R.id.innerTitle);
            listTextView.setText(getGroup(listPosition).toString());
            ch.secButton = (Button) convertView.findViewById(R.id.liBut);
            ch.secButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout ll = (LinearLayout) v.getParent();
                    TextView inner = (TextView) ll.findViewById(R.id.innerTitle);
                }
            });
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        TextView innerTitleViw = (TextView) convertView
                .findViewById(R.id.innerTitle);
        innerTitleViw.setText(expandedTitle);
        ch.secButton = (Button) convertView.findViewById(R.id.liBut);
        ch.secButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = (LinearLayout) v.getParent();
                TextView inner = (TextView) ll.findViewById(R.id.innerTitle);
                TextView desc = (TextView) ll.findViewById(R.id.expandedListItem);


                Bundle b = intent.getExtras();
                try{
                    System.out.println("ABOUT TO TRY");
                    int tid = Integer.parseInt(inner.getText().toString().substring(0,inner.getText().toString().indexOf(':')).trim());
                    int indx = Integer.parseInt(inner.getText().toString().substring(inner.getText().toString().indexOf(':')+1,inner.getText().toString().indexOf(' ')).trim());
                    String title = inner.getText().toString().substring(inner.getText().toString().indexOf(' '));
                    Intent in = new Intent(context, ModuleDescription.class);
                    System.out.println("TRIED!");
                    b.putInt("tid", tid);
                    b.putInt("indx", indx);
                    b.putString("title", title.trim());
                    b.putString("desc", desc.getText().toString());
                    in.putExtras(b);
                    System.out.println("TITLE + " + title);
                    context.startActivity(in);
                }catch(Exception e){
                    e.printStackTrace();

                    String title = inner.getText().toString().trim();
                    System.out.println("FAILED BEFORE" + title);

                    Intent in = new Intent(context, ModuleDescription.class);
                    b.putString("title", title);
                    b.putString("desc", desc.getText().toString());
                    in.putExtras(b);
                    context.startActivity(in);
                }

                //String title = inner.getText().toString().trim();

                //System.out.println("SKIPPED BEFORE :" + title);
                //Intent in = new Intent(context, ModuleDescription.class);
               // b.putString("title", title);
               // b.putString("desc", desc.getText().toString());
               // in.putExtras(b);
               // context.startActivity(in);
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
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
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandablelist_groups, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
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
