package com.mapo.mapoten.config

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.RetryAndFollowUpInterceptor
import retrofit2.Retrofit

class MyInterceptor(userToken:String) :Interceptor {
    val token = "Bearer $userToken"
    override fun intercept(chain: Interceptor.Chain): Response {
       val request = chain.request()
           .newBuilder()
           .addHeader("Authorization", token)
           .build()
        return chain.proceed(request)
    }

}