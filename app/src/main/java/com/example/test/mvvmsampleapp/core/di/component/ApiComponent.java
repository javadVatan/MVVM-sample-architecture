package com.example.test.mvvmsampleapp.core.di.component;


import com.example.test.mvvmsampleapp.core.di.module.ApiServerModule;
import com.example.test.mvvmsampleapp.core.remote.ApiInterface;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by JavadVatan on 8/20/2018
 */


@Singleton
@Component(modules = {ApiServerModule.class})
public interface ApiComponent {
    ApiInterface getApiAll();

    Retrofit getRetrofit();

//    void inject(BasePresenter<BaseView> basePresenter);
}
