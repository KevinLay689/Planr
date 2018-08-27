package com.example.kevinlay.planr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.kevinlay.planr.ui.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        // Set up the toolbar.
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setCheckedItem(R.id.nav_home)

        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.frame, HomeFragment(), "homeFragment").commit()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            when (menuItem.itemId) {
                R.id.nav_home -> {

                }
                R.id.nav_browse -> {
                    Toast.makeText(this, "browse clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

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
}
