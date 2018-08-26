package com.example.kevinlay.planr.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.kevinlay.planr.repository.local.model.User

/**
 * Created by kevinlay on 8/26/18.
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}