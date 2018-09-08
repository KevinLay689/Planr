package com.example.kevinlay.planr

import android.app.Application
import com.example.kevinlay.planr.dagger.AppComponent
import com.example.kevinlay.planr.dagger.AppModule
import com.example.kevinlay.planr.dagger.DaggerAppComponent
import com.google.firebase.FirebaseApp

/**
 * Created by kevinlay on 8/26/18.
 */
class PlanrApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}