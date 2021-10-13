package com.mapo.mapoten.data.employment

import com.google.gson.annotations.SerializedName

data class CodeName(

    @SerializedName("CODE")
    val code: String,

    @SerializedName("CODE_NM")
    val codeName: String,
)
