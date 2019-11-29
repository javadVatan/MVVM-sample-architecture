package com.example.test.mvvmsampleapp.feature.main.projectlist

import android.arch.lifecycle.MutableLiveData
import com.example.test.mvvmsampleapp.core.base.BaseApiModel
import com.example.test.mvvmsampleapp.core.base.BaseObserver
import com.example.test.mvvmsampleapp.core.base.BaseRepository
import com.example.test.mvvmsampleapp.feature.main.projectlist.model.Project
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectListRepo : BaseRepository() {

    fun getProjectList(userId: String): MutableLiveData<List<Project>> {
        val data = MutableLiveData<List<Project>>()
        mLoading.value = true

        mApiAll.getProjectList(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object  : BaseObserver<List<Project>>(){
                    override fun onSuccess(t:List<Project>?) {
                        data.value = t
                        mLoading.value = false
                    }

                    override fun onError(baseData: BaseApiModel<*>?) {
                        mLoading.value = false
                    }

                    override fun onFailure(e: Error?): Boolean {
                        mLoading.value = false
                        return  false
                    }
                })
        return data
    }
}
