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
    public static final int CODE_USER_IS_BAN = 5/* 51*/;
    public static final int CODE_DEPRECATED_VERSION_CODE = 11/*9*//*54*/;


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
            boolean isToastDisabled = onFailure(Error.SERVER_UNEXPECTED_ERROR);
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
                boolean isToastDisabled = this.onFailure(Error.SERVER_UNEXPECTED_ERROR);
                if (!isToastDisabled)
                    Toast.makeText(mApp, mApp.getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onComplete() {

    }

    private void manageCustomCode(BaseApiModel baseData) {
        switch (baseData.getCode()) {

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

    }
}


