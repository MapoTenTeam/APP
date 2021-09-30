package com.mapo.mapoten.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private val userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoiMTEiLCJpYXQiOjE2MzI4ODI5OTJ9.qZWawMCoY9198_D0ZE1kacZwLFfixyMQ7e4Dho8Wgc0"
    private val compToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoiaGVlMTIzIiwiaWF0IjoxNjMyOTY4MzY4fQ.e3VfXD5Uq93lsh6vjppjX4ndcU1pPSwUx1hXd0TrSHg"

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor(compToken))
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





