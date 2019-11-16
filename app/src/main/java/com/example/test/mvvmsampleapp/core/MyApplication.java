package com.example.test.mvvmsampleapp.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.test.mvvmsampleapp.BuildConfig;
import com.example.test.mvvmsampleapp.core.di.component.ApiComponent;
import com.example.test.mvvmsampleapp.core.di.component.DaggerApiComponent;
import com.example.test.mvvmsampleapp.core.di.module.ContextModule;
import com.example.test.mvvmsampleapp.core.multilanguage.LocaleManager;
import com.facebook.stetho.Stetho;


public class MyApplication extends Application {
    private static volatile MyApplication instance;

    private static ApiComponent mApiComponent;
    private volatile Context mActivityContext;
    private volatile Context mApplicationContext;


    public static synchronized MyApplication getInstance() {
        return instance;
    }

    public static ApiComponent getApiComponent() {
        return mApiComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        instance = this;
        mApplicationContext = base;
        base = LocaleManager.setLocale(base);

        // Set again for init new locale.
        mApplicationContext = base;
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        logRxError();

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);

        mApiComponent = DaggerApiComponent.builder().contextModule(new ContextModule(this)).build();

    }

/*

    private void logRxError() {
        RxJavaPlugins.setErrorHandler(throwable -> {
            throwable.printStackTrace();
            Log.i("MyApplication", throwable.getMessage());
        }); // nothing or some logging
    }
*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

    public Context getActivityContext() {
        return mActivityContext;
    }

    public void setActivityContext(Context mContext) {
        this.mActivityContext = mContext;
    }

    public Context getApplicationContextt() {
        return mApplicationContext;
    }
}