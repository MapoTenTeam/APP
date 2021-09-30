package com.mapo.mapoten.service

import com.mapo.mapoten.data.Login.DuplicateIdInfoItem
import com.mapo.mapoten.data.Login.EmailAuth
import com.mapo.mapoten.data.Login.LoginRequest
import com.mapo.mapoten.data.Login.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("user/duplicate/id/{userid}")
    fun isDuplicateUserId(@Path("userid") userId : String) : Call<DuplicateIdInfoItem>

    @GET("user/duplicate/email/{email}")
    fun isDuplicateUserEmail(@Path("email") email : String) : Call<DuplicateIdInfoItem>

    @GET("user/duplicate/bizrno/{bizrno}")
    fun isDuplicateBizrno(@Path("bizrno") bizrno : String) : Call<DuplicateIdInfoItem>

    @POST("user/signin")
    fun requestLogin(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>

    @POST("user/auth/email/{email}")
    fun emailAuth (@Path("email") email :String ) : Call<EmailAuth>




}