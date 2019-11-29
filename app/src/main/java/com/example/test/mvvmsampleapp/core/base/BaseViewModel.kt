package com.example.test.mvvmsampleapp.core.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

/**
 * Created by Javad Vatan on 11/17/2019
 * Sites: http://Jvatan.ir && http://JavadVatan.ir
 */
open class BaseViewModel<T : BaseRepository>(application: Application): AndroidViewModel(application) {
    lateinit var mLoading: MutableLiveData<Boolean>
    protected lateinit var mRepository: T

}