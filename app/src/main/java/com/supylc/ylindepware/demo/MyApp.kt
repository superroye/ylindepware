package com.supylc.ylindepware.demo

import android.app.Application

/**
 * Created by Supylc on 2020/10/12.
 */
class MyApp : Application() {

    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()

        context = this
    }
}