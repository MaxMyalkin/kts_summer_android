package ru.ktsstudio.myapplication.data.network

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import ru.ktsstudio.myapplication.data.stores.OkHttp
import java.io.InputStream

@GlideModule
class GlideModule : AppGlideModule() {
    private val okHttpClient: OkHttpClient = OkHttp.instance

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val factory = OkHttpUrlLoader.Factory(okHttpClient)

        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }
}
