package com.mapo.mapoten.service

import com.mapo.mapoten.data.DuplicateIdInfoItem
import com.mapo.mapoten.data.PersonalProfile
import com.mapo.mapoten.data.PersonalProfileItems
import com.mapo.mapoten.data.UpdatePersonalProfileItems
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface AccountManageService {

    @GET("user/personal/profile")
    fun getUserProfile(): Call<PersonalProfile>


    @PUT("user/personal/upload/profile")
    fun updateUserProfile(@Body updateProfile : UpdatePersonalProfileItems
    ):Call<Void>

}


