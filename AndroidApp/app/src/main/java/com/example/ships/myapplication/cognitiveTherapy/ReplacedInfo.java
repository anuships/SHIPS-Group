package com.example.ships.myapplication.cognitiveTherapy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017/9/15.
 */

class ReplacedInfo implements Parcelable {

    public String concerns;
    public String positiveSummary;
    public String negativeSummary;
    public boolean isExpanded;

    public ReplacedInfo(){}

    public ReplacedInfo(Parcel in){
        concerns = in.readString();
        positiveSummary = in.readString();
        negativeSummary = in.readString();
        isExpanded = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(concerns);
        dest.writeString(positiveSummary);
//        dest.writeString(negativeSummary);
        dest.writeInt(isExpanded ? 1 : 0);
    }

    public static final Parcelable.Creator<ReplacedInfo> CREATOR = new Parcelable.Creator<ReplacedInfo>(){
        @Override
        public ReplacedInfo createFromParcel(Parcel source) {
            return new ReplacedInfo(source);
        }

        @Override
        public ReplacedInfo[] newArray(int size) {
            return new ReplacedInfo[size];
        }
    };
}
