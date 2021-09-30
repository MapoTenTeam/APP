package com.mapo.mapoten.service

import com.mapo.mapoten.data.employment.EmploymentResponse
import com.mapo.mapoten.data.employment.PublicJobPostingResponse
import com.mapo.mapoten.data.employment.SelectJobEnterpriseDetailOutputDto
import com.mapo.mapoten.data.employment.SelectJobEnterpriseOutputDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EmploymentService {

    @GET("job/public")
    fun getPublicJobList(): Call<ArrayList<PublicJobPostingResponse>>

    @GET("job/public/detail/{id}")
    fun inquirePublicDetailPosting(@Path("id") id: Int): Call<EmploymentResponse>

    @GET("job/general")
    fun getGeneralJobList(): Call<ArrayList<PublicJobPostingResponse>>

    @GET("job/general/detail/{id}")
    fun inquireGeneralDetailPosting(@Path("id") id: Int): Call<EmploymentResponse>


    // 기업 채용공고 목록 조회
    @GET("job/enterprise/list")
    fun getEnterpriseJobList(): Call<SelectJobEnterpriseOutputDto>

    @GET("job/enterprise/list/detail/{jobid}")
    fun getEnterPriseJobDetail(@Path("jobid") jobId: Int): Call<SelectJobEnterpriseDetailOutputDto>
}