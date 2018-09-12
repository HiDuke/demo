package com.sun.demo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class App extends Application {
    private static final String TABLE_NAME = "tablename";
    private SharedPreferences mTablename;

    @Override
    public void onCreate() {
        super.onCreate();
        mTablename = getSharedPreferences(App.class.getName(), Context.MODE_PRIVATE);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        setmTablename("");
    }

    public String getmTablename() {
        return mTablename.getString(TABLE_NAME, "");
    }

    public void setmTablename(String tablename) {
        mTablename.edit().putString(TABLE_NAME, tablename).commit();
    }
}
