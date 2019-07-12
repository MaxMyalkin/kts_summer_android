package ru.ktsstudio.myapplication.data.stores

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.ktsstudio.myapplication.data.network.ItemTypeAdapterFactory

object GsonStore {

    val instance: Gson = GsonBuilder()
        .enableComplexMapKeySerialization()
        .registerTypeAdapterFactory(ItemTypeAdapterFactory())
        .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

}