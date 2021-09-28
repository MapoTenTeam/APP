package com.mapo.mapoten.service

import com.mapo.mapoten.data.DuplicateIdInfoItem
import com.mapo.mapoten.data.UpdatePasswordItem
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("user/duplicate/id/{userid}")
    fun isDuplicateUserId(@Path("userid") userId: String): Call<DuplicateIdInfoItem>

    @FormUrlEncoded
    @PATCH("user/personal/change/password")
    @Headers(
        "Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoiMTEiLCJpYXQiOjE2MzI4MjUwMTd9.FZDFWGshnzWtSeLncv0sEPCXWh3ctkCJ-hD5cHBIDLY",
    )
    fun updatePassword(
        @Field("PASSWORD") password: String
    ): Call<UpdatePasswordItem>
}