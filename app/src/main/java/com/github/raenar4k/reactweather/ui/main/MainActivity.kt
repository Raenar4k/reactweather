package com.github.raenar4k.reactweather.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.github.raenar4k.reactweather.R
import com.github.raenar4k.reactweather.REQUEST_CODE_PLACE_AUTOCOMPLETE
import com.github.raenar4k.reactweather.getApplication
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.AutocompleteFilter.TYPE_FILTER_CITIES
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    getUsers()

    val button = findViewById(R.id.button) as Button
    button.setOnClickListener { startPlaceAutocomplete() }
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

  private fun startPlaceAutocomplete() {
    try {
      val intent = PlaceAutocomplete
        .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
        .setFilter(AutocompleteFilter.Builder().setTypeFilter(TYPE_FILTER_CITIES).build())
        .build(this)
      startActivityForResult(intent, REQUEST_CODE_PLACE_AUTOCOMPLETE)
    } catch (e: GooglePlayServicesRepairableException) {
      Timber.d(e)
    } catch (e: GooglePlayServicesNotAvailableException) {
      Timber.d(e)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == REQUEST_CODE_PLACE_AUTOCOMPLETE && resultCode == Activity.RESULT_OK) {
      val place = PlaceAutocomplete.getPlace(this, data)
      Timber.d("Selected city: ${place.name}, id: ${place.id}")
    }
  }
}
