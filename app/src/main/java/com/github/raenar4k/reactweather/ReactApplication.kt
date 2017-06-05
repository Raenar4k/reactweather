package com.github.raenar4k.reactweather

import android.app.Application
import android.content.Context
import timber.log.Timber

fun getApplication(context: Context): ReactApplication {
  return context.applicationContext as ReactApplication
}

class ReactApplication : Application() {
  private lateinit var applicationComponent: ApplicationComponent

  override fun onCreate() {
    super.onCreate()
    if (!BuildConfig.RELEASE) {
      Timber.plant(Timber.DebugTree())
    }
    applicationComponent = createApplicationComponent()
  }

  private fun createApplicationComponent(): ApplicationComponent {
    return DaggerApplicationComponent.builder()
      .build()
  }

  fun getApplicationComponent(): ApplicationComponent {
    return applicationComponent
  }
}