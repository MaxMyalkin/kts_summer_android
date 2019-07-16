package ru.ktsstudio.myapplication.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.ktsstudio.myapplication.data.stores.TokenStore

class AddTokenHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenStore.token
        val originalRequest = chain.request()

        // modify request
        val request = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        // make request
        val response = chain.proceed(request)

        // Get data from response

        return response
    }
}
