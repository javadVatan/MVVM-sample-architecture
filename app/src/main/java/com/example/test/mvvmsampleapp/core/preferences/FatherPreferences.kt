package com.example.test.mvvmsampleapp.core.preferences

import android.content.Context
import com.example.test.mvvmsampleapp.core.MyApplication
import com.google.gson.Gson


/**
 * Created by Javad Vatan on 10/24/2019
 * Sites: http://Jvatan.ir && http://JavadVatan.ir
 */
open class FatherPreferences {
    lateinit var mContext: Context
    lateinit var mGson :Gson

    init {
        mContext = MyApplication.getInstance().applicationContextt
        mGson = Gson()
    }
}
