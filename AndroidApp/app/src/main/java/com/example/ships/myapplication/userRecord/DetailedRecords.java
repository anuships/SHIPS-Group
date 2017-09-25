package com.example.ships.myapplication.userRecord;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ships.myapplication.cognitiveTherapy.DetailedInfo;

/**
 * Created by user on 2017/9/9.
 */

class DetailedRecords implements Parcelable {
    public String modules;
    public String details;
    public boolean isExpanded;

    public DetailedRecords(){}

    public DetailedRecords(Parcel in){
        modules = in.readString();
        details = in.readString();
        isExpanded = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(modules);
        dest.writeString(details);
        dest.writeInt(isExpanded ? 1 : 0);
    }

    public static final Parcelable.Creator<DetailedRecords> CREATOR = new Parcelable.Creator<DetailedRecords>(){
        @Override
        public DetailedRecords createFromParcel(Parcel source) {
            return new DetailedRecords(source);
        }

        @Override
        public DetailedRecords[] newArray(int size) {
            return new DetailedRecords[size];
        }
    };
}
