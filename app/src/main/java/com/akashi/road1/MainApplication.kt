package com.akashi.road1

import android.app.Application

class MainApplication : Application() {

    companion object {
        lateinit var context: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}