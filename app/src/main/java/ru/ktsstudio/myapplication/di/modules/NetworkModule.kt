package ru.ktsstudio.myapplication.di.modules

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.ktsstudio.myapplication.data.network.GithubApiService
import ru.ktsstudio.myapplication.di.providers.ApiProvider
import ru.ktsstudio.myapplication.di.providers.GsonProvider
import ru.ktsstudio.myapplication.di.providers.LoggingInterceptorProvider
import ru.ktsstudio.myapplication.di.providers.OkHttpProvider
import ru.ktsstudio.myapplication.di.providers.RetrofitProvider
import toothpick.config.Module

class NetworkModule : Module() {

    init {
        bind(Gson::class.java).toProvider(GsonProvider::class.java).providesSingletonInScope()
        bind(Interceptor::class.java).withName("logging").toProvider(LoggingInterceptorProvider::class.java)
        bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).providesSingletonInScope()
        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java).providesSingletonInScope()
        bind(GithubApiService::class.java).toProvider(ApiProvider::class.java).instancesInScope()
    }
}