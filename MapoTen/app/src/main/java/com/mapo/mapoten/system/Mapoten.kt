package com.mapo.mapoten.system

import android.app.Application

class Mapoten : Application() {
    companion object {
        private var _mapoten : Mapoten? = null
        private val mapoten : Mapoten get() = _mapoten!!
        fun getInstance() : Mapoten {
            return if(_mapoten != null) mapoten
            else {
                _mapoten = Mapoten()
                mapoten
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}