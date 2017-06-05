package com.github.raenar4k.reactweather.network

import com.github.raenar4k.reactweather.ApplicationScope
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

  @ApplicationScope
  @Provides
  fun provideUserService(): UserService {
    val callAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    val retrofit: Retrofit = Retrofit.Builder()
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(callAdapterFactory)
      .baseUrl("https://randomuser.me/")
      .build()

    return retrofit.create(UserService::class.java)
  }
}