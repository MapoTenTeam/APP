package com.mapo.mapoten.data.employment

data class EmploymentResponse(
    val statusCode: Int,
    val message: String,
    val data: EmploymentJobPostingDetailItem
)