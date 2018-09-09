package com.example.kevinlay.planr.repository.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "events")
data class Event(@PrimaryKey val eventId: String,
                 val tripId: String,
                 val eventName: String,
                 val cost: Float,
                 val details: String)