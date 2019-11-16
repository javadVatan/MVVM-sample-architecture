package com.example.test.mvvmsampleapp.core.di.module;

import com.example.test.mvvmsampleapp.core.remote.ApiInterface;
import com.example.test.mvvmsampleapp.global.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)

public class ApiServerModule {

    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }


    @Provides
    public Retrofit retrofit(Gson gson, OkHttpClient client) {

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    public ApiInterface apiServerModule(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

}