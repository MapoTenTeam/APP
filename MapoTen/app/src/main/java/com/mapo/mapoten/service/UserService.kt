package com.mapo.mapoten.service

import com.mapo.mapoten.data.DuplicateIdInfoItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("user/duplicate/id/{userid}")
    fun isDuplicateUserId(@Path("userid") userId : String) : Call<DuplicateIdInfoItem>

    @GET("user/duplicate/email/{email}")
    fun isDuplicateUserEmail(@Path("email") email : String) : Call<DuplicateIdInfoItem>












}