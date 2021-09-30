package com.mapo.mapoten.data

data class LoginResponse (
    val statusCode: Int,
    val message: String,
    val accessToken: String
)

