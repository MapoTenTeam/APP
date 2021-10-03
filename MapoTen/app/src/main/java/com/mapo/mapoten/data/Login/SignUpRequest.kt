package com.mapo.mapoten.data.Login

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("MBER_NM")
    val name: String,
    @SerializedName("MBER_ID")
    val id: String,
    @SerializedName("MBER_EMAIL_ADRES")
    val email: String,
    @SerializedName("PASSWORD")
    val password: String,
    @SerializedName("EMAIL_VRFCT")
    val emailVrfct: Boolean,
    @SerializedName("TERMS")
    val termAgree: Boolean
    )
