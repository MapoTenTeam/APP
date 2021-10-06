package com.mapo.mapoten.service

import com.mapo.mapoten.data.Login.*
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("user/duplicate/id/{userid}")
    fun isDuplicateUserId(@Path("userid") userId : String) : Call<DuplicateInfoItem>

    @GET("user/duplicate/email/{email}")
    fun isDuplicateUserEmail(@Path("email") email : String) : Call<DuplicateInfoItem>

    @GET("user/duplicate/bizrno/{bizrno}")
    fun isDuplicateBizrno(@Path("bizrno") bizrno : String) : Call<DuplicateInfoItem>

    @POST("user/auth/email/{email}")
    fun emailAuth (@Path("email") email :String ) : Call<GetUserByEmailAuthDto>

    @POST("user/signin")
    fun requestLogin(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>

//    @POST("user/personal/signup")
//    fun requestSignUp(
//        @Body signUpRequest: SignUpRequest
//    ) : Call<SignUpResponse>

    @FormUrlEncoded
    @POST("user/personal/signup")
    fun requestSignUp(
        @Field("MBER_NM") name:String,
        @Field("MBER_ID") userId:String,
        @Field("MBER_EMAIL_ADRES") email:String,
        @Field("PASSWORD") userPw:String,
        @Field("EMAIL_VRFCT") emailVrfct:Boolean,
        @Field("TERMS") terms:Boolean,
    ) : Call<SignUpResponse>

    @POST("user/find/id")
    fun getUserByFindId(
        @Body idFindInputDto: UserByIdFindInputDto
    ) : Call<GetUserByIdFindOutputDto>


}