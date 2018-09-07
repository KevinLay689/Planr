package com.example.kevinlay.planr.ui.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.model.Trip
import com.example.kevinlay.planr.repository.model.User

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
        val user1 = User("","","","", "", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user2 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user3 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user4 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user5 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user6 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user7 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user8 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//
//        val user9 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user10 = User("","","","",BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user11 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user12 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user13 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user14 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user15 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//        val user16 = User("","","","", BitmapFactory.decodeResource(resources, R.drawable.ic_menu), ArrayList())
//
//        val trip = Trip("1", "1", ArrayList(), arrayListOf(user1, user2, user3, user4,
//                user5, user6, user7, user8,
//                user9, user10, user11, user12,
//                user13, user14, user15, user16))

//        val trip2 = Trip("2", "1", ArrayList(), arrayListOf(user1, user2, user3, user4,
//                user5, user6, user7, user8,
//                user9, user10, user11, user12,
//                user13, user14, user15, user16))
//
        val trip3 = Trip("3", "1", ArrayList(), arrayListOf(user1))

        return arrayListOf(trip3)
    }
}