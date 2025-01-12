package com.example.tip102group01friendzy.ui.feature.search

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface API_Service_Map {
    //拿到訂單資料
    @GET("rest/CompanionInfo")
    suspend fun showCompanionInfo():List<CompanionInfo>
}

object MapApiInstance{
    val api: API_Service_Map by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/friendzy-new-web/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API_Service_Map::class.java)
    }
}