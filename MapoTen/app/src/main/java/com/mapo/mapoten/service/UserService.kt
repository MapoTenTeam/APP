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

    @POST("user/personal/signup")
    fun requestPersonalSignUp(
        @Body authCredentialsPersonalDto: AuthCredentialsPersonalDto
    ) : Call<SignUpResponse>

    @POST("user/enterprise/signup")
    fun requestEnterpriseSignUp(
        @Body authCredentialsEnterpriseDto: AuthCredentialsEnterpriseDto
    ) : Call<SignUpResponse>

    @POST("user/find/id")
    fun getUserByFindId(
        @Body idFindInputDto: UserByIdFindInputDto
    ) : Call<GetUserByIdFindOutputDto>

    @POST("user/find/password")
    fun getUserByFindPwd(
        @Body pwdFindInputDto: UserByPasswordFindInputDto
    ) : Call<GetUserByPasswordFindOutputDto>



}

