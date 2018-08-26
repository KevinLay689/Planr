package com.example.kevinlay.planr.repository.local.model

/**
 * Created by kevinlay on 8/26/18.
 */
data class User(val username: String,
                val userId: String,
                val trips: List<Trip>)