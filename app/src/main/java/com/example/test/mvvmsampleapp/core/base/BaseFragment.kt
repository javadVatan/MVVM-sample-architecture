package com.p_gum.p_gum.core.base


import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.test.mvvmsampleapp.core.MyApplication
import com.example.test.mvvmsampleapp.core.base.BaseViewModel
import com.example.test.mvvmsampleapp.core.multilanguage.LocaleManager


abstract class BaseFragment <T : BaseViewModel<*>> : Fragment() {

    protected var app = MyApplication.getInstance()
    protected var rootView: View? = null
    protected var mContext: Context? = null
    protected var mActivity: Activity? = null
    protected lateinit var mViewModel:T


    init {
        LocaleManager.setLocale(app.applicationContextt)
    }
/*
    override fun initComposite(view: View, savedInstanceState: Bundle?) {
        super.initComposite(view, savedInstanceState)
            mPresenter?.initComposite()

    }*/


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }


    fun toast(resId: Int) {
        Toast.makeText(context, getString(resId), Toast.LENGTH_SHORT).show()
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mActivity = activity
    }

}
