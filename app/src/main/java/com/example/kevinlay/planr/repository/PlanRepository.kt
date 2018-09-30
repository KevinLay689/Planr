package com.example.kevinlay.planr.repository

import com.example.kevinlay.planr.repository.local.LocalDataSource
import com.example.kevinlay.planr.repository.model.Event
import com.example.kevinlay.planr.repository.model.Trip
import com.example.kevinlay.planr.repository.model.User
import com.example.kevinlay.planr.repository.remote.RemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Needs a ton of work, logic is flipped. Local data source should be single source
 * of truth. Figure out how to save lists in room
 *
 */
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

    fun getUser(): Single<User> {
        return localDataSource.getUser(remoteDataSource.firebaseAuth.uid!!)
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

    fun saveEvent(event: Event): Completable {
        return localDataSource.saveEvent(event)
                .andThen(remoteDataSource.insertEvent(event))
    }

    /**
     * TODO: Need to properly set up local room db to be the cache
     */
    fun getTrips(location: String): Single<List<Trip>> {
        return remoteDataSource.getTripsByArea(location)
    }
}