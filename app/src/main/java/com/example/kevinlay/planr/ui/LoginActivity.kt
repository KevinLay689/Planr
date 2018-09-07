package com.example.kevinlay.planr.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.kevinlay.planr.MainActivity
import com.example.kevinlay.planr.R

// TODO: Move register into here and just make them a single activity
class LoginActivity : AppCompatActivity() {

    private lateinit var buttonLogin: Button
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mSignUp: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        mEmail = findViewById(R.id.loginUsername)
        mPassword = findViewById(R.id.loginPassword)
        mProgressBar = findViewById(R.id.progressBar)
        mSignUp = findViewById(R.id.tvSignup)

        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener { signIn(mEmail.text.toString(), mPassword.text.toString()) }

        mSignUp.setOnClickListener {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(mAuth.currentUser)
    }

    private fun validateForm(): Boolean {
        var isValid = true
        val email = mEmail.text.toString()

        if (TextUtils.isEmpty(email)) {
            mEmail.error = RegisterActivity.REQUIRED_ERROR
            isValid = false
        } else {
            mEmail.error = null
        }

        val password = mPassword.text.toString()

        if (TextUtils.isEmpty(password)) {
            mPassword.error = RegisterActivity.REQUIRED_ERROR
            isValid = false
        } else {
            mPassword.error = null
        }

        return isValid
    }

    private fun updateUI(user: FirebaseUser?) {
        mProgressBar.visibility = View.INVISIBLE

        if (user != null) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        mProgressBar.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        updateUI(FirebaseAuth.getInstance().currentUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                    mProgressBar.visibility = View.INVISIBLE
                }
    }

    companion object {

        private const val TAG = "LoginActivity"
    }
}
