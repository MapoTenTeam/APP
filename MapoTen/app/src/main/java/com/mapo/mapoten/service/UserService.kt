package com.mapo.mapoten.service

import com.mapo.mapoten.data.DuplicateIdInfoItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("user/duplicate/id/{userid}")
    fun isDuplicateUserId(@Path("userid") userId : String) : Call<DuplicateIdInfoItem>
}