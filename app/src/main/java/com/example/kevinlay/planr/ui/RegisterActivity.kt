package com.example.kevinlay.planr.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.kevinlay.planr.PlanrApplication
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.PlanRepository
import com.example.kevinlay.planr.repository.local.LocalDataSource
import com.example.kevinlay.planr.repository.model.User
import com.example.kevinlay.planr.repository.remote.RemoteDataSource
import com.example.kevinlay.planr.util.into
import io.reactivex.Completable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    private lateinit var mEmailField: EditText
    private lateinit var mPasswordField: EditText
    private lateinit var mFirstName: EditText
    private lateinit var mLastName: EditText
    private lateinit var mHometown: EditText
    private lateinit var mCreateAccount: Button
    private lateinit var mProgressBar: ProgressBar

    @Inject lateinit var planRepository: PlanRepository
    @Inject lateinit var localDataSource: LocalDataSource

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        (application as PlanrApplication).appComponent.inject(this)

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
    }

    private fun createAccount(firstName: String, lastName: String,
                              location: String, email: String, password: String) {

        if (!validateForm()) {
            return
        }

        mProgressBar.visibility = View.VISIBLE

        planRepository.saveUser(firstName, lastName, location, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    goToDashboard()
                    mProgressBar.visibility = View.INVISIBLE
                }, { error ->
                    showToast("Repo error: " + error.message!!)
                    mProgressBar.visibility = View.INVISIBLE
                })
                .into(compositeDisposable)

    }

    private fun showToast(msg: String) {
        Toast.makeText(this@RegisterActivity,
                msg,
                Toast.LENGTH_LONG).show()
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