package com.github.raenar4k.reactweather.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import com.github.raenar4k.reactweather.R
import com.github.raenar4k.reactweather.REQUEST_CODE_PLACE_AUTOCOMPLETE
import com.github.raenar4k.reactweather.getApplication
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.AutocompleteFilter.TYPE_FILTER_CITIES
import com.google.android.gms.location.places.PlacePhotoMetadataResult
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

  lateinit var googleApiClient: GoogleApiClient
  lateinit var imageView: ImageView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    getUsers()

    googleApiClient = createGoogleApiClient(this)

    val button = findViewById(R.id.button) as Button
    button.setOnClickListener { startPlaceAutocomplete() }
    imageView = findViewById(R.id.image_view) as ImageView
  }

  private fun createGoogleApiClient(activity: FragmentActivity): GoogleApiClient {
    return GoogleApiClient.Builder(activity)
      .addApi(Places.GEO_DATA_API)
      .enableAutoManage(activity) { connectionFailedResult -> Timber.d(connectionFailedResult.errorMessage) }
      .build()
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
      getPhoto(place.id)
    }
  }

  private fun getPhoto(placeId: String) {
    Single.fromCallable {
      // As per api, result can be null, needs check
      val result: PlacePhotoMetadataResult? = Places.GeoDataApi.getPlacePhotos(googleApiClient, placeId).await()

      if (result?.status?.isSuccess == true) {
        val photoMetadataBuffer = result.photoMetadata
        val photoMetadata = photoMetadataBuffer.get(0)
        val bitmap = photoMetadata.getPhoto(googleApiClient).await().bitmap
        photoMetadataBuffer.release()

        bitmap
      } else {
        throw IllegalStateException("error while getting place photo")
      }
    }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { imageView.setImageBitmap(it) },
        { Timber.d(it) })
  }

}
