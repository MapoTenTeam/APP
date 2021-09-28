package com.mapo.mapoten.service

import android.provider.ContactsContract
import com.mapo.mapoten.data.EditMyProfileItem
import com.mapo.mapoten.ui.data.PersonalProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface AccountManageService {


    @GET("user/personal/profile")
    fun getUserProfile(
        @Header(
            "Authorization"
        ) auth : String
    ): Call<List<PersonalProfile>>
}


