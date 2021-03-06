package com.mapo.mapoten.service

import com.mapo.mapoten.data.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface AccountManageService {

    //개인 마이페이지

    @GET("user/personal/profile")
    fun getUserProfile(): Call<PersonalProfile>


    @PUT("user/personal/upload/profile")
    fun updateUserProfile(
        @Body updateProfile: UpdatePersonalProfileItems
    ): Call<Void>


    //기업 마이페이지
    @GET("user/enterprise/profile")
    fun getCompanyProfile(): Call<BusinessProfile>

    @PUT("user/enterprise/upload/profile")
    fun updateBusinessProfile(@Body updateProfile: UpdateBusinessProfileItems): Call<Void>

    @Multipart
    @POST("user/enterprise/upload/profile/image")
    fun updateBusinessLogoImg(@Part file: MultipartBody.Part): Call<ImageResponse>

    //공통 - 현재비번조회
    @FormUrlEncoded
    @POST("user/personal/change/password")
    fun checkCurrentPw(@Field("PASSWORD") password: String): Call<ImageResponse>

    @FormUrlEncoded
    @PATCH("user/personal/change/password")
    fun updatePassword(@Field("PASSWORD") password: String): Call<Void>

    @FormUrlEncoded
    @PATCH("user/enterprise/change/password")
    fun updateBusinessPassword(@Field("PASSWORD") password: String): Call<Void>


    // 회원 탈퇴
    @DELETE("user/personal")
    fun deleteUserAccount(): Call<ImageResponse>

    @DELETE("user/enterprise")
    fun deleteEnterpriseAccount(): Call<ImageResponse>
}


