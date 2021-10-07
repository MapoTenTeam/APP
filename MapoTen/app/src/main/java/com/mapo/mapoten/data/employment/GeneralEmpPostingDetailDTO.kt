package com.mapo.mapoten.data.employment

import com.google.gson.annotations.SerializedName

data class GeneralEmpPostingDetailDTO(
    // 기업 소개
    @SerializedName("JOBID")
    val jobId: Int,

    @SerializedName("CMPNY_NM")
    val name: String, // 사업체명

    @SerializedName("BIZRNO")
    val bizrno: String, // 사업자번호

    @SerializedName("ADRES")
    val address: String, // 주소

    @SerializedName("INDUTY")
    val sector: String, // 업종

    @SerializedName("CEO")
    val ceo: String, // 대표자

    @SerializedName("NMBR_WRKRS")
    val quaternion: String, // 사원수


    // 채용
    @SerializedName("TITLE")
    val title: String, // 채용제목

    @SerializedName("JOB_TYPE_DESC")
    val jobTypeDesc: String, // 모집직종

    @SerializedName("REQUIRE_COUNT")
    val requireCount: String, // 모집인원

    @SerializedName("JOB_DESC")
    val jobDesc: String, // 직무내용

    @SerializedName("DEUCATION")
    val education: String, // 학력

    @SerializedName("CAREER")
    val career: String, // 경력

    @SerializedName("CAREER_PERIOD")
    val careerPeriod: String, // 경력기간

    @SerializedName("WORK_AREA")
    val workArea: String, // 근무예정지

    @SerializedName("WORK_ADDRESS")
    val workAddress: String, // 근무예정지 주소

    @SerializedName("WORK_AREA_DESC")
    val workAreaDesc: String, // 소속산업단지

    @SerializedName("EMPLOYTYPE")
    val employType: String, // 고용형태

    @SerializedName("EMPLOYTYPE_DET")
    val employTypeDet: ArrayList<CodeName>, // 고용형태 상세

    @SerializedName("PAYCD")
    val paycd: String, // 임금 지급형태

    @SerializedName("PAY_AMOUNT")
    val payAmount: String, // 임금금액

    @SerializedName("WORK_TIME_TYPE")
    val workTimeType: String, // 근무시간유형

    @SerializedName("MEAL_COD")
    val mealCod: String, // 식사제공

    @SerializedName("WORKINGHOURS")
    val workingHours: String, // 1주당 근로시간

    @SerializedName("SEVERANCE_PAY_TYPE")
    val severancePayType: String, // 퇴직금 형태

    @SerializedName("SOCIAL_INSURANCE")
    val socialInsurance: ArrayList<CodeName>, // 사회보험

    @SerializedName("CLOSING_TYPE")
    val closingType: String, // 접수 마감일 구분

    @SerializedName("ENDRECEPTION")
    val endReception: String, // 접수 마감일

    @SerializedName("APPLY_METHOD")
    val applyMethod: String, // 접수 방법

    @SerializedName("APPLY_METHOD_ETC")
    val applyMethodEtc: String, // 접수 방법 상세

    @SerializedName("TEST_METHOD")
    val testMethod: String, // 전형방법

    @SerializedName("TEST_METHOD_DTC")
    val testMethodEtc: String, // 전형방법 상세

    @SerializedName("APPLY_DOCUMENT")
    val applyDocument: ArrayList<CodeName>, // 제출서류

    // 채용 담당
    @SerializedName("CONTACT_NAME")
    val contactName: String, // 재용담당자 이름

    @SerializedName("CONTACT_DEPARTMENT")
    val contactDepartment: String, // 재용담당자 이름

    @SerializedName("CONTACT_PHONE")
    val contactPhone: String, // 재용담당자 연락처

    @SerializedName("CONTACT_EMAIL")
    val contactEmail: String, // 채용 담당자 이메일

    @SerializedName("CMPNY_IM")
    val companyImage: String, // 사업체 사진

    @SerializedName("JOB_IM")
    val jobImage: String // 사업체 사진
)
