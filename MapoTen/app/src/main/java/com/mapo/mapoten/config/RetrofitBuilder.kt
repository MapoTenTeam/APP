package com.mapo.mapoten.config

import com.mapo.mapoten.ui.activity.App
import retrofit2.Retrofit
import okhttp3.*
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private var token = App.prefs.token


    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()


    private var instance: Retrofit? = null
    private const val BASE_URL: String = "http://121.162.15.140:3000/"

    init {
        instance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }

    fun getToken() : String = token!!

    fun setToken(token :String) {
        this.token = token
    }
}





