package com.mapo.mapoten.data.employment

data class SelectJobEnterpriseOutputDto(

    val statusCode: Int,
    val message: String,
    val ok : Boolean,
    val data: ArrayList<SelectJobEnterpriseItem>
)
