package com.mapo.mapoten.data.Login

data class LoginResponse (
    val statusCode: Int,
    val message: String,
    val accessToken: String,
    val user_se: String
)

