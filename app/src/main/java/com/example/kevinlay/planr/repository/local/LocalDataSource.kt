package com.example.kevinlay.planr.repository.local

import com.example.kevinlay.planr.repository.model.Event
import com.example.kevinlay.planr.repository.model.Trip
import com.example.kevinlay.planr.repository.model.User
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalDataSource(private val userDao: UserDao,
                      private val eventDao: EventDao,
                      private val tripDao: TripDao) {

    fun saveUser(user: User): Completable {
        return Completable.fromAction { userDao.saveUser(user) }
                .subscribeOn(Schedulers.io())
    }

    fun getUser(userId: String): Single<User> {
        return userDao.getUser(userId)
    }

    fun saveTrip(trip: Trip): Completable {
        return Completable.fromAction { tripDao.saveTrip(trip) }
                .subscribeOn(Schedulers.io())
    }

    fun getTrips(ownerId: String): Single<List<Trip>> {
        return tripDao.getTripsByOwner(ownerId)
    }

    fun saveEvent(event: Event): Completable {
        return Completable.fromAction { eventDao.saveEvent(event) }
                .subscribeOn(Schedulers.io())
    }

    fun getEventByTrip(tripId: String): Single<List<Event>> {
        return eventDao.getEventByTrip(tripId)
    }
}