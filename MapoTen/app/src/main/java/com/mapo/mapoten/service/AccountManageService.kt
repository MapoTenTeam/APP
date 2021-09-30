package com.mapo.mapoten.service

import com.mapo.mapoten.data.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface AccountManageService {

    //개인 마이페이지

    @GET("user/personal/profile")
    fun getUserProfile(): Call<PersonalProfile>


    @PUT("user/personal/upload/profile")
    fun updateUserProfile(@Body updateProfile : UpdatePersonalProfileItems
    ):Call<Void>


    //기업 마이페이지
    @GET("user/enterprise/profile")
    fun getCompanyProfile():Call<BusinessProfile>

}


