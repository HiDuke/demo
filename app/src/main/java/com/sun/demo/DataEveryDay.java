package com.sun.demo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataEveryDay implements Parcelable {
    private float mTemperature;
    private Date mTime;

    public DataEveryDay(float mTemperature, long time) {
        this.mTemperature = mTemperature;
        this.mTime = new Date(time);
        Log.e("cheng2",""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mTime));

    }


    protected DataEveryDay(Parcel in) {
        mTemperature = in.readFloat();
//        Log.e("cheng4",""+in.readLong());
        mTime = new Date(in.readLong());
//        Log.e("cheng5",""+in.readLong());
    }

    public static final Creator<DataEveryDay> CREATOR = new Creator<DataEveryDay>() {
        @Override
        public DataEveryDay createFromParcel(Parcel in) {
            return new DataEveryDay(in);
        }

        @Override
        public DataEveryDay[] newArray(int size) {
            return new DataEveryDay[size];
        }
    };

    public float getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(float mTemperature) {
        this.mTemperature = mTemperature;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        Log.e("cheng3",""+mTime.getTime());
        dest.writeFloat(mTemperature);
        dest.writeLong(mTime.getTime());


    }
}
