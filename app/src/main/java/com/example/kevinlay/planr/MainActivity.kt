package com.example.kevinlay.planr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.kevinlay.planr.repository.PlanRepository
import com.example.kevinlay.planr.ui.home.HomeFragment
import com.example.kevinlay.planr.ui.LoginActivity
import com.example.kevinlay.planr.util.into
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    @Inject lateinit var planRepository: PlanRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as PlanrApplication).appComponent.inject(this)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        setupToolbar()

        supportFragmentManager.beginTransaction().add(R.id.frame, HomeFragment(), homeFragmentTag).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setCheckedItem(R.id.nav_home)

        navigationView.setNavigationItemSelectedListener { menuItem ->

            menuItem.isChecked = true
            mDrawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.nav_home -> {
                    if (supportFragmentManager.findFragmentByTag(homeFragmentTag) != null) {
                        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment(), homeFragmentTag).commit()
                    }
                }
                R.id.nav_browse -> {
                    Toast.makeText(this, "Browse clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                    val disposable = CompositeDisposable()
                    planRepository.localDataSource.getUser(planRepository.remoteDataSource.firebaseAuth.uid!!)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { user ->
                                Log.i(TAG, "userId is: ${user.userId} email is ${user.email}")
                            }.into(disposable)

                }

                R.id.nav_signout -> {
                    logout()
                }
            }
            true
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(baseContext, LoginActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val homeFragmentTag: String = "homeFragment"
        const val TAG: String = "HomeActivity"
    }
}
