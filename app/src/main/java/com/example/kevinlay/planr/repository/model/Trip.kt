package com.example.kevinlay.planr.repository.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "trips")
data class Trip(@PrimaryKey var tripId: String = "",
                var ownerId: String = "",
                @Ignore var eventList: List<Event>? = null,
                @Ignore var usersAttendingList: List<User>? = null)