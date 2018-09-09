package com.example.kevinlay.planr.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.kevinlay.planr.repository.model.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUser(userId: String): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)
}