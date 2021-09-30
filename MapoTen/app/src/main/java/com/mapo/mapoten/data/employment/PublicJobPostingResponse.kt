package com.mapo.mapoten.data.employment

data class PublicJobPostingResponse(
    val statusCode: Int,
    val message: String,
    val data: ArrayList<EmploymentJobPostingDetailItem>
)
