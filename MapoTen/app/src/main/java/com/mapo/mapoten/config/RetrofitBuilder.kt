package com.mapo.mapoten.config

import com.mapo.mapoten.system.AppPrefs
import com.mapo.mapoten.ui.activity.App
import com.mapo.mapoten.ui.activity.MainActivity
import retrofit2.Retrofit
import okhttp3.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {

    private val token = AppPrefs.getToken(App.getContext())
    private val testToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoidGVzdF9wdSIsImlhdCI6MTYzNDAxNjU4M30.4-m0kMj_xPq2jUE8-A9ucf-vFPr-hhOgARxfn3rYEJI"
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor(token!!))
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




}





