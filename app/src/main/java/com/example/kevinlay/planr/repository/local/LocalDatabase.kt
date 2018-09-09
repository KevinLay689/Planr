package com.example.kevinlay.planr.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.kevinlay.planr.repository.model.Event
import com.example.kevinlay.planr.repository.model.Trip
import com.example.kevinlay.planr.repository.model.User

/**
 * Created by kevinlay on 8/26/18.
 */
@Database(entities = [(Event::class), (User::class), (Trip::class)], version = 2, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun tripDao(): TripDao
}