package com.example.kevinlay.planr.dagger

import android.arch.persistence.room.Room
import com.example.kevinlay.planr.PlanrApplication
import com.example.kevinlay.planr.repository.local.LocalDatabase
import com.example.kevinlay.planr.repository.local.EventDao

import dagger.Module
import dagger.Provides

/**
 * Created by kevinlay on 8/26/18.
 */
@Module
class AppModule(application: PlanrApplication) {

    private val database: LocalDatabase = Room.databaseBuilder(application, LocalDatabase::class.java, "db").fallbackToDestructiveMigration().build()

    @Provides
    fun provideLocalDatabase(): LocalDatabase {
        return database
    }

    @Provides
    fun provideUserDao(): EventDao {
        return database.eventDao()
    }
}