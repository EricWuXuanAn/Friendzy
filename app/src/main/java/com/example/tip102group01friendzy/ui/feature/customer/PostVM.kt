package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.RetrofitInstance

class PostVM() : ViewModel() {

    suspend fun postOrder(
        service: String,
        service_charge: Double,
        service_poster: Int,
        start_time: Long,
        finished_time: Long,
        service_status: Int,
        post_status: Int,
        service_detail: String
    ): addPostResponse {
        val response = RetrofitInstance.api.addNewPost(
            addPostRequest(
                service = service,
                service_charge = service_charge,
                service_poster = service_poster,
                start_time = start_time,
                finished_time = finished_time,
                service_status = service_status,
                poster_status = post_status,
                service_detail = service_detail
            )
        )
        return response
    }
}