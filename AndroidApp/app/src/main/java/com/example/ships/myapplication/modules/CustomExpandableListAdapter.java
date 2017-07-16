package com.example.ships.myapplication.modules;

/**
 * Created by Jyun on 2017/04/10.
 */

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ships.myapplication.R;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail;
    int position;
    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, String> expandableListDetail) {
        this.context = context;
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
        System.out.println(expandedTitle);
        innerTitleViw.setText(expandedTitle);
        ch.secButton = (Button) convertView.findViewById(R.id.liBut);
        ch.secButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = (LinearLayout) v.getParent();
                TextView inner = (TextView) ll.findViewById(R.id.innerTitle);
                System.out.println(inner.getText().toString());
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
