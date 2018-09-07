package com.example.kevinlay.planr.repository.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.graphics.Bitmap

@Entity(tableName = "users")
data class User(@PrimaryKey var userId: String = "",
                var firstName: String = "",
                var lastName: String = "",
                var location: String = "",
                var email: String = "",
                @Ignore var userImage: Bitmap? = null,
                @Ignore var tripList: List<Trip> = ArrayList())