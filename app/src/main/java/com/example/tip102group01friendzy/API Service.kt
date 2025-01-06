package com.example.tip102group01friendzy

import com.example.tip102group01friendzy.ui.feature.customer.Black_List
import com.example.tip102group01friendzy.ui.feature.customer.Favorite_List
import com.example.tip102group01friendzy.ui.feature.customer.OrderList
import com.example.tip102group01friendzy.ui.feature.customer.Post
import com.example.tip102group01friendzy.ui.feature.customer.addPostRequest
import com.example.tip102group01friendzy.ui.feature.customer.addPostResponse
import com.example.tip102group01friendzy.ui.feature.customer.selectedPostList
import android.accounts.Account
import com.example.tip102group01friendzy.ui.feature.companion.CompanionOrder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.lang.reflect.Member

interface APIService {
//    @Multipart 不要打開
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

    @POST("rest/customer/insertPost")
    suspend fun addNewPost(
        @Body request: addPostRequest
    ): addPostResponse

    @GET("rest/customer/showPostList")
    suspend fun showPostList():List<Post>

    @POST("rest/customer/selectedPostList")
    suspend fun selectedPostList(
        @Body request: selectedPostList
    ):Post


    @GET("rest/member/find")
    suspend fun findMemberInfo(): MemberModel.MemberInfoResponse?

    @POST("rest/member/edit")
    suspend fun edit(@Body request: MemberModel.edit): Response<Unit>
    

    @GET("rest/member/login/{email}/{mpassword}")
    suspend fun login(@Path("email") account: String, @Path("mpassword")mpasswod: String):Response<Unit>


    //陪伴者訂單管理
    @GET("rest/companion/order/showAll")
    suspend fun comShowAllOrder():List<CompanionOrder>

    //陪伴者訂單明細
    @GET("rest/companion/order/showId/{id}")
    suspend fun comOrderDetails(@Path("id") account: Int):CompanionOrder

    //陪伴者改訂單狀態
    @PUT("rest/companion/order/orderStatus/update")
    suspend fun comOrderUpdate(@Body comOrder:CompanionOrder):CompanionOrder
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