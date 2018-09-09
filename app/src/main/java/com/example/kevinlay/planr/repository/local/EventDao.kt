package com.example.kevinlay.planr.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.kevinlay.planr.repository.model.Event
import io.reactivex.Single

/**
 * Created by kevinlay on 8/26/18.
 */
@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE eventId = :eventId")
    fun getEvent(eventId: String): Single<Event>

    @Query("SELECT * FROM events WHERE tripId = :tripId")
    fun getEventByTrip(tripId: String): Single<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEvent(event: Event)
}