package com.mapo.mapoten.config

import retrofit2.Retrofit
import okhttp3.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {

    private val userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoiMTEiLCJpYXQiOjE2MzI4ODI5OTJ9.qZWawMCoY9198_D0ZE1kacZwLFfixyMQ7e4Dho8Wgc0"
    private val businessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoidGVzdF9wdSIsImlhdCI6MTYzMzU5MTgyN30.M-aQWvEgvqvLjHGwGYnfr248oNIwKECTWIqjzRXHb7w"
    private val businessToken2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoidGVzdF9nZSIsImlhdCI6MTYzMzM5NDgzN30.jwne1Ip2yQoc-FLOM5we4lnACXZLV7KgsAUbqIMInJM"
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor(businessToken))
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





