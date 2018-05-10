package com.example.maxiaowu.societyapp.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maxiaowu on 2018/5/2.
 */

public class MusicTrack implements Parcelable {


    public long mId;
    public int mSourcePosition;

    public MusicTrack(long id,int sourcePosition) {
        mId = id;
        mSourcePosition = sourcePosition;
    }

    public MusicTrack(Parcel in){
        mId = in.readLong();
        mSourcePosition = in.readInt();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeInt(mSourcePosition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusicTrack> CREATOR = new Creator<MusicTrack>() {
        @Override
        public MusicTrack createFromParcel(Parcel in) {
            return new MusicTrack(in);
        }

        @Override
        public MusicTrack[] newArray(int size) {
            return new MusicTrack[size];
        }
    };
}
