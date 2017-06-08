package com.github.raenar4k.reactweather.network.google_places

import com.github.raenar4k.reactweather.network.google_places.models.PlaceDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GooglePlacesApi {
  @GET("https://maps.googleapis.com/maps/api/place/details/json?placeid={placeId}&key={apiKey}")
  fun getPlaceDetails(@Path("placeId") placeId: String, @Path("apiKey") apiKey: String): Single<PlaceDetails>

  @GET("https://maps.googleapis.com/maps/api/place/photo?maxwidth={maxWidth}&photoreference={photoReference}&key={apiKey}")
  fun getPlacePhoto(@Path("placeId") placeId: String, @Path("apiKey") apiKey: String): Single<PlaceDetails>
}

