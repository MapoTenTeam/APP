package com.mapo.mapoten.data.Login

data class GetUserByEmailAuthDto(
    val statusCode: Int,
    val message: String,
    val code: Int
)