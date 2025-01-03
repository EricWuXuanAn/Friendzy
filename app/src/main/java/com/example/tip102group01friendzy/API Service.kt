package com.example.tip102group01friendzy

import com.example.tip102group01friendzy.ui.feature.customer.Black_List
import com.example.tip102group01friendzy.ui.feature.customer.Favorite_List
import com.example.tip102group01friendzy.ui.feature.customer.OrderList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import java.lang.reflect.Member

interface APIService {
    @Multipart
    @POST("rest/member/register")
    suspend fun  createMember(@Body request: CreateMemberRequest): CreatMemberResponce

    @POST("rest/member/forget")
    suspend fun forgetPassword(@Body member: Member) //FIXME

    //拿到訂單資料
    @GET("rest/customer/orderList/showAllList")
    suspend fun showAllOrderList(): List<OrderList>

    @GET("rest/customer/blackList")
    suspend fun showAllBlackList():List<Black_List>

    @GET("rest/customer/favoriteList")
    suspend fun showAllFavoriteList():List<Favorite_List>
    


    

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