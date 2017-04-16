package com.github.raenar4k.reactweather

import android.app.Application
import timber.log.Timber

class ReactApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.RELEASE) {
            Timber.plant(Timber.DebugTree())
        }
    }
}