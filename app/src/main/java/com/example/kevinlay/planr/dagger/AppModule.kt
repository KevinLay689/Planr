package com.example.kevinlay.planr.dagger

import android.arch.persistence.room.Room
import com.example.kevinlay.planr.PlanrApplication
import com.example.kevinlay.planr.repository.PlanRepository
import com.example.kevinlay.planr.repository.local.LocalDatabase
import com.example.kevinlay.planr.repository.local.EventDao
import com.example.kevinlay.planr.repository.local.LocalDbSource
import com.example.kevinlay.planr.repository.remote.RemoteDbSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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

    @Provides
    fun provideRemoteDbSource(): RemoteDbSource {
        return RemoteDbSource(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance().reference)
    }

    @Provides
    fun provideLocalDbSource(eventDao: EventDao): LocalDbSource {
        return LocalDbSource(eventDao)
    }

    @Provides
    fun providePlanRepository(remoteDbSource: RemoteDbSource,
                              localDbSource: LocalDbSource): PlanRepository {
        return PlanRepository(remoteDbSource, localDbSource)
    }
}