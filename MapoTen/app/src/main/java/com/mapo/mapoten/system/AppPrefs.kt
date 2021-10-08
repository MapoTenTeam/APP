package com.mapo.mapoten.system

import android.app.Application
import android.content.Context

object AppPrefs {
    private const val LOGIN_STATE = "login"
    const val TOKEN = "token"
    const val APP_PRESFS = "app_prefs"

//    fun loginApp(context: Context) {
//        val prefs = context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
//        prefs.edit()
//            .putBoolean(LOGIN_STATE, true)
//            .apply()
//    }

    fun saveToken(context: Context, token: String) {
        val prefs = context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(TOKEN, token)
            putBoolean(LOGIN_STATE, true)
        }.apply()
    }

    fun getToken(context: Context) : String? {
        return context.getSharedPreferences(APP_PRESFS, Application.MODE_PRIVATE)
            .getString(TOKEN, "")
    }


}