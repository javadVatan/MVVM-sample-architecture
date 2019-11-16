package com.example.test.mvvmsampleapp.core.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseApiModel<T> {
    @Expose
    @SerializedName("data")
    public T data;
    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("code")
    public int code;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
