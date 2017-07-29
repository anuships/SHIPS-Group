package com.example.ships.myapplication.cognitiveTherapy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017/7/29.
 */

public class DetailedInfo implements Parcelable {

        public String concerns;
        public String explanations;
        public boolean isExpanded;

        public DetailedInfo(){}

        public DetailedInfo(Parcel in){
            concerns = in.readString();
            explanations = in.readString();
            isExpanded = in.readInt() == 1;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(concerns);
            dest.writeString(explanations);
            dest.writeInt(isExpanded ? 1 : 0);
        }

        public static final Parcelable.Creator<DetailedInfo> CREATOR = new Parcelable.Creator<DetailedInfo>(){
            @Override
            public DetailedInfo createFromParcel(Parcel source) {
                return new DetailedInfo(source);
            }

            @Override
            public DetailedInfo[] newArray(int size) {
                return new DetailedInfo[size];
            }
        };
}
