package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReservationVM:ViewModel() {
    private val _reservationState = MutableStateFlow(Post())
    var reservationState:StateFlow<Post> = _reservationState.asStateFlow()



    suspend fun getNewStatus(
        service_id: Int
    ){
        val response = RetrofitInstance.api.myRequestList(
            selectedPostList(service_id = service_id)
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
}