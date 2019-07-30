package ru.ktsstudio.myapplication.di.providers

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.ktsstudio.myapplication.data.network.ItemTypeAdapterFactory
import javax.inject.Inject
import javax.inject.Provider

class GsonProvider @Inject constructor() : Provider<Gson> {
    override fun get(): Gson {
        return GsonBuilder()
            .enableComplexMapKeySerialization()
            .registerTypeAdapterFactory(ItemTypeAdapterFactory())
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }
}