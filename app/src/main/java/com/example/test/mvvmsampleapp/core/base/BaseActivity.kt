package com.example.test.mvvmsampleapp.core.base


import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.test.mvvmsampleapp.BuildConfig
import com.example.test.mvvmsampleapp.core.MyApplication
import com.example.test.mvvmsampleapp.core.multilanguage.LocaleManager

abstract class BaseActivity <T : BaseViewModel<*>>: AppCompatActivity() {
    protected var app = MyApplication.getInstance()
    protected lateinit var mContext: Context
    protected lateinit var mViewModel:T

    override fun attachBaseContext(base: Context) {
        app.activityContext = this
        mContext = base
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    fun log(text: String) {
        if (BuildConfig.DEBUG) {
            Log.wtf("P-GUM DEBUG", text)
        }
    }

    fun toast(resId: Int) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val PACKAGE_HASH_CODE = 1717078405
    }

}
