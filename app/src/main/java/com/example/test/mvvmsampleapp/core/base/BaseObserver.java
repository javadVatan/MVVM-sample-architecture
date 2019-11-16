package com.example.test.mvvmsampleapp.core.base;


import android.app.Application;
import android.widget.Toast;

import com.example.test.mvvmsampleapp.R;
import com.example.test.mvvmsampleapp.core.MyApplication;
import com.example.test.mvvmsampleapp.global.GlobalFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;




public abstract class BaseObserver<T> extends DisposableObserver<T> {
    public static final int CODE_EXCEPTION = 1/*-1*/;
    public static final int CODE_UPDATING = 10;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_INVALID_TOKEN = 4/* 51*/;
    public static final int CODE_USER_IS_BAN = 5/* 51*/;

    public static final int CODE_INVALID_VERSION_CODE = 3/*53*/;
    public static final int CODE_DEPRECATED_VERSION_CODE = 11/*9*//*54*/;

    public static final int CODE_LOGIN_USER_NOT_LOGIN = 7/*103*/;


    Application mApp = MyApplication.getInstance();
    GlobalFunction mFunction = GlobalFunction.Companion.getInstance();

    protected abstract void onSuccess(T t);

    protected abstract void onError(BaseApiModel baseData);

    // The return boolean shows it can create toast or not
    protected abstract boolean onFailure(Error/*Throwable e*/e);

    @Override
    public void onNext(T t) {
        try {
            onSuccess(t);
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof SocketTimeoutException) {
            onFailure(Error.SERVER_TIMEOUT_EXEPTION);
            return;
        }

        //region manage Http Error
        if (e instanceof HttpException) {
            BaseApiModel baseData = new BaseApiModel();
            //   Retrofit mRetrofit = MyApplication.getApiComponent().getRetrofit();
            HttpException httpException = (HttpException) e;
            //   ErrorUtils.parseError(httpException.response(), mRetrofit);
            ResponseBody body = httpException.response().errorBody();

            //parse json
            try {
                JSONObject object = new JSONObject(body.string());
                baseData.setCode(object.getInt("code"));
                baseData.setMessage(object.getString("message"));
            } catch (JSONException | IOException e1) {
                e1.printStackTrace();
            }

            manageHttpCode(httpException.code(), baseData);
            return;
        }
        //endregion

        if (!mFunction.isNetworkAvailable())
            onFailure(Error.SERVER_NOT_NETWORK_AVAILABLE_ERROR);
        else {
            boolean isToastDisabled= onFailure(Error.SERVER_UNEXPECTED_ERROR);
            if (!isToastDisabled/* && mFunction.isActivityRunning(mApp.getActivityContext())*/) {

                Toast.makeText(mApp, mApp.getString(R.string.error_unexpected), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void manageHttpCode(int code, BaseApiModel baseData) {
        switch (code) {
     /*       //Unauthorized
            case 401:

                break;*/

            case 403:
            case 400:
            case 404:
                manageCustomCode(baseData);
                break;

            //Too Many Requests
            case 429:
  /*              if (mFunction.isActivityRunning(mApp.getActivityContext())) {
                    new Toast(Toast.TYPE_DANGER, mApp.getString(R.string.error_too_many_request)).show();
                }*/
                this.onFailure(Error.SERVER_UNEXPECTED_ERROR);
                break;

            //Internal ServerConst Error
            case 500:
            case 502:
            case 503:
                Toast.makeText(mApp, mApp.getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                this.onFailure(Error.SERVER_UNEXPECTED_ERROR);
                break;
        }
    }

    @Override
    public void onComplete() {

    }

    private void manageCustomCode(BaseApiModel baseData) {
        switch (baseData.getCode()) {
            case BaseObserver.CODE_INVALID_VERSION_CODE:
            case BaseObserver.CODE_LOGIN_USER_NOT_LOGIN:
            case BaseObserver.CODE_INVALID_TOKEN:
//                new Toast(Toast.TYPE_DANGER, Application.getInstance().getString(R.string.you_need_login_again)).show();
                break;

            case BaseObserver.CODE_DEPRECATED_VERSION_CODE:
                manageDeprecatedVersionCode(baseData);
                break;

            case BaseObserver.CODE_EXCEPTION:
                Toast.makeText(mApp, mApp.getString(R.string.error_unexpected), Toast.LENGTH_SHORT).show();
                onError(baseData);
                break;

            case BaseObserver.CODE_UPDATING:
                Toast.makeText(mApp, baseData.getMessage(), Toast.LENGTH_SHORT).show();
                onError(baseData);
                break;

            case BaseObserver.CODE_USER_IS_BAN:
                Toast.makeText(mApp, baseData.getMessage(), Toast.LENGTH_SHORT).show();
/*                if (Setting.isUserLogin())
                    Application.getInstance().clearDataAndLogout();*/
                onFailure(Error.SERVER_UNEXPECTED_ERROR);
                break;

            default:
                onError(baseData);
                break;
        }
    }

    public enum Error {
        SERVER_UNEXPECTED_ERROR,
        SERVER_TIMEOUT_EXEPTION,
        SERVER_NOT_NETWORK_AVAILABLE_ERROR
    }

    private void manageDeprecatedVersionCode(BaseApiModel baseData) {
   /*     String updateUrl;
        if (baseData != null && baseData.getMessage().length() != 0) {
            updateUrl = baseData.getMessage();

            Setting.setUpdateData(NotificationFireBase.TYPE_UPDATE_FORCE, updateUrl);

            // There was bug, sometimes the SecurePreferences is null (that means user status is not login)
            // and it can the app crashed. I (Javad Vatan) added this condition for work properly.
            if (SecurePreferences.getInstance() != null)
                Application.getInstance().safeLogout();
            else
                Application.getInstance().clearDataAndLogout();

            showUpdateNotification(updateUrl);
        }*/
    }
/*
    private void showUpdateNotification(String url) {
        String title = mApp.getApplicationContextt().getResources().getString(R.string.titlt_force_update);
        String detail = mApp.getApplicationContextt().getString(R.string.detail_force_update);

        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        new NotificationMaker.Builder(Constants.NOTIFICATION_ID_LOCAL_UPDATE,
                Constants.NOTIFICATION_CHANEL_LOCAL_UPDATE, title)
                .body(detail)
                .contentIntent(notificationIntent)
                .build();

    }*/
}

