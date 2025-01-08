package com.example.tip102group01friendzy.ui.feature.customer

data class OrderList (
    val order_id: Int ,
    val member_name:String = "",
    val service_idno: Int = 0,
    val order_person: String = "",
    val customer_rate: Int = 0,
    val custoner_rate_content: String = "",
    val companion_rate: Int = 0,
    val cmpanion_rate_content:String = "",
    var order_price:Double = 0.0,
    var order_status: Int = 0,
    var order_poster:String = "",
    val order_title:String = "",
    val service_detail: String = "",
    val start_time: Long = 0L,
    val finished_time: Long = 0L
)

data class selectedOrderListRequest(
    val order_id: Int
)

data class OrderListNullable (
    val order_id: Int? = null,
    val member_name:String? = null,
    val service_idno: Int? = null,
    val order_person: String? = null,
    val customer_rate: Int? = null,
    val custoner_rate_content: String? = null,
    val companion_rate: Int? = null,
    val cmpanion_rate_content:String? = null,
    var order_pirce:Double? = null,
    var order_status: Int? = null,
    var order_poster:String? = null,
    val order_title:String? = null,

)
