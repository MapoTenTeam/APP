package com.mapo.mapoten.service

import com.mapo.mapoten.data.DuplicateIdInfoItem
import com.mapo.mapoten.ui.data.PersonalProfile
import com.mapo.mapoten.ui.data.PersonalProfileItems
import retrofit2.Call
import retrofit2.http.*

interface AccountManageService {

    @GET("user/personal/profile")
    fun getUserProfile(): Call<PersonalProfile>

    @FormUrlEncoded
    @PUT("user/personal/upload/profile")
    fun updateUserProfile(
        @Field("MBER_NM") name:String,
        @Field("MBER_EMAIL_ADRES") email: String,
        @Field("MBTLNUM") mobile : String,
        @Field("ADRES") address: String,
        @Field("DETAIL_ADRES") detailAd : String
    ):Call<DuplicateIdInfoItem>
}


