package com.mapo.mapoten.data.Login

import com.google.gson.annotations.SerializedName

data class UserByIdFindInputDto(
    @SerializedName("USER_NM")
    val name : String,
    @SerializedName("USER_EMAIL")
    val email : String
)
