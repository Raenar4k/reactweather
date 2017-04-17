package com.github.raenar4k.reactweather

import com.github.raenar4k.reactweather.network.NetworkModule
import com.github.raenar4k.reactweather.network.UserService
import dagger.Component

@ApplicationScope
@Component(modules = arrayOf(NetworkModule::class))
interface ApplicationComponent {
    fun userService(): UserService
}