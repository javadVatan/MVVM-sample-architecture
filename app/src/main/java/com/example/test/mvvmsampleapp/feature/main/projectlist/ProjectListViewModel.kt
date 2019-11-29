package com.example.test.mvvmsampleapp.feature.main.projectlist

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.example.test.mvvmsampleapp.core.base.BaseViewModel
import com.example.test.mvvmsampleapp.feature.main.projectlist.model.Project

class ProjectListViewModel(application: Application) : BaseViewModel<ProjectListRepo>(application) {
    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    val projectListObservable: MutableLiveData<List<Project>>



    init {
        mRepository = ProjectListRepo()

        // If any transformation is needed, this can be simply done by Transformations class ...
        projectListObservable = mRepository.getProjectList("Google")
        mLoading = mRepository.mLoading
    }
}
