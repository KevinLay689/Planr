package com.example.kevinlay.planr.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kevinlay.planr.R

/**
 * Created by kevinlay on 8/26/18.
 */
class HomeFragment: Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupActionBar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setupActionBar() {
//        (activity as AppCompatActivity).supportActionBar?.setTitle(
//                if (arguments != null && arguments.get(ARGUMENT_EDIT_TASK_ID) != null)
//                    R.string.edit_task
//                else
//                    R.string.add_task
//        )
    }
}