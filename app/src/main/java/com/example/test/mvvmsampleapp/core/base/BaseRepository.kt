package com.example.test.mvvmsampleapp.core.base

import android.arch.lifecycle.MutableLiveData
import com.example.test.mvvmsampleapp.core.MyApplication
import com.example.test.mvvmsampleapp.core.remote.ApiInterface

/**
 * Created by Javad Vatan on 11/17/2019
 * Sites: http://Jvatan.ir && http://JavadVatan.ir
 */
open class BaseRepository {
     var mLoading: MutableLiveData<Boolean> = MutableLiveData()
    protected var mApiAll: ApiInterface = MyApplication.getApiComponent().apiAll
}