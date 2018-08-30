package com.example.kevinlay.planr.ui.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.local.model.Trip
import com.example.kevinlay.planr.repository.local.model.User

class TripsAdapter(private val trips: List<Trip>): RecyclerView.Adapter<TripsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_my_trip, parent, false)
        return TripsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    override fun onBindViewHolder(holder: TripsViewHolder, position: Int) {
        holder.setData(trips[position].usersAttendingList!!)
    }
}

class TripsViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val primaryText = view.findViewById<TextView>(R.id.primary_text)
    val subText = view.findViewById<TextView>(R.id.sub_text)
    val imageView = view.findViewById<ImageView>(R.id.media_image)
    val button1 = view.findViewById<Button>(R.id.action_button_1)
    val button2 = view.findViewById<Button>(R.id.action_button_2)

    val recyclerviewUsers = view.findViewById<RecyclerView>(R.id.recyclerview_users)
    lateinit var layoutManager: LinearLayoutManager
    var adapter: UsersAdapter = UsersAdapter()

    fun setData(userList: List<User>) {
        Glide.with(view).load("https://scitechdaily.com/images/Less-Algae-Keeps-Tahoe-Blue.jpg").apply(RequestOptions.centerCropTransform()).into(imageView)
        layoutManager = LinearLayoutManager(view.context).apply { orientation = LinearLayoutManager.HORIZONTAL }
        adapter.users = userList
        recyclerviewUsers.layoutManager = layoutManager
        recyclerviewUsers.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}