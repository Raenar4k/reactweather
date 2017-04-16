package com.github.raenar4k.reactweather.network

import com.github.raenar4k.reactweather.network.model.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET

interface UserService {
    @GET("api/?results=5&inc=name,gender,nat,picture&noinfo")
    fun getUsers(): Single<ApiResponse>
}