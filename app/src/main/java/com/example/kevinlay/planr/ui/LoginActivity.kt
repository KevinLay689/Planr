package com.example.kevinlay.planr.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.kevinlay.planr.MainActivity
import com.example.kevinlay.planr.PlanrApplication
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.PlanRepository
import com.example.kevinlay.planr.repository.remote.RemoteDataSource
import com.example.kevinlay.planr.util.into
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// TODO: Move register into here and just make them a single activity
class LoginActivity : AppCompatActivity() {

    private lateinit var buttonLogin: Button
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mSignUp: TextView

    @Inject lateinit var planRepository: PlanRepository

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (application as PlanrApplication).appComponent.inject(this)

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
        if (planRepository.remoteDataSource.firebaseAuth.currentUser == null) {
            updateUI(false)
        } else {
            updateUI(true)
        }
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

    private fun updateUI(isLoginSuccessfull: Boolean) {
        mProgressBar.visibility = View.INVISIBLE

        if (isLoginSuccessfull) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        mProgressBar.visibility = View.VISIBLE

        planRepository.signInAndSaveUser(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ _ ->
                    updateUI(true)
                    mProgressBar.visibility = View.INVISIBLE
                }, { error ->
                    showToast(error.message!!)
                    mProgressBar.visibility = View.INVISIBLE
                })
                .into(compositeDisposable)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@LoginActivity,
                msg,
                Toast.LENGTH_LONG).show()
    }

    companion object {

        private const val TAG = "LoginActivity"
    }
}
