package com.example.tip102group01friendzy.ui.feature.customer

class OrderList (
    var orderID: Int,
    var service_IDNO: Int,
    var order_Person: String = "",
    var order_Pirce:Double,
    var order_Status: Int,
    var order_content:String = ""
){
    override fun equals(other: Any?): Boolean {
        return this.orderID == (other as OrderList).orderID
    }

    override fun hashCode(): Int {
        return orderID.hashCode()
    }
}