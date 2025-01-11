package com.example.tip102group01friendzy

import com.example.tip102group01friendzy.ui.feature.account.MemberInfo
import com.example.tip102group01friendzy.ui.feature.chat.Chatroom
import com.example.tip102group01friendzy.ui.feature.customer.Black_List
import com.example.tip102group01friendzy.ui.feature.customer.Favorite_List
import com.example.tip102group01friendzy.ui.feature.customer.OrderList
import com.example.tip102group01friendzy.ui.feature.customer.Post
import com.example.tip102group01friendzy.ui.feature.customer.addPostRequest
import com.example.tip102group01friendzy.ui.feature.customer.addPostResponse
import com.example.tip102group01friendzy.ui.feature.customer.requestDelete
import com.example.tip102group01friendzy.ui.feature.customer.selectedOrderListRequest
import com.example.tip102group01friendzy.ui.feature.customer.selectedPostList
import android.accounts.Account
import com.example.tip102group01friendzy.ui.feature.companion.Applicant
import com.example.tip102group01friendzy.ui.feature.companion.ComArea
import com.example.tip102group01friendzy.ui.feature.companion.ComPublish
import com.example.tip102group01friendzy.ui.feature.companion.ComSkill
import com.example.tip102group01friendzy.ui.feature.companion.CompanionOrder
import com.example.tip102group01friendzy.ui.feature.companion.MyPublish
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.lang.reflect.Member
import java.net.CookieManager
import java.net.CookiePolicy
import com.example.tip102group01friendzy.ui.feature.companion.CompanionOrder as CompanionOrder1

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

    @POST("rest/member/login")
    suspend fun login(@Body member: com.example.tip102group01friendzy.Member): MemberInfo

    @GET("rest/member/find")
    suspend fun findMemberInfo(): MemberModel.MemberInfoResponse?

    @POST("rest/member/edit")
    suspend fun editpassword(@Body request: MemberModel.editpassword): Response<Unit>

    @POST("rest/member/edit")
    suspend fun editnickname(@Body request: MemberModel.editnickname): Response<Unit>

    @POST("rest/member/edit")
    suspend fun editphone(@Body request: MemberModel.editphone): Response<Unit>

    @POST("rest/member/edit")
    suspend fun editintroduction(@Body request: MemberModel.editintroduction): Response<Unit>

//    @GET("rest/member/login/{email}/{mpassword}")
//    suspend fun login(@Path("email") account: String, @Path("mpassword")mpasswod: String):Response<Unit>


    //陪伴者訂單管理
    @GET("rest/companion/order/showAll/{memberNo}")
    suspend fun comShowAllOrder(@Path("memberNo") memberNo: Int):List<CompanionOrder1>

    //陪伴者訂單明細
    @GET("rest/companion/order/showId/{memberNo}/{servicePoster}/{orderId}")
    suspend fun comOrderDetails(
        @Path("memberNo") memberNo: Int, @Path("servicePoster") servicePoster: Int, @Path("orderId") orderId: Int,
    ): CompanionOrder1

    //陪伴者改訂單狀態
    @PUT("rest/companion/order/statusUp")
    suspend fun comOrderUpdate(@Body comOrder: CompanionOrder1): CompanionOrder1

    @GET("rest/chatrooms")
    suspend fun showAllChatrooms(): List<Chatroom>

    @POST("rest/customer/selectedOrderList")
    suspend fun selectedorderList(@Body request: selectedOrderListRequest): OrderList

    @POST("rest/customer/updateStatus")
    suspend fun myRequestList(@Body request: selectedPostList)

    @POST("rest/customer/deleteBlackList")
    suspend fun deleteBlackList(@Body request: requestDelete)

    @POST("rest/customer/updateStatus")
    suspend fun updateOrderStatus(@Body request: selectedOrderListRequest)

    @POST("rest/chatroom/create/{otherUserId}")
    suspend fun createChatroom(@Path("otherUserId") otherUserId: Int): Response<Chatroom>

    @POST("rest/customer/updateDeclineStatus")
    suspend fun updateDeclineStatus(@Body request: selectedPostList)
    //應徵者清單
    @GET("rest/companion/appoint/showAll/{memberNo}")
    suspend fun showAllApplicants(@Path("memberNo") memberNo: Int):List<Applicant>

    //應徵者詳細資訊
    @GET("rest/companion/appoint/showId/{memberNo}/{account}/{serviceId}")
    suspend fun showApplicantByAccount(
        @Path("memberNo") memberNo: Int,@Path("account") account: Int,@Path("serviceId") serviceId: Int
    ):Applicant

    //陪伴者主頁的預約清單
    @GET("rest/companion/publish/showAll/{memberNo}")
    suspend fun showAllPublish(@Path("memberNo") memberNo: Int):List<ComPublish>

    //陪伴者看預約詳細資料
    @GET("rest/companion/publish/showId/{serviceId}/{memberNo}")
    suspend fun showPublishDetail(@Path("serviceId") serviceId: Int,@Path("memberNo") memberNo: Int):ComPublish

    //陪伴者刊登頁取得專長
    @GET("rest/companion/publish/skill")
    suspend fun comPublishSkill(): List<ComSkill>

    //陪伴者刊登頁取得地區
    @GET("rest/companion/publish/area")
    suspend fun comPublishArea(): List<ComArea>

    //陪伴者刊登新增
    @POST("rest/companion/publish/addPublish")
    suspend fun addPublish(@Body myPublish: MyPublish): Int

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