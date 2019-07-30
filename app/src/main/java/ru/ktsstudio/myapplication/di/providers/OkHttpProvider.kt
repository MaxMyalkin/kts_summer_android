package ru.ktsstudio.myapplication.di.providers

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import ru.ktsstudio.myapplication.data.network.AddTokenHeaderInterceptor
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class OkHttpProvider @Inject constructor(
    @Named("logging") private val loggingInterceptor: Interceptor
) : Provider<OkHttpClient> {
    override fun get(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AddTokenHeaderInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }
}