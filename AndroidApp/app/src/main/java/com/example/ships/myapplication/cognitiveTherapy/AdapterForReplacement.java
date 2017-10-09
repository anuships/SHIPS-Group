package com.example.ships.myapplication.cognitiveTherapy;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ships.myapplication.R;

import java.util.List;

import static com.example.ships.myapplication.R.drawable.sad;
import static com.example.ships.myapplication.R.drawable.smile;

/**
 * Created by user on 2017/9/15.
 */

class AdapterForReplacement extends BaseAdapter {
    List<ReplacedInfo> replacedInfos;
    Activity context;
    public void setDisplayNeg(Boolean displayNeg) {
        this.displayNeg = displayNeg;
    }

    Boolean displayNeg = true;

    public class Row{
        AppCompatTextView mTvTitle;
        AppCompatTextView mTvDescription;
        FrameLayout mFlWrapper;
        ImageView mIvArrow;
        ImageView emotion;
    }

    public AdapterForReplacement(Activity context, List<ReplacedInfo> replacedInfos){
        this.replacedInfos = replacedInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return replacedInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return replacedInfos.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.click_list_view, parent, false);
            theRow.mFlWrapper = (FrameLayout) convertView.findViewById(R.id.fl_wrapper);
            theRow.mTvTitle = (AppCompatTextView) convertView.findViewById(R.id.tv_title);
            theRow.mTvTitle.setTextSize(30);
            theRow.mTvDescription = (AppCompatTextView) convertView.findViewById(R.id.tv_description);
            theRow.mTvDescription.setTextSize(20);
            theRow.mIvArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            theRow.emotion = (ImageView) convertView.findViewById(R.id.emotion);

            convertView.setTag(theRow);
        }else{
            theRow = (Row) convertView.getTag();
        }

        // Update the View
        ReplacedInfo di = replacedInfos.get(position);
        if(di.isExpanded){
            theRow.mFlWrapper.setVisibility(View.VISIBLE);
            theRow.mIvArrow.setRotation(180f);
        }else{
            theRow.mFlWrapper.setVisibility(View.GONE);
            theRow.mIvArrow.setRotation(0f);
        }


        theRow.mTvTitle.setText(di.concerns);
        if (displayNeg){
            theRow.mTvDescription.setText(di.negativeSummary);
            theRow.emotion.setBackgroundDrawable(context.getResources().getDrawable(sad));
        } else {
            theRow.mTvDescription.setText(di.positiveSummary);
            theRow.emotion.setBackgroundDrawable(context.getResources().getDrawable(smile));
        }


        theRow.emotion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (displayNeg){
                    displayNeg = false;
                } else {
                    displayNeg = true;
                }
                ((CognitiveReplacement) context).refreshList();
            }
        });




        // return the view
        return convertView;
    }


}

