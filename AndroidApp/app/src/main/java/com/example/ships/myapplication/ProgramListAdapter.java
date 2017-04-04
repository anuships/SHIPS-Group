package com.example.ships.myapplication;

import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

/**
 * Created by Jyun on 2017/04/03.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ProgramListAdapter extends ArrayAdapter<Programs> {
    private ArrayList<Programs> programList;
    Context context;

    public ProgramListAdapter(Context context, int textViewResourceId,
                           ArrayList<Programs> programList) {
        super(context, textViewResourceId, programList);
        this.context = context;
        this.programList = programList;
    }

    private class ViewHolder {
        TextView programTitle;
        CheckBox isChecked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.programs_rows, null);

            holder = new ViewHolder();
            holder.programTitle = (TextView) convertView.findViewById(R.id.ProgamTitle);
            holder.isChecked = (CheckBox) convertView.findViewById(R.id.checkProgram);
            convertView.setTag(holder);

            holder.isChecked.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Programs program = (Programs) cb.getTag();
                    program.setSelected(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Programs program = programList.get(position);
        holder.programTitle.setText(program.getName());
        holder.isChecked.setText(program.getName());
        holder.isChecked.setChecked(false);
        holder.isChecked.setTag(program);

        return convertView;
    }

}


