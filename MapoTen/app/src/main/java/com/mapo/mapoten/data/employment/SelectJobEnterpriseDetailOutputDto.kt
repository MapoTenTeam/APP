package com.mapo.mapoten.data.employment

data class SelectJobEnterpriseDetailOutputDto(
    val statusCode: Int,
    val message: String,
    val data: JobEnterpriseDetailOutputDto
)
