package com.example.test.mvvmsampleapp.core.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Javad Vatan on 2/28/2018.
 */

public class GlobalPreferences extends FatherPreferences {

    private static GlobalPreferences myInstance;
    private SharedPreferences mSharedPreferences;


    private GlobalPreferences() {
        mSharedPreferences =  PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static synchronized GlobalPreferences getInstance() {
        if (myInstance == null) {
            myInstance = new GlobalPreferences();
        }
        return myInstance;
    }

    public boolean getBoolean(String key) {
        return getInstance().mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getInstance().mSharedPreferences.getBoolean(key, defValue);
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

    public int getInt(String key, int defValue) {
        return getInstance().mSharedPreferences.getInt(key, defValue);
    }

    public void setString(String key, String val) {
        getInstance().mSharedPreferences.edit().putString(key, val).apply();
    }

    public String getString(String key) {
        return getInstance().mSharedPreferences.getString(key, "");
    }

    public void setLong(String key, long aValue) {
        getInstance().mSharedPreferences.edit().putLong(key, aValue).apply();
    }

    public void putModel(Object model) {
        if (model == null)
            throw new IllegalArgumentException("model is null");

        getInstance().mSharedPreferences.edit().putString(model.getClass().getCanonicalName(), mGson.toJson(model)).apply();
    }

    public <T> T getModel(Class<T> a) {
        String json = getInstance().mSharedPreferences.getString(a.getCanonicalName(),null);
        if (json == null)
            return null;
        try {
            return mGson.fromJson(json, a);
        } catch (Exception e) {
            throw new IllegalArgumentException("Model stored with key " + a.getCanonicalName() + " is instanceof other class");
        }
    }

    public long getLong(String key) {
        return getInstance().mSharedPreferences.getLong(key, 0);
    }

    public long getLong(String key, long defValue) {
        return getInstance().mSharedPreferences.getLong(key, defValue);
    }
    public void clearPreferences() {
        getInstance().mSharedPreferences.edit().clear().apply();
    }
}
