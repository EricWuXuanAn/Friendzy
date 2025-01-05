package com.example.tip102group01friendzy

import com.example.tip102group01friendzy.ui.feature.account.MemberInfo
import com.example.tip102group01friendzy.ui.feature.chat.Chatroom
import com.example.tip102group01friendzy.ui.feature.customer.Black_List
import com.example.tip102group01friendzy.ui.feature.customer.Favorite_List
import com.example.tip102group01friendzy.ui.feature.customer.OrderList
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.lang.reflect.Member
import java.net.CookieManager
import java.net.CookiePolicy

interface APIService {
    //    @Multipart 不要打開
    @POST("rest/member/register")
    suspend fun createMember(@Body request: CreateMemberRequest): CreatMemberResponse

    @POST("rest/member/forget")
    suspend fun forgetPassword(@Body member: Member) //FIXME

    //拿到訂單資料
    @GET("rest/customer/orderList/showAllList")
    suspend fun showAllOrderList(): List<OrderList>

    @GET("rest/customer/blackList")
    suspend fun showAllBlackList(): List<Black_List>

    @GET("rest/customer/favoriteList")
    suspend fun showAllFavoriteList(): List<Favorite_List>

    @POST("rest/member/login")
    suspend fun login(@Body member: com.example.tip102group01friendzy.Member): MemberInfo

    @GET("rest/chatrooms")
    suspend fun showAllChatrooms(): List<Chatroom>
}

object RetrofitInstance {
    val client = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(CookieManager().apply {
            setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        }))
        .build()

    val api: APIService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/friendzy-new-web/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

    }
}