package com.mapo.mapoten.data.employment

import com.google.gson.annotations.SerializedName

data class GeneralEmpPostingDTO(
    @SerializedName("JOBID")
    val jobId: Int,

    @SerializedName("CMPNY_NM")
    val companyName: String, // 사업체명

    @SerializedName("CMPNY_IM")
    val companyImage: String, // 이미지 주소

    @SerializedName("TITLE")
    val title: String, // 채용 제목

    @SerializedName("JOB_TYPE_DESC")
    val jobType: String, // 모집 직종

    @SerializedName("WORK_ADDRESS")
    val address: String, // 근무 예정지

    @SerializedName("CAREER")
    val career: String, // 경력

    @SerializedName("STARTRECEPTION")
    val startReception: String, // 접수 시작일

    @SerializedName("ENDRECEPTION")
    val endReception: String // 접수 마감일


)
