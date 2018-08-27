package com.example.kevinlay.planr

import android.app.Application
import com.example.kevinlay.planr.dagger.AppComponent

/**
 * Created by kevinlay on 8/26/18.
 */
class PlanrApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
    }
}