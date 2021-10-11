package com.mapo.mapoten.data.Login

import com.google.gson.annotations.SerializedName

data class UserByPasswordFindInputDto(
    @SerializedName("USER_ID")
    val userId : String,
    @SerializedName("USER_EMAIL")
    val email : String
)
