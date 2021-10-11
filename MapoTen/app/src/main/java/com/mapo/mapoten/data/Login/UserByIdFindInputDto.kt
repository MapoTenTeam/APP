package com.mapo.mapoten.data.Login

import com.google.gson.annotations.SerializedName

data class UserByIdFindInputDto(
    @SerializedName("USER_NM")
    val userId : String,
    @SerializedName("USER_EMAIL")
    val password : String
)
