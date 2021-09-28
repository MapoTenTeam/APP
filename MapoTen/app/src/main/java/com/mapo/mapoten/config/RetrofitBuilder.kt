package com.mapo.mapoten.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private var instance: Retrofit? = null
    private const val BASE_URL: String = "http://121.162.15.140:3000/"

    init {
        instance = Retrofit.Builder()
            .baseUrl(BASE_URL)
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


