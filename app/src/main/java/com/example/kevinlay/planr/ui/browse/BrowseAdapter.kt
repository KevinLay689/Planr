package com.example.kevinlay.planr.ui.browse

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.model.Trip

class BrowseAdapter(var trips: List<Trip>): RecyclerView.Adapter<BrowseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_browse, parent, false)
        return BrowseViewHolder(view)
    }

    override fun getItemCount(): Int {
       return trips.size
    }

    override fun onBindViewHolder(holder: BrowseViewHolder, position: Int) {
        holder.location.text = trips[position].tripName
        holder.price.text = trips[position].eventList?.map { event ->
            event.cost
        }?.sum().toString()
    }
}

class BrowseViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val location = view.findViewById<TextView>(R.id.locationLabel)
    val price = view.findViewById<TextView>(R.id.price)
    val rating = view.findViewById<TextView>(R.id.hostRating)
}