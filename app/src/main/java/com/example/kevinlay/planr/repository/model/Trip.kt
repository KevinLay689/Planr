package com.example.kevinlay.planr.repository.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "trips")
data class Trip(@PrimaryKey var tripId: String = "",
                var tripName: String = "",
                var isPrivate: Boolean = false,
                var ownerId: String = "",
                var startDay: String = "",
                var endDay: String = "",
                var location: String = "",
                @Ignore var eventList: List<Event>? = null,
                @Ignore var usersAttendingList: List<User>? = null)