package com.example.kevinlay.planr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnCompleteListener
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*


class LoginActivity : AppCompatActivity() {

    private var buttonLogin: Button? = null
    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var mProgressBar: ProgressBar? = null
    private var mSignUp: TextView? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        mEmail = findViewById(R.id.loginUsername)
        mPassword = findViewById(R.id.loginPassword)
        mProgressBar = findViewById(R.id.progressBar)
        mSignUp = findViewById(R.id.tvSignup)

        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin!!.setOnClickListener { signIn(mEmail!!.text.toString(), mPassword!!.text.toString()) }

        mSignUp!!.setOnClickListener {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = mEmail!!.text.toString()

        if (TextUtils.isEmpty(email)) {
            mEmail!!.error = "Required."
            valid = false
        } else {
            mEmail!!.error = null
        }

        val password = mPassword!!.text.toString()

        if (TextUtils.isEmpty(password)) {
            mPassword!!.error = "Required."
            valid = false
        } else {
            mPassword!!.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        mProgressBar!!.visibility = View.INVISIBLE

        if (user != null) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        mProgressBar!!.visibility = View.VISIBLE

        // [START sign_in_with_email]
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth!!.currentUser
                        updateUI(user)
                        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
                        //Toast.makeText(getApplicationContext(), "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                        val uID = currentFirebaseUser!!.uid
                        updateUI(currentFirebaseUser)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // [START_EXCLUDE]
                    if (!task.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Sign in failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                    mProgressBar!!.visibility = View.INVISIBLE
                    // [END_EXCLUDE]
                }
        // [END sign_in_with_email]
    }

    override fun onStart() {
        super.onStart()
        updateUI(mAuth!!.currentUser)
    }

    companion object {

        private val TAG = "LoginActivity"
    }
}
