package com.example.ships.myapplication.programlist;

import android.widget.ArrayAdapter;

/**
 * Created by Jyun on 2017/04/03.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ships.myapplication.R;

import java.util.ArrayList;

public class ProgramListAdapter extends ArrayAdapter<Programs> {
    ArrayList<Programs> programsItems = null;
    Context context;
    public ProgramListAdapter(Context context, ArrayList<Programs> resource) {
        super(context, R.layout.programs_rows,resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.programsItems = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.programs_rows, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.ProgamTitle);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkProgram);
        name.setText(programsItems.get(position).getName());
        if(programsItems.get(position).getValue() == 1) {
            cb.setChecked(true);
        }
        else{
            cb.setChecked(false);}
        return convertView;
    }


}
