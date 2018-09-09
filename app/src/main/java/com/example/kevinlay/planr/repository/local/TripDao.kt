package com.example.kevinlay.planr.repository.local

import android.arch.persistence.room.*
import com.example.kevinlay.planr.repository.model.Trip
import io.reactivex.Single

@Dao
interface TripDao {

    @Query("SELECT * FROM trips WHERE tripId = :tripId")
    fun getTrip(tripId: String): Single<Trip>

    @Query("SELECT * FROM trips WHERE ownerId = :ownerId")
    fun getTripsByOwner(ownerId: String): Single<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTrip(trip: Trip)
}