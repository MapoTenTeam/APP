package com.mapo.mapoten.ui.activity

import android.app.Application
import com.mapo.mapoten.system.AppPrefs

class App : Application() {

//    init {
//        instance = this
//    }
//
//    companion object {
//        var instance: App? = null
//        fun getContext(): Context {
//            return instance!!.applicationContext
//        }
//    }

    companion object {
        lateinit var prefs: AppPrefs
    }

    override fun onCreate() {
        prefs = AppPrefs(applicationContext)
        super.onCreate()
    }
}