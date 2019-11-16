/*
package com.example.test.mvvmsampleapp.core.di.module;

import com.p_gum.p_gum.activity.signin.model.TokenRefreshModel;
import com.p_gum.p_gum.core.Application;
import com.p_gum.p_gum.core.base.BaseApiModel;
import com.p_gum.p_gum.core.preferences.Setting;
import com.p_gum.p_gum.core.server.ApiMethod;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

//import com.p_gum.p_gum.core.server.SocketManager;


*/
/**
 * Created by Javad Vatan on 10/27/2018
 * Sites: http://Jvatan.ir && http://JavadVatan.ir
 *//*

public class TokenAuthenticator implements Authenticator {
    private boolean isLockRefresh = false;

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (isLockRefresh) return null;

        isLockRefresh = true;

        Call<BaseApiModel<TokenRefreshModel>> refreshCall = new ApiMethod().refreshToken();

        //make it as retrofit synchronous call
        retrofit2.Response<BaseApiModel<TokenRefreshModel>> refreshResponse = refreshCall.execute();
        if (refreshResponse != null && refreshResponse.code() == 201) {
            String token = refreshResponse.body().getData().getApiToken();
            Setting.setToken(token);
            isLockRefresh = false;

*/
/*
            SocketManager mSocket = SocketManager.Companion.getInstance();
            // Authorize Socket with new token
            if (mSocket != null)
                mSocket.tryVerifyUser(token);*//*


            return response.request().newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        } else if (refreshResponse != null && refreshResponse.code() == 401) {
            isLockRefresh = false;
            Application.getInstance().clearDataAndLogout();
        }

        return null;
    }

}*/
