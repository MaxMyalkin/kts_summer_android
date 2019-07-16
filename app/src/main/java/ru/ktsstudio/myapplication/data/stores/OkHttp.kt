package ru.ktsstudio.myapplication.data.stores

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.ktsstudio.myapplication.data.network.AddTokenHeaderInterceptor

object OkHttp {

    private val loggingInterceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { message -> Log.d("OkHttp", message) }
    )
        .apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    val instance: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AddTokenHeaderInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()
}