package com.example.ships.myapplication.cognitiveTherapy;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.ships.myapplication.R;

import java.util.List;

/**
 * Created by user on 2017/7/29.
 */

public class AdapterForDetail extends BaseAdapter {

    List<DetailedInfo> detailedInfos;
    Context context;

    public class Row{
        AppCompatTextView mTvTitle;
        AppCompatTextView mTvDescription;
        FrameLayout mFlWrapper;
        ImageView mIvArrow;
    }

    public AdapterForDetail(Context context, List<DetailedInfo> detailedInfos){
        this.detailedInfos = detailedInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return detailedInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return detailedInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Row theRow;
        if(convertView == null){
            theRow = new Row();
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
            theRow.mFlWrapper = (FrameLayout) convertView.findViewById(R.id.fl_wrapper);
            theRow.mTvTitle = (AppCompatTextView) convertView.findViewById(R.id.tv_title);
            theRow.mTvDescription = (AppCompatTextView) convertView.findViewById(R.id.tv_description);
            theRow.mIvArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);

            convertView.setTag(theRow);
        }else{

            theRow = (Row) convertView.getTag();
        }

        // Update the View
        DetailedInfo di = detailedInfos.get(position);
        if(di.isExpanded){
            theRow.mFlWrapper.setVisibility(View.VISIBLE);
            theRow.mIvArrow.setRotation(180f);
        }else{
            theRow.mFlWrapper.setVisibility(View.GONE);
            theRow.mIvArrow.setRotation(0f);
        }

        theRow.mTvTitle.setText(di.concerns);
        theRow.mTvDescription.setText(di.explanations);


        // return the view
        return convertView;
    }
}

