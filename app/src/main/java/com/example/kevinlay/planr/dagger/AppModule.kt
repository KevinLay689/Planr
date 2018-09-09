package com.example.kevinlay.planr.dagger

import android.arch.persistence.room.Room
import com.example.kevinlay.planr.PlanrApplication
import com.example.kevinlay.planr.repository.PlanRepository
import com.example.kevinlay.planr.repository.local.*
import com.example.kevinlay.planr.repository.remote.RemoteDataSource
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
    fun provideUserDao(): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideEventDao(): EventDao {
        return database.eventDao()
    }

    @Provides
    fun provideTripDao(): TripDao {
        return database.tripDao()
    }

    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance().reference)
    }

    @Provides
    fun provideLocalDataSourcee(userDao: UserDao,
                                eventDao: EventDao,
                                tripDao: TripDao): LocalDataSource {
        return LocalDataSource(userDao, eventDao, tripDao)
    }

    @Provides
    fun providePlanRepository(remoteDbSource: RemoteDataSource,
                              localDbSource: LocalDataSource): PlanRepository {
        return PlanRepository(remoteDbSource, localDbSource)
    }
}