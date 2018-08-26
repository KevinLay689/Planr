package com.example.kevinlay.planr.repository.local.model

import com.example.kevinlay.planr.repository.local.model.Event

/**
 * Created by kevinlay on 8/26/18.
 */
data class Trip(val ownerId: String,
                val events: List<Event>)