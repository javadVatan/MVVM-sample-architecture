package com.example.test.mvvmsampleapp.core.di.module;


import android.content.Context;

import com.example.test.mvvmsampleapp.BuildConfig;
import com.example.test.mvvmsampleapp.core.preferences.Setting;
import com.example.test.mvvmsampleapp.global.GlobalFunction;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.readystatesoftware.chuck.ChuckInterceptor;

/**
 * Created by JavadVatan on 8/20/2018
 */

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    public OkHttpClient okHttpClient(Cache cache, Context context) {
//        installServiceProviderIfNeeded(context);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder builder1 = original.newBuilder();
                          /*  .addHeader(Constants.HEADER_AUTHORIZATION, getToken())
                            .addHeader(Constants.HEADER_LANGUAGE, Setting.getUserLocal())
                            .addHeader(Constants.HEADER_PACKAGE_NAME, context.getPackageName())
                            .addHeader(Constants.HEADER_TIME_ZONE, TimeZone.getDefault().getID())
                            .addHeader(Constants.HEADER_VERSION_CODE, String.valueOf(BuildConfig.VERSION_CODE));*/
            Request request = builder1.build();

            if (!GlobalFunction.Companion.getInstance().isNetworkAvailable()) {
                return new Response.Builder()
                        .code(600) //Simply put whatever value you want to designate to aborted request.
                        .request(chain.request())
                        .build();
            }

            return chain.proceed(request);
        });
//        okHttpClient.authenticator(new TokenAuthenticator());
        okHttpClient.cache(cache);
        okHttpClient.readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG)
            okHttpClient.addInterceptor(new ChuckInterceptor(context));

        return okHttpClient.build();
    }

/*    private void installServiceProviderIfNeeded(Context context) {
        try {
            ProviderInstaller.installIfNeeded(context);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();

            // Prompt the user to install/update/enable Google Play services.
            GooglePlayServicesUtil.showErrorNotification(e.getConnectionStatusCode(), context);

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }*/

    private String getToken() {
        String token = "";
        if (Setting.isUserLogin())
            token = "Bearer " + Setting.getToken();
        return token;
    }

    @Provides
    public Cache cache(File cachefile) {
        return new Cache(cachefile, 10 * 1000 * 1000);//10MB cache;
    }

    @Provides
    public File file(Context context) {
        File file_cache = new File(context.getCacheDir(), "okhttp_cache");
        file_cache.mkdirs();
        return file_cache;
    }
}
