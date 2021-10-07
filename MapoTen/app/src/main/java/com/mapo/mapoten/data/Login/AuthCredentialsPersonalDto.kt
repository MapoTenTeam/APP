package com.mapo.mapoten.data.Login

import com.google.gson.annotations.SerializedName

data class AuthCredentialsPersonalDto(
    @SerializedName("MBER_NM")
    val name: String,
    @SerializedName("MBER_ID")
    val id: String,
    @SerializedName("MBER_EMAIL_ADRES")
    val email: String,
    @SerializedName("PASSWORD")
    val password: String,
    @SerializedName("EMAIL_VRFCT")  // 이메일 인증 여부
    val emailVrfct: Int,
    @SerializedName("TERMS")  // 이용약관 체크여부
    val termAgree: Int
)
