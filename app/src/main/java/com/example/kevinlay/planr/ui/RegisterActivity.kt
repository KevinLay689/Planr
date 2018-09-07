package com.example.kevinlay.planr.ui

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
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.local.model.User2
import com.example.kevinlay.planr.util.RemoteDatabaseConstants

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var mEmailField: EditText
    private lateinit var mPasswordField: EditText
    private lateinit var mFirstName: EditText
    private lateinit var mLastName: EditText
    private lateinit var mHometown: EditText
    private lateinit var mCreateAccount: Button
    private lateinit var mProgressBar: ProgressBar

    private lateinit var mAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        FirebaseApp.initializeApp(this)
        databaseReference = FirebaseDatabase.getInstance().reference

        mFirstName = findViewById(R.id.loginFirstName)
        mLastName = findViewById(R.id.loginLastName)
        mHometown = findViewById(R.id.loginLocation)
        mEmailField = findViewById(R.id.loginEmail)
        mPasswordField = findViewById(R.id.loginPassword)
        mProgressBar = findViewById(R.id.progressBar)

        mCreateAccount = findViewById(R.id.createAccount)

        mCreateAccount.setOnClickListener {
            createAccount(mFirstName.text.toString(),
                    mLastName.text.toString(),
                    mHometown.text.toString(),
                    mEmailField.text.toString(),
                    mPasswordField.text.toString())
        }
        mAuth = FirebaseAuth.getInstance()
    }

    private fun createAccount(firstName: String, lastName: String,
                              homeTown: String, email: String, password: String) {

        if (!validateForm()) {
            return
        }

        mProgressBar.visibility = View.VISIBLE

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        insertUserIntoDatabase(firstName, lastName, homeTown, email, mAuth.currentUser!!.uid)
                        goToDashboard()
                    } else {
                        Toast.makeText(this@RegisterActivity,
                                task.exception.toString(),
                                Toast.LENGTH_LONG).show()
                    }

                    mProgressBar.visibility = View.INVISIBLE
                }
    }

    private fun insertUserIntoDatabase(firstName: String,
                                       lastName: String,
                                       homeTown: String,
                                       email: String,
                                       id: String) {
        val user = User2(firstName, lastName, homeTown, "", email, id)

        databaseReference.child(RemoteDatabaseConstants.usersColumn)
                .child(id)
                .setValue(user)
    }


    private fun goToDashboard() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val firstName = mFirstName.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            mFirstName.error = REQUIRED_ERROR
            isValid = false
        } else {
            mFirstName.error = null
        }

        val lastName = mLastName.text.toString()
        if (TextUtils.isEmpty(lastName)) {
            mLastName.error = REQUIRED_ERROR
            isValid = false
        } else {
            mLastName.error = null
        }

        val hometown = mHometown.text.toString()
        if (TextUtils.isEmpty(hometown)) {
            mHometown.error = REQUIRED_ERROR
            isValid = false
        } else {
            mHometown.error = null
        }

        val email = mEmailField.text.toString()
        if (TextUtils.isEmpty(email)) {
            mEmailField.error = REQUIRED_ERROR
            isValid = false
        } else {
            mEmailField.error = null
        }

        val password = mPasswordField.text.toString()
        if (TextUtils.isEmpty(password)) {
            mPasswordField.error = REQUIRED_ERROR
            isValid = false
        } else {
            mPasswordField.error = null
        }

        return isValid
    }

    companion object {

        private const val TAG = "EmailPassword"
        const val REQUIRED_ERROR = "Required."
    }
}