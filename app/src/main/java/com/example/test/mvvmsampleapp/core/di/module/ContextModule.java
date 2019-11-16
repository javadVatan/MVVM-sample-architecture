package com.example.test.mvvmsampleapp.core.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JavadVatan on 8/20/2018
 */
@Module
public class ContextModule {
    private final Context mContext;

    public ContextModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    @Singleton
    public Context initContext() {
        return mContext;
    }
}
