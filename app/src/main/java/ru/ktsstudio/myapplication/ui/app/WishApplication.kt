package ru.ktsstudio.myapplication.ui.app

import android.app.Application
import ru.ktsstudio.myapplication.di.DI
import ru.ktsstudio.myapplication.di.modules.NetworkModule
import toothpick.Toothpick

class WishApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        Toothpick.openScope(DI.APP)
            .apply { installModules(NetworkModule()) }
    }
}