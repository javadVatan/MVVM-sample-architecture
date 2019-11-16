package com.example.test.mvvmsampleapp.feature.main.projectlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.test.mvvmsampleapp.feature.main.projectlist.model.Project

class ProjectListViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    val projectListObservable: MutableLiveData<List<Project>>
    var loading: MutableLiveData<Boolean>
    var mProjectListRepo = ProjectListRepo()

    init {

        // If any transformation is needed, this can be simply done by Transformations class ...
        projectListObservable = mProjectListRepo.getProjectList("Google")
        loading = mProjectListRepo.loading
    }
}
