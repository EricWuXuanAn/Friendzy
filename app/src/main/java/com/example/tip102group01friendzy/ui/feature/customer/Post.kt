package com.example.tip102group01friendzy.ui.feature.customer

data class Post(
    val service_id: Int = 0,
    val service_poster: Int = 0,
    val service: String = "",
    val service_detail: String? = null,
    val service_pic: String?= null,
    val start_time: Long = 0L ,
    val finished_time: Long = 0L ,
    val service_charge: Double = 0.0,
    val service_status: Int = 0,
    val service_location: String? = "",
    val poster_status: Int = 0,
    val member_name: String = ""
)

data class addPostRequest(
    val service: String,
    val service_charge: Double,
    val service_poster: Int,
    val start_time: Long,
    val finished_time: Long,
    val service_status: Int,
    val poster_status: Int,
    val service_detail: String
)

data class addPostResponse(
    val service: String,
    val service_charge: Double,
    val service_poster: Int,
    val start_time: Long,
    val finished_time: Long,
)
data class selectedPostList(
    val service_id: Int
)

