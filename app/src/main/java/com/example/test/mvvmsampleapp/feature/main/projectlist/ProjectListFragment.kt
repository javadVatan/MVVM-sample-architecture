package com.example.test.mvvmsampleapp.feature.main.projectlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test.mvvmsampleapp.R
import com.example.test.mvvmsampleapp.feature.main.projectlist.adapter.ProjectAdapter
import com.example.test.mvvmsampleapp.feature.main.projectlist.adapter.callback.ProjectClickCallback
import com.example.test.mvvmsampleapp.feature.main.projectlist.model.Project
import com.p_gum.p_gum.core.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_project_list.*


class ProjectListFragment : BaseFragment<ProjectListViewModel>() {
    private var projectAdapter: ProjectAdapter? = null

    private val projectClickCallback = ProjectClickCallback {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            //                ((MainActivity) getActivity()).show(project);
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_project_list, container, false)

        initializeViewModel()

        return rootView
    }

    private fun initializeViewModel() {
       mViewModel = ViewModelProviders.of(this).get(ProjectListViewModel::class.java)

        observeViewModel(mViewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun observeViewModel(viewModel: ProjectListViewModel) {
        // Update the list when the data changes

        viewModel.mLoading.observe(this, Observer<Boolean> {
            loading_projects?.visibility = if (it!!) View.VISIBLE else View.GONE
        })

        viewModel.projectListObservable.observe(this, object : Observer<List<Project>> {
            override fun onChanged(t: List<Project>?) {
                projectAdapter?.setProjectList(t!!)
            }
        })
    }

    private fun initList() {
        projectAdapter = ProjectAdapter(projectClickCallback)

        project_list?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = projectAdapter
        }
    }

    companion object {
        val TAG = "ProjectListFragment"
    }
}
