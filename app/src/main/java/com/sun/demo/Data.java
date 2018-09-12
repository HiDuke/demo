package com.sun.demo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Data implements Parcelable {
    private String deviceId;
    private String latestTemperature;
    private Date earliestTime;

    public Date getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(Date earliestTime) {
        this.earliestTime = earliestTime;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    private Date latestTime;

    public Data(String deviceId, String lastTemperature, long earliestTime, long latestTime) {
        this.deviceId = deviceId;
        this.latestTemperature = lastTemperature;
        this.latestTime = new Date(latestTime);
        this.earliestTime = new Date(earliestTime);
    }

    protected Data(Parcel in) {
        deviceId = in.readString();
        latestTemperature = in.readString();
        earliestTime = new Date(in.readLong());
        latestTime = new Date(in.readLong());

    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLatestTemperature() {
        return latestTemperature;
    }

    public void setLastTemperature(String lastTemperature) {
        this.latestTemperature = lastTemperature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceId);
        dest.writeString(latestTemperature);
        dest.writeLong(earliestTime.getTime());
        dest.writeLong(latestTime.getTime());
    }
}
