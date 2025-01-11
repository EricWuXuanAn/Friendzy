package com.example.tip102group01friendzy.ui.feature.customer

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.RetrofitInstance

class ReservationConfirmVM(

):ViewModel() {
//    private val _reservationComfirmState = MutableStateFlow(OrderList())
//    var reservaConfirmtionState: StateFlow<OrderList> = _reservationComfirmState.asStateFlow()


    suspend fun updateOrderStatus(service_id: Int){
        val response = RetrofitInstance.api.updateOrderStatus(
            selectedOrderListRequest(service_id = service_id)
        )
        return response
    }
    suspend fun getSelectedPostList(
        service_id: Int
    ): Post{
        val response = RetrofitInstance.api.selectedPostList(
            selectedPostList(service_id = service_id)
        )
        return response
    }

    suspend fun getSelectOrder(order_id: Int):OrderList{
        Log.d("tag_", "order_idVM: $order_id")
        val order = RetrofitInstance.api.selectedorderList(
            selectedOrderListRequest(service_id = order_id)
        )
        return order
    }

    suspend fun updateDeclineStatus(service_id: Int){
        val response = RetrofitInstance.api.updateDeclineStatus(
            selectedPostList(service_id = service_id)
        )
        return response
    }
}