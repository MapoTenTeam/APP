package com.mapo.mapoten.data.employment

import com.google.gson.annotations.SerializedName

data class JobEnterpriseDetailOutputDto(

    @SerializedName("JOBID")
    val jobId: Int,

    // 기업 소개
    @SerializedName("CMPNY_NM")
    val name: String, // 사업체명
    @SerializedName("BIZRNO")
    val bizrNo: String, // 사업자번호
    @SerializedName("ADRES")
    val address: String, // 주소
    @SerializedName("INDUTY")
    val sector: String, // 업종
    @SerializedName("CEO")
    val ceo: String, // 대표자
    @SerializedName("NMBR_WRKRS")
    val quaternion: String, // 사원수

    @SerializedName("TITLE")
    val title : String, // 채용공고제목

)
