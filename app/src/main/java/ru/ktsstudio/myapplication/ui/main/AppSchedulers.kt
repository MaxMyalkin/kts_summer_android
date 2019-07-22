package ru.ktsstudio.myapplication.ui.main

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulers {

    fun ui(): Scheduler = AndroidSchedulers.mainThread()

    fun computation() = Schedulers.computation()

    fun io(): Scheduler = Schedulers.io()
}
