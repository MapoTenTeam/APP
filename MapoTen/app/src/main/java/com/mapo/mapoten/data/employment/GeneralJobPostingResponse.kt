package com.mapo.mapoten.data.employment

data class GeneralJobPostingResponse(

    val statusCode: Int,
    val message: String,
    val count: Int,
    val data: ArrayList<GeneralEmpPostingDTO>

)
