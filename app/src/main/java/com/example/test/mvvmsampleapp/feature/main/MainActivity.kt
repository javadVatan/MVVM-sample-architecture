package com.example.test.mvvmsampleapp.feature.main

import android.os.Bundle

import com.example.test.mvvmsampleapp.R
import com.example.test.mvvmsampleapp.core.base.BaseActivity


import com.example.test.mvvmsampleapp.feature.main.projectlist.ProjectListFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add project list fragment if this is first creation
        if (savedInstanceState == null) {
            val fragment = ProjectListFragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, ProjectListFragment.TAG).commit()
        }
    }


}
