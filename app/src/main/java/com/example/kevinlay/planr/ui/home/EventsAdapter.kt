package com.example.kevinlay.planr.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.model.Event

class EventsAdapter(var events: List<Event>): RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_event, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.eventName.text = events[position].eventName
        holder.eventCost.text = "$" + events[position].cost
//        holder.eventStartTime.text = events[position].cost.toString()
    }

    fun updateList(events: List<Event>) {
        this.events = events
    }
}

class EventViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val eventName = view.findViewById<TextView>(R.id.eventName)
    val eventCost = view.findViewById<TextView>(R.id.eventCost)
    val eventStartTime = view.findViewById<TextView>(R.id.startTime)
}