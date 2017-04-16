package com.github.raenar4k.reactweather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.raenar4k.reactweather.network.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getUsers()
    }

    private fun getUsers() {
        val callAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(callAdapterFactory)
                .baseUrl("https://randomuser.me/")
                .build()

        val userService = retrofit.create(UserService::class.java)

        userService.getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d("Test tag", "Success") },
                        { Log.d("Test tag", "Error") }
                )
    }
}
