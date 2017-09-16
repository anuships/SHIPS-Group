package com.example.ships.myapplication.userRecord;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.ships.myapplication.R;
import com.example.ships.myapplication.cognitiveTherapy.AdapterForDetail;
import com.example.ships.myapplication.cognitiveTherapy.DetailedInfo;

import java.util.List;

/**
 * Created by user on 2017/9/9.
 */

public class AdapterForRecords extends BaseAdapter {
        List<DetailedRecords> detailedRecords;
        Context context;

        public class Row{
            AppCompatTextView mTvTitle;
            AppCompatTextView mTvDescription;
            FrameLayout mFlWrapper;
            ImageView mIvArrow;
        }

        public AdapterForRecords(Context context, List<DetailedRecords> detailedRecords){
            this.detailedRecords = detailedRecords;
            this.context = context;
        }

        @Override
        public int getCount() {
            return detailedRecords.size();
        }

        @Override
        public Object getItem(int position) {
            return detailedRecords.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            com.example.ships.myapplication.userRecord.AdapterForRecords.Row theRow;
            if(convertView == null){
                theRow = new com.example.ships.myapplication.userRecord.AdapterForRecords.Row();
                convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
                theRow.mFlWrapper = (FrameLayout) convertView.findViewById(R.id.fl_wrapper);
                theRow.mTvTitle = (AppCompatTextView) convertView.findViewById(R.id.tv_title);
                theRow.mTvTitle.setTextSize(30);
                theRow.mTvDescription = (AppCompatTextView) convertView.findViewById(R.id.tv_description);
                theRow.mTvDescription.setTextSize(30);
                theRow.mIvArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
                convertView.setTag(theRow);
            }else{
                theRow = (com.example.ships.myapplication.userRecord.AdapterForRecords.Row) convertView.getTag();
            }

            // Update the View
            DetailedRecords di = detailedRecords.get(position);
            if(di.isExpanded){
                theRow.mFlWrapper.setVisibility(View.VISIBLE);
                theRow.mIvArrow.setRotation(180f);
            }else{
                theRow.mFlWrapper.setVisibility(View.GONE);
                theRow.mIvArrow.setRotation(0f);
            }

            theRow.mTvTitle.setText(di.modules);
            theRow.mTvDescription.setText(di.details);
            // return the view
            return convertView;
        }
}
