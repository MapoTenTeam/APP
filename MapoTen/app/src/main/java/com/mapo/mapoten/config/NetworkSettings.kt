package com.mapo.mapoten.config

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.http.RetryAndFollowUpInterceptor

object NetworkSettings {

    lateinit var client: OkHttpClient.Builder

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
    }
}