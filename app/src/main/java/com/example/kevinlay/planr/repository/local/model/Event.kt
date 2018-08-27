package com.example.kevinlay.planr.repository.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by kevinlay on 8/26/18.
 */
@Entity(tableName = "events")
data class Event(@PrimaryKey val eventId: String,
                 val eventName: String,
                 val isActive: Boolean,
                 val cost: Float,
                 val details: String)