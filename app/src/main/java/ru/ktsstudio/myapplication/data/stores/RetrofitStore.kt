package ru.ktsstudio.myapplication.data.stores

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.ktsstudio.myapplication.data.network.GithubApiService

object RetrofitStore {

    val instance: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(OkHttp.instance)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonStore.instance))
        .build()

    val service: GithubApiService = instance.create(GithubApiService::class.java)
}