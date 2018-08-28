package com.example.kevinlay.planr.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.kevinlay.planr.repository.local.model.Event
import com.example.kevinlay.planr.repository.local.model.Trip
import com.example.kevinlay.planr.repository.local.model.User

/**
 * Created by kevinlay on 8/26/18.
 */
@Database(entities = [(Event::class), (User::class), (Trip::class)], version = 2, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao
}