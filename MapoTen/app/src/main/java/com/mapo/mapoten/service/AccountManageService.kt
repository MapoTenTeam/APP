package com.mapo.mapoten.service

import com.mapo.mapoten.ui.data.PersonalProfile
import com.mapo.mapoten.ui.data.PersonalProfileItems
import retrofit2.Call
import retrofit2.http.*

interface AccountManageService {


    @GET("user/personal/profile")
    fun getUserProfile(): Call<PersonalProfile>
}


