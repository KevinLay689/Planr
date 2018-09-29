package com.example.kevinlay.planr.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kevinlay.planr.MainActivity
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.ui.trip.TripsAdapter

/**
 * Created by kevinlay on 8/26/18.
 */
class HomeFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        val tripsRecyclerView = view.findViewById<RecyclerView>(R.id.trips_recyclerview)
        val adapter = TripsAdapter(arrayListOf())

        tripsRecyclerView.adapter = adapter
        tripsRecyclerView.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true)

        val mainActivity = activity
        if (mainActivity is MainActivity) {
            mainActivity.showFab()
        }

        return view
    }
}