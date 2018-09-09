package com.example.kevinlay.planr.repository.remote

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

class RemoteDataSource(val firebaseAuth: FirebaseAuth,
                       val databaseReference: DatabaseReference) {

    fun getUserTrips(userId: String): Single<List<Trip>> {
        return Single.create { emitter ->
            databaseReference.child(RemoteDatabaseConstants.usersColumn)
                    .child(userId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(Throwable())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            emitter.onSuccess(snapshot.getValue(User::class.java)?.tripList
                                    ?: arrayListOf())
                        }
                    })
        }
    }

    fun insertUserTrip(userId: String, trip: Trip): Completable {
        return getUserTrips(userId)
                .flatMapCompletable { trips ->
                    val arrayListTrips = ArrayList(trips)
                    arrayListTrips.add(trip)
                    return@flatMapCompletable Completable.create { emitter ->
                        databaseReference.child(RemoteDatabaseConstants.usersColumn)
                                .child(userId)
                                .child(RemoteDatabaseConstants.tripListColumn)
                                .setValue(arrayListTrips) { _, _ -> emitter.onComplete() }
                    }
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