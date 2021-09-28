package com.mapo.mapoten.data

import com.google.gson.annotations.SerializedName

data class EmploymentJobPostingItem(
    val id: Int,
    val title: String, // 채용 제목
    val name: String, // 사업체 이름
    val address: String, // 사업체 주소

    @SerializedName("start_reception")
    val startReception: String, // 접수 시작

    @SerializedName("end_reception")
    val endReception: String, // 접수 종료

    val image: String, // 사업체 사진
    val count: Int, // 일반 일자리 갯수

    val job : String, // 직무
)
