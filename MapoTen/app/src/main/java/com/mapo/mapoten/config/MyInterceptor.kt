package com.mapo.mapoten.config

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.RetryAndFollowUpInterceptor
import retrofit2.Retrofit

class MyInterceptor :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       val request = chain.request()
           .newBuilder()
           .addHeader("Authorization", "Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoiMTEiLCJpYXQiOjE2MzI4MTkxMzB9.L7r4dwUxbKhSboeo15Pq-LkMZR3sUOTy-QX6Sx1oRW4")
           .build()
        return chain.proceed(request)
    }

    /*lateinit var client: OkHttpClient.Builder

    init {
        client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val origin = chain.request()
                val newRequest = origin.newBuilder()
                    .addHeader("Authorization", "Bearer")
                    .build()
                chain.proceed(newRequest)
            }.addInterceptor(RetryInterceptor())
    }

    private class RetryInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            var response = chain.proceed(request)
            var cnt = 0
            var limit = 2
            while (!response.isSuccessful && cnt < limit) {
                cnt++
                response.close()
                response = chain.proceed(request)
            }
            return response
        }
    }*/
}