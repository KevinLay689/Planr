package com.example.kevinlay.planr.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.local.model.Trip
import com.example.kevinlay.planr.repository.local.model.User
import com.example.kevinlay.planr.ui.home.TripsAdapter

/**
 * Created by kevinlay on 8/26/18.
 */
class HomeFragment: Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        val tripsRecyclerView = view.findViewById<RecyclerView>(R.id.trips_recyclerview)
        val adapter = TripsAdapter(createData())

        tripsRecyclerView.adapter = adapter
        tripsRecyclerView.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true)

        return view
    }

    fun createData(): List<Trip> {
        val user1 = User("1", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user2 = User("2", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user3 = User("3", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user4 = User("4", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user5 = User("5", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user6 = User("6", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user7 = User("7", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user8 = User("8", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())

        val user9 = User("9", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user10 = User("10", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user11 = User("11", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user12 = User("12", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user13 = User("13", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user14 = User("14", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user15 = User("15", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
        val user16 = User("16", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())

        val trip = Trip("1", "1", ArrayList(), arrayListOf(user1, user2, user3, user4,
                user5, user6, user7, user8,
                user9, user10, user11, user12,
                user13, user14, user15, user16))

//        val trip2 = Trip("2", "1", ArrayList(), arrayListOf(user1, user2, user3, user4,
//                user5, user6, user7, user8,
//                user9, user10, user11, user12,
//                user13, user14, user15, user16))
//
//        val trip3 = Trip("3", "1", ArrayList(), arrayListOf(user1, user2, user3, user4,
//                user5, user6, user7, user8,
//                user9, user10, user11, user12,
//                user13, user14, user15, user16))

        return arrayListOf(trip)
    }
}