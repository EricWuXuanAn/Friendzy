package com.example.tip102group01friendzy

import android.accounts.Account
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path
import java.lang.reflect.Member

interface APIService {
//    @Multipart
    @POST("rest/member/register")
    suspend fun  createMember(@Body request: CreateMemberRequest): CreatMemberResponce

    @POST("rest/member/forget")
    suspend fun forgetPassword(@Body member: Member) //FIXME

    @GET("rest/member/login/{email}/{mpassword}")
    suspend fun login(@Path("email") account: String, @Path("mpassword")mpasswod: String):Response<Unit>
}

object RetrofitInstance{
    val api: APIService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/friendzy-new-web/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

    }
}