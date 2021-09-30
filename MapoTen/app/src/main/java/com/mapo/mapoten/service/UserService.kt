package com.mapo.mapoten.service

import com.mapo.mapoten.data.DuplicateIdInfoItem
import com.mapo.mapoten.data.LoginRequest
import com.mapo.mapoten.data.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("user/duplicate/id/{userid}")
    fun isDuplicateUserId(@Path("userid") userId : String) : Call<DuplicateIdInfoItem>

    @GET("user/duplicate/email/{email}")
    fun isDuplicateUserEmail(@Path("email") email : String) : Call<DuplicateIdInfoItem>

    @POST("user/signin")
    fun requestLogin(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>

}