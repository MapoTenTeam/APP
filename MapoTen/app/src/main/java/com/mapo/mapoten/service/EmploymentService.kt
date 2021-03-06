package com.mapo.mapoten.service

import com.mapo.mapoten.data.employment.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface EmploymentService {

    @FormUrlEncoded
    @POST("job/public")
    fun getPublicJobList(
        @Query("page") page: Int,
        @Field("SEARCH_NAME") searchTerm: String
    ): Call<GeneralJobPostingResponse>

    @FormUrlEncoded
    @POST("job/general")
    fun getGeneralJobList(
        @Query("page") page: Int,
        @Field("SEARCH_NAME") searchTerm: String
    ): Call<GeneralJobPostingResponse>

    @GET("job/detail/{jobid}")
    fun inquireGeneralDetailPosting(@Path("jobid") id: Int): Call<EmploymentResponse>


    // 기업 채용공고 목록 조회
    @GET("job/enterprise/list")
    fun getEnterpriseJobList(@Query("page") page: Int): Call<SelectJobEnterpriseOutputDto>

    @GET("job/enterprise/list/detail/{jobid}")
    fun getEnterPriseJobDetail(@Path("jobid") jobId: Int): Call<SelectJobEnterpriseDetailOutputDto>

    @DELETE("job/enterprise/delete/{jobid}")
    fun deleteMyJobPosting(@Path("jobid") jobId: Int): Call<Objects>


    // bookmark
    @POST("bookmarks/{jobid}")
    fun registerBookmark(@Path("jobid") id: Int): Call<Objects>

    @PATCH("bookmarks/{bookid}")
    fun cancelBookmark(@Field("jobid") id: Int): Call<Objects>

    @GET("bookmarks")
    fun getBookmarks(@Query("page") page: Int): Call<BookmarkResponse>
}