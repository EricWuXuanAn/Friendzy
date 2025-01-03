package com.example.tip102group01friendzy.ui.feature.customer

data class OrderList (
    val order_id: Int,
    val member_name:String,
    val service_idno: Int,
    val order_person: String,
    val customer_rate: Int,
    val custoner_rate_content: String,
    val companion_rate: Int,
    val cmpanion_rate_content:String,
    var order_pirce:Double,
    var order_status: Int,
    var order_poster:String
){
//    override fun equals(other: Any?): Boolean {
//        return this.orderID == (other as OrderList).orderID
//    }
//
//    override fun hashCode(): Int {
//        return orderID.hashCode()
//    }
}