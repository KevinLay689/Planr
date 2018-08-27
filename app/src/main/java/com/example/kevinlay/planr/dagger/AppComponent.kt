package com.example.kevinlay.planr.dagger

import com.example.kevinlay.planr.ui.HomeFragment
import dagger.Component

/**
 * Created by kevinlay on 8/26/18.
 */
@Component(modules = arrayOf(AppModule::class))
abstract class AppComponent {
    abstract fun inject(homeFragment: HomeFragment)
}