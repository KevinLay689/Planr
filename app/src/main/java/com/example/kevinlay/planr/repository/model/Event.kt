package com.example.kevinlay.planr.repository.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "events")
data class Event(@PrimaryKey var eventId: String = "",
                 var tripId: String = "",
                 var eventName: String = "",
                 var cost: Float = 0.0f,
                 var startTime: String = "",
                 var endTime: String = "",
                 var details: String = "")