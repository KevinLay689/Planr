package com.example.kevinlay.planr.repository.remote

import android.util.Log
import com.example.kevinlay.planr.repository.model.Event
import com.example.kevinlay.planr.repository.model.Trip
import com.example.kevinlay.planr.repository.model.User
import com.example.kevinlay.planr.util.RemoteDatabaseConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Single
import kotlin.collections.ArrayList

class RemoteDataSource(val firebaseAuth: FirebaseAuth,
                       val databaseReference: DatabaseReference) {

    fun getUser(userId: String): Single<User> {
        return Single.create { emitter ->
            databaseReference.child(RemoteDatabaseConstants.usersColumn)
                    .child(userId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(Throwable())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            emitter.onSuccess(snapshot.getValue(User::class.java) ?: User())
                        }
                    })
        }
    }

    fun getUserTrips(userId: String): Single<List<Trip>> {
        return Single.create { emitter ->
            databaseReference.child(RemoteDatabaseConstants.usersColumn)
                    .child(userId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(Throwable())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            Log.i("Trips list" , snapshot.getValue(User::class.java)?.toString())

                            emitter.onSuccess(snapshot.getValue(User::class.java)?.trips ?: arrayListOf())
                        }
                    })
        }
    }

    fun insertUserTrip(trip: Trip): Completable {
        return insertTrip(trip).flatMapCompletable { key ->
            getUserTrips(firebaseAuth.uid!!)
                    .flatMapCompletable { trips ->
                        val arrayListTrips = ArrayList(trips)
                        trip.tripId = key
                        arrayListTrips.add(trip)
                        return@flatMapCompletable Completable.create { emitter ->
                            databaseReference.child(RemoteDatabaseConstants.usersColumn)
                                    .child(firebaseAuth.uid!!)
                                    .child(RemoteDatabaseConstants.tripListColumn)
                                    .setValue(arrayListTrips) { _, _ -> emitter.onComplete() }
                        }
                    }
        }
    }

    fun insertTrip(trip: Trip): Single<String> {
        return Single.create { emitter ->
            databaseReference.child(RemoteDatabaseConstants.tripListColumn)
                    .child(trip.tripId)
                    .setValue(trip) { _, ref -> emitter.onSuccess(ref.key ?: "") }
        }
    }

    fun getTripEvents(tripId: String): Single<List<Event>> {
        return Single.create { emitter ->
            databaseReference.child(RemoteDatabaseConstants.tripListColumn)
                    .child(tripId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(Throwable())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            Log.i("Trips Events" , snapshot.getValue(Trip::class.java)?.toString())
                            emitter.onSuccess(snapshot.getValue(Trip::class.java)?.eventList ?: arrayListOf())
                        }
                    })
        }
    }

    fun getTripsByArea(location: String): Single<List<Trip>> {
        val trips: ArrayList<Trip> = ArrayList()

        return Single.create { emitter ->
            databaseReference.child(RemoteDatabaseConstants.tripListColumn)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(Throwable())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            Log.i("Trips by area" , snapshot.getValue(Trip::class.java)?.toString())

                            for (snap: DataSnapshot in snapshot.children) {
                                val tempTrip = snap.getValue(Trip::class.java)
                                if (tempTrip != null
                                        && !tempTrip.isPrivate
                                        && tempTrip.location == location) {
                                    trips.add(tempTrip)
                                }
                            }
                            emitter.onSuccess(trips)
                        }
                    })
        }
    }

    fun insertEvent(event: Event): Completable {
        return Completable.create { emitter ->
            databaseReference.child(RemoteDatabaseConstants.eventColumn)
                    .child(event.eventId)
                    .setValue(event) { _, _ -> emitter.onComplete() }
        }
    }

    fun signIn(email: String, password: String): Single<FirebaseUser> {
        return Single.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onSuccess(firebaseAuth.currentUser!!)
                        } else {
                            emitter.onError(Throwable("Authentication failed"))
                        }
                    }
        }
    }

    fun createAccountAndInsertToRemoteDatabase(firstName: String,
                                               lastName: String,
                                               location: String,
                                               email: String,
                                               password: String): Single<User> {
        return createAccount(email, password)
                .andThen(insertUserIntoDatabase(firstName, lastName, location, email))
    }

    private fun createAccount(email: String,
                      password: String): Completable {

        return Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(Throwable(task.exception?.toString()))
                        }
                    }
        }
    }

    private fun insertUserIntoDatabase(firstName: String,
                                       lastName: String,
                                       location: String,
                                       email: String) : Single<User> {
        return Single.create { emitter ->
            val user = User(firebaseAuth.currentUser?.uid!!, firstName, lastName, location, email)

            databaseReference.child(RemoteDatabaseConstants.usersColumn)
                    .child(firebaseAuth.currentUser?.uid!!)
                    .setValue(user) { _, _ -> emitter.onSuccess(user) }
        }
    }
}