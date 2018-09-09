package com.example.kevinlay.planr.repository

import com.example.kevinlay.planr.repository.local.LocalDataSource
import com.example.kevinlay.planr.repository.remote.RemoteDataSource
import io.reactivex.Completable

class PlanRepository(val remoteDataSource: RemoteDataSource,
                     val localDataSource: LocalDataSource) {

    fun saveUser(firstName: String,
                 lastName: String,
                 location: String,
                 email: String,
                 password: String): Completable {

        return remoteDataSource.createAccountAndInsertToRemoteDatabase(firstName, lastName, location, email, password)
                .flatMapCompletable { user ->
                    localDataSource.saveUser(user)
                }
    }
}