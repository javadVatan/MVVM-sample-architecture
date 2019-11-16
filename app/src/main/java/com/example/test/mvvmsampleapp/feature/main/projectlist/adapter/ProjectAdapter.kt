package com.example.test.mvvmsampleapp.feature.main.projectlist.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test.mvvmsampleapp.R
import com.example.test.mvvmsampleapp.feature.main.projectlist.adapter.callback.ProjectClickCallback
import com.example.test.mvvmsampleapp.feature.main.projectlist.model.Project
import kotlinx.android.synthetic.main.project_list_item.view.*

class ProjectAdapter(private val projectClickCallback: ProjectClickCallback?) : RecyclerView.Adapter<ProjectAdapter.ViewHolders>() {

    internal var projectList: List<Project>? = null

    fun setProjectList(projectList: List<Project>) {
        if (this.projectList == null) {
            this.projectList = projectList
            notifyItemRangeInserted(0, projectList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@ProjectAdapter.projectList!!.size
                }

                override fun getNewListSize(): Int {
                    return projectList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@ProjectAdapter.projectList!![oldItemPosition].id == projectList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val project = projectList[newItemPosition]
                    val old = projectList[oldItemPosition]
                    return project.id == old.id && project.git_url == old.git_url
                }
            })
            this.projectList = projectList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {

        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.project_list_item, null)

        return ViewHolders(layoutView)
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.itemView.name.text = projectList?.get(position)?.full_name
    }

    override fun getItemCount(): Int {
        return if (projectList == null) 0 else projectList!!.size
    }

    inner class ViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView)

}
