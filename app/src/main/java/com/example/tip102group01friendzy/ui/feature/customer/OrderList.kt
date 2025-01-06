package com.example.tip102group01friendzy.ui.feature.customer

data class OrderList (
    val order_id: Int = 0,
    val member_name:String = "",
    val service_idno: Int = 0,
    val order_person: String = "",
    val customer_rate: Int = 0,
    val custoner_rate_content: String = "",
    val companion_rate: Int = 0,
    val cmpanion_rate_content:String = "",
    var order_pirce:Double = 0.0,
    var order_status: Int = 0,
    var order_poster:String = "",
    val order_title:String = ""
){
//    override fun equals(other: Any?): Boolean {
//        return this.orderID == (other as OrderList).orderID
//    }
//
//    override fun hashCode(): Int {
//        return orderID.hashCode()
//    }
}