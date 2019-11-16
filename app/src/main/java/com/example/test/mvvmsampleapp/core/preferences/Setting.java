package com.example.test.mvvmsampleapp.core.preferences;


import com.example.test.mvvmsampleapp.global.Constants;

/**
 * Created by Javad Vatan on 2/5/2018.
 */

public class Setting {
//    private static Setting myInstance;

    private Setting() {
    }

    /*
     ***************************** GlobalPreferences ********************************************
     * These Preferences can remove while user logout.
     */

    public static String getEmail() {
        return GlobalPreferences.getInstance().getString(Constants.SHARED_PREF_EMAIL);
    }

    public static void setEmail(String email) {
        GlobalPreferences.getInstance().setString(Constants.SHARED_PREF_EMAIL, email);
    }

    public static String getToken() {
        return GlobalPreferences.getInstance().getString(Constants.SHARED_PREF_TOKEN);
    }

    public static void setToken(String token) {
        GlobalPreferences.getInstance().setString(Constants.SHARED_PREF_TOKEN, token);
    }

    public static boolean isUserLogin() {
        if (GlobalPreferences.getInstance() != null) {
            String token = Setting.getToken();
            return token != null && token.length() != 0;
        }
        return false;
    }

    public static void isUserLogin(User mUserListener) {
        if (mUserListener == null) return;

        if (GlobalPreferences.getInstance() != null) {
            String token = Setting.getToken();

            if (token != null && token.length() != 0)
                mUserListener.onToken(token);
            else
                mUserListener.onFailed();
        } else
            mUserListener.onFailed();
    }

    public interface User {
        void onToken(String token);

        void onFailed();
    }

    /*
     ******************************* StaticPreferences ********************************************
     * These Preferences dose not remove while user logout.
     */

    public static boolean isFirstRun() {
        return StaticPreferences.getInstance().getBoolean(Constants.SHARED_PREF_FIRST_RUN);
    }

    public static void setFirstRun(boolean isFirstRun) {
        StaticPreferences.getInstance().setBoolean(Constants.SHARED_PREF_FIRST_RUN, isFirstRun);
    }

}
