package com.example.kevinlay.planr.repository

import com.example.kevinlay.planr.repository.local.LocalDataSource
import com.example.kevinlay.planr.repository.model.Trip
import com.example.kevinlay.planr.repository.remote.RemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

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

    fun signInAndSaveUser(email: String, password: String): Single<Boolean> {
        return remoteDataSource.signIn(email, password)
                .flatMap { firebaseUser ->
                    remoteDataSource.getUser(firebaseUser.uid)
                }.flatMapCompletable { userDetails ->
                    localDataSource.saveUser(userDetails)
                }.andThen(Single.just(true))
    }


    fun saveTrip(trip: Trip): Completable {
        return localDataSource.saveTrip(trip)
                .andThen(remoteDataSource.insertUserTrip(trip))
    }
}