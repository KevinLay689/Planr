package com.example.kevinlay.planr.dagger

import com.example.kevinlay.planr.MainActivity
import com.example.kevinlay.planr.ui.browse.BrowseFragment
import com.example.kevinlay.planr.ui.login.LoginActivity
import com.example.kevinlay.planr.ui.login.RegisterActivity
import com.example.kevinlay.planr.ui.trip.CreateTripFragment
import com.example.kevinlay.planr.ui.home.HomeFragment
import dagger.Component

/**
 * Created by kevinlay on 8/26/18.
 */
@Component(modules = [(AppModule::class)])
abstract class AppComponent {
    abstract fun inject(homeFragment: HomeFragment)
    abstract fun inject(mainActivity: MainActivity)
    abstract fun inject(loginActivity: LoginActivity)
    abstract fun inject(registerActivity: RegisterActivity)
    abstract fun inject(createTripFragment: CreateTripFragment)
    abstract fun inject(browseFragment: BrowseFragment)
}