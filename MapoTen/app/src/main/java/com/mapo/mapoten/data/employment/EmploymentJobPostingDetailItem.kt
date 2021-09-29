package com.mapo.mapoten.data.employment

import com.google.gson.annotations.SerializedName

data class EmploymentJobPostingDetailItem(

    // 기업 소개
    val name: String, // 사업체명
    val address: String, // 주소
    val sector: String, // 업종
    val ceo: String, // 대표자
    val quaternion: String, // 사원수

    @SerializedName("web_site")
    val webSite: String, // 홈페이지

    // 채용
    val title: String, // 채용제목

    @SerializedName("job_type_desc")
    val jobTypeDesc: String, // 모집직종

    @SerializedName("require_count")
    val requireCount: String, // 모집인원

    @SerializedName("job_desc")
    val jobDesc: String, // 직무내용
    val education: String, // 학력
    val career: String, // 경력

    @SerializedName("career_period")
    val careerPeriod: String, // 경력기간

    @SerializedName("work_area")
    val workArea: String, // 근무예정지

    @SerializedName("work_address")
    val workAddress: String, // 근무예정지 주소

    @SerializedName("work_area_desc")
    val workAreaDesc: String, // 소속산업단지

    val employType: String, // 고용형태

    @SerializedName("employType_det")
    val employTypeDet: String, // 고용형태 상세

    val paycd: String, // 임금 지급형태

    @SerializedName("pay_amount")
    val payAmount: String, // 임금금액

    @SerializedName("work_time_type")
    val workTimeType: String, // 근무시간유형

    @SerializedName("meal_cod")
    val mealCod: String, // 식사제공
    val workingHours: String, // 1주당 근로시간

    @SerializedName("severance_pay_type")
    val severancePayType: String, // 퇴직금 형태

    @SerializedName("social_insurance")
    val socialInsurance: String, // 사회보험

    @SerializedName("closing_type")
    val closingType: String, // 접수 마감일 구분

    @SerializedName("end_reception")
    val endReception: String, // 접수 마감일

    @SerializedName("apply_method")
    val applyMethod: String, // 접수 방법

    @SerializedName("apply_method_etc")
    val applyMethodEtc: String, // 접수 방법 상세

    @SerializedName("test_mthod")
    val testMethod: String, // 전형방법

    @SerializedName("test_method_etc")
    val testMethodEtc: String, // 전형방법 상세

    @SerializedName("apply_document")
    val applyDocument: String, // 제출서류

    // 채용 담당
    @SerializedName("contact_name")
    val contactName: String, // 재용담당자 이름

    @SerializedName("contact_phone")
    val contactPhone: String, // 재용담당자 연락처

    @SerializedName("contact_email")
    val contactEmail: String, // 채용 담당자 이메일

    val image : String // 사업체 사진

)