package com.mapo.mapoten.data.Login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("USER_ID")
    val userId : String,
    @SerializedName("PASSWORD")
    val password : String
)