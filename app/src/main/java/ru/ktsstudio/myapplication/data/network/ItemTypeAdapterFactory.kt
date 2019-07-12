package ru.ktsstudio.myapplication.data.network

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ItemTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>?): TypeAdapter<T> {
        val delegate = gson.getDelegateAdapter(this, type)
        val elementAdapter = gson.getAdapter(JsonElement::class.java)

        return object : TypeAdapter<T>() {
            override fun read(`in`: JsonReader?): T {
                var jsonElement = elementAdapter.read(`in`)
                if (jsonElement.isJsonObject && jsonElement.asJsonObject.has(SUCCESS_FIELD)) {
                    getDataField(jsonElement.asJsonObject)
                        ?.let { jsonElement = it }
                }
                return delegate.fromJsonTree(jsonElement)
            }

            override fun write(out: JsonWriter?, value: T) {
                delegate.write(out, value)
            }
        }
            .nullSafe()
    }

    private fun getDataField(jsonObject: JsonObject): JsonElement? {
        if (jsonObject.has(DATA_FIELD)) {
            val data = jsonObject.get(DATA_FIELD)
            if (data.isJsonObject || data.isJsonArray) {
                return jsonObject.get(DATA_FIELD)
            }
        }
        return null
    }

    companion object {
        private const val DATA_FIELD = "data"
        private const val SUCCESS_FIELD = "success"
    }
}