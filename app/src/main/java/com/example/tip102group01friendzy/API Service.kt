package com.example.tip102group01friendzy

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import java.lang.reflect.Member

interface APIService {
    @Multipart
    @POST("/member/register")
    suspend fun  createMember(@Body request: CreateMemberRequest): CreatMemberResponce

    @POST("/member/forget")
    suspend fun forgetPassword(@Body member: Member) //FIXME
}

object RetrofitInstance{
    val api: APIService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/friendzy-new-web/rest")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

    }
}