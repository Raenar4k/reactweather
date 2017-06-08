package com.github.raenar4k.reactweather.network.google_places.models

data class PlaceDetails(val result: PlaceDetailsResult) {

  data class PlaceDetailsResult(val photos: List<PlaceDetailsPhoto>) {

    data class PlaceDetailsPhoto(val photo_reference: String)

  }

}