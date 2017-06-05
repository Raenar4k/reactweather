package com.github.raenar4k.reactweather.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.raenar4k.reactweather.R
import com.github.raenar4k.reactweather.getApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    getUsers()
  }

  private fun getUsers() {
    val userService = getApplication(this).getApplicationComponent().userService()

    userService.getUsers()
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { Timber.d("Success") },
        { Timber.d("Error") }
      )
  }
}
