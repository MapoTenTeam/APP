package com.mapo.mapoten.service

import com.mapo.mapoten.data.employment.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EmploymentService {

    @GET("job/public")
    fun getPublicJobList(@Query("page") page : Int): Call<GeneralJobPostingResponse>

    @GET("job/public/detail/{id}")
    fun inquirePublicDetailPosting(@Path("id") id: Int): Call<EmploymentResponse>

    @GET("job/general")
    fun getGeneralJobList(@Query("page") page : Int): Call<GeneralJobPostingResponse>

    @GET("job/detail/{jobid}")
    fun inquireGeneralDetailPosting(@Path("jobid") id: Int): Call<EmploymentResponse>


    // 기업 채용공고 목록 조회
    @GET("job/enterprise/list")
    fun getEnterpriseJobList(@Query("page") page : Int): Call<SelectJobEnterpriseOutputDto>

    @GET("job/enterprise/list/detail/{jobid}")
    fun getEnterPriseJobDetail(@Path("jobid") jobId: Int): Call<SelectJobEnterpriseDetailOutputDto>
}