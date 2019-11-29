package com.example.test.mvvmsampleapp.core.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Javad Vatan on 2/28/2018.
 */

public class StaticPreferences extends FatherPreferences{

    private static StaticPreferences myInstance;
    private SharedPreferences mSharedPreferences;

    private StaticPreferences() {
        mSharedPreferences = mContext.getSharedPreferences(
                " com.example.test.mvvmsampleapp.core.static_preferences", Context.MODE_PRIVATE);
    }

    public static synchronized StaticPreferences getInstance() {
        if (myInstance == null) {
            myInstance = new StaticPreferences();
        }
        return myInstance;
    }

    public boolean getBoolean(String key) {
        return getInstance().mSharedPreferences.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean aValue) {
        getInstance().mSharedPreferences.edit().putBoolean(key, aValue).apply();
    }

    public void setInt(String key, int aValue) {
        getInstance().mSharedPreferences.edit().putInt(key, aValue).apply();
    }

    public int getInt(String key) {
        return getInstance().mSharedPreferences.getInt(key, 0);
    }

    public void setString(String key, String val) {
        getInstance().mSharedPreferences.edit().putString(key, val).apply();
    }

    public String getString(String key) {
        return getInstance().mSharedPreferences.getString(key, "");
    }

    public String getString(String key, String defValue) {
        return getInstance().mSharedPreferences.getString(key, defValue);
    }

    public void setLong(String key, long aValue) {
        getInstance().mSharedPreferences.edit().putLong(key, aValue).apply();
    }

    public long getLong(String key) {
        return getInstance().mSharedPreferences.getLong(key, 0);
    }

}
