package ru.ktsstudio.myapplication.di.providers

import retrofit2.Retrofit
import ru.ktsstudio.myapplication.data.network.GithubApiService
import javax.inject.Inject
import javax.inject.Provider

class ApiProvider @Inject constructor(
    private val retrofit: Retrofit
) : Provider<GithubApiService> {
    override fun get(): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }
}