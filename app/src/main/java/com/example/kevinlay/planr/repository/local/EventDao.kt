package com.example.kevinlay.planr.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.kevinlay.planr.repository.local.model.Event
import io.reactivex.Single

/**
 * Created by kevinlay on 8/26/18.
 */
@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getTrips(): Event
}