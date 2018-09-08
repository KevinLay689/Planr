package com.example.kevinlay.planr.dagger

import com.example.kevinlay.planr.ui.LoginActivity
import com.example.kevinlay.planr.ui.RegisterActivity
import com.example.kevinlay.planr.ui.home.HomeFragment
import dagger.Component

/**
 * Created by kevinlay on 8/26/18.
 */
@Component(modules = [(AppModule::class)])
abstract class AppComponent {
    abstract fun inject(homeFragment: HomeFragment)
    abstract fun inject(loginActivity: LoginActivity)
    abstract fun inject(registerActivity: RegisterActivity)
}