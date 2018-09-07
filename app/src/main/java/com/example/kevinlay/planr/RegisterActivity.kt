package com.example.kevinlay.planr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.kevinlay.planr.repository.local.model.User2

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null
    private var mFirstName: EditText? = null
    private var mLastName: EditText? = null
    private var mHometown: EditText? = null
    private var mMajor: EditText? = null
    private var mCreateAccount: Button? = null
    private var mProgressBar: ProgressBar? = null

    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null
    // [END declare_auth]

    private var databaseReference: DatabaseReference? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        FirebaseApp.initializeApp(this)

        //Database Reference
        databaseReference = FirebaseDatabase.getInstance().reference

        // Views
        mFirstName = findViewById(R.id.loginFirstName)
        mLastName = findViewById(R.id.loginLastName)
        mHometown = findViewById(R.id.loginHometown)
        mMajor = findViewById(R.id.loginMajor)
        mEmailField = findViewById(R.id.loginUsername)
        mPasswordField = findViewById(R.id.loginPassword)
        mProgressBar = findViewById(R.id.progressBar3)

        mCreateAccount = findViewById(R.id.createAccount)

        mCreateAccount!!.setOnClickListener {
            createAccount(mFirstName!!.text.toString(),
                    mLastName!!.text.toString(),
                    mHometown!!.text.toString(),
                    mMajor!!.text.toString(),
                    mEmailField!!.text.toString(),
                    mPasswordField!!.text.toString())
        }
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
    }
    // [END on_start_check_user]

    private fun createAccount(firstName: String, lastName: String,
                              homeTown: String, major: String, email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        mProgressBar!!.visibility = View.VISIBLE

        if (mAuth == null) {
            Log.e(TAG, "createAccount: Error mauth is null")
        }
        // [START create_user_with_email]
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = mAuth!!.currentUser
                        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
                        //Toast.makeText(getApplicationContext(), "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                        val uID = currentFirebaseUser!!.uid
                        insertUserIntoDatabase(firstName, lastName, homeTown, major, email, uID)
                        goToDashboard()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@RegisterActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }

                    mProgressBar!!.visibility = View.INVISIBLE
                }
        // [END create_user_with_email]
    }

    private fun insertUserIntoDatabase(firstName: String, lastName: String, homeTown: String, major: String, email: String, id: String) {
        val user = User2(firstName, lastName, homeTown, major, email, id)
        databaseReference!!.child("users").child(id).setValue(user)
    }


    private fun goToDashboard() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun validateForm(): Boolean {
        var valid = true

        val firstName = mFirstName!!.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            mFirstName!!.error = "Required."
            valid = false
        } else {
            mFirstName!!.error = null
        }

        val lastName = mLastName!!.text.toString()
        if (TextUtils.isEmpty(lastName)) {
            mLastName!!.error = "Required."
            valid = false
        } else {
            mLastName!!.error = null
        }

        val hometown = mHometown!!.text.toString()
        if (TextUtils.isEmpty(hometown)) {
            mHometown!!.error = "Required."
            valid = false
        } else {
            mHometown!!.error = null
        }

        val email = mEmailField!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            mEmailField!!.error = "Required."
            valid = false
        } else {
            mEmailField!!.error = null
        }

        val password = mPasswordField!!.text.toString()
        if (TextUtils.isEmpty(password)) {
            mPasswordField!!.error = "Required."
            valid = false
        } else {
            mPasswordField!!.error = null
        }

        return valid
    }

    companion object {

        private val TAG = "EmailPassword"
    }
}