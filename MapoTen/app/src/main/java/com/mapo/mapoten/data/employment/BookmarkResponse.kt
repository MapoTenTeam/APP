package com.mapo.mapoten.data.employment

data class BookmarkResponse(
    val statusCode: Int,
    val message: String,
    val count: Int,
    val data: ArrayList<BookmarkDTO>
)
