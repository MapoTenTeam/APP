package com.mapo.mapoten.data.Login

import com.google.gson.annotations.SerializedName

data class AuthCredentialsEnterpriseDto(
    @SerializedName("CMPNY_NM") // 회사명
    val companyName: String,
    @SerializedName("ENTRPRS_MBER_ID")  // 아이디
    val id: String,
    @SerializedName("APPLCNT_EMAIL_ADRES")  // 이메일
    val email: String,
    @SerializedName("ENTRPRS_MBER_PASSWORD")  // 비밀번호
    val password: String,
    @SerializedName("APPLCNT_NM")  // 신청인명
    val name: String,
    @SerializedName("EMAIL_VRFCT")  // 이메일 인증 여부
    val emailVrfct: Int,
    @SerializedName("TERMS")  // 이용약관 체크여부
    val termAgree: Int,
    @SerializedName("BIZRNOAVAILABLE")  // 사업자번호 확인여부
    val confirmBizrno: Int
)




