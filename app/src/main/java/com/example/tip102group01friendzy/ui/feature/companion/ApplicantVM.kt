package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompanionApplicantVM():ViewModel() {
    //全部應徵者
    private val _applicantListState = MutableStateFlow(emptyList<Applicant>())
    val appointmentState = _applicantListState.asStateFlow()

    //單個應徵者細項
    private val _applicantSelectState = MutableStateFlow<Applicant?>(null)
    val applicantSelectState = _applicantSelectState.asStateFlow()

    //全部應徵者基本資訊
    fun getApplicantList(memberNo: Int) {
        viewModelScope.launch {
            val value = fetchAppointment(memberNo)
            _applicantListState.value = value
        }
    }

    //單個應徵者詳細資訊
    fun getApplicantSelect(memberNo: Int, account: Int, serviceId: Int) {
        viewModelScope.launch {
            val value = fetchAppointmentById(memberNo, account, serviceId)
            _applicantSelectState.update { value }
        }
    }

    //確認&拒絕
    fun setApplicantStatus(serviceId: Int, account: Int, reject: Int) {
        viewModelScope.launch {
            val value = fetchStatusUpdate(
                Applicant(
                    serviceId = serviceId,
                    accountId = account,
                    reject = reject
                )
            )
        }
    }


    //呼叫API取得全部應徵者基本資訊
    private suspend fun fetchAppointment(memberNo: Int): List<Applicant> {
        try {
            val list = RetrofitInstance.api.showAllApplicants(memberNo)
            return list
        } catch (e: Exception) {
            return emptyList()
        }
    }

    //呼叫API取得單個應徵者詳細資訊
    private suspend fun fetchAppointmentById(
        memberNo: Int,
        account: Int,
        serviceId: Int
    ): Applicant {
        try {
            val appointment =
                RetrofitInstance.api.showApplicantByAccount(memberNo, account, serviceId)
            return appointment
        } catch (e: Exception) {
            return Applicant()
        }
    }

    //呼叫API更改應徵者狀態
    private suspend fun fetchStatusUpdate(applicant: Applicant): Applicant {
        try {
            val status = RetrofitInstance.api.comAppointUpdateRate(
                applicant
            )
            return status
        } catch (e: Exception) {
            return Applicant()
        }
    }
}

data class Applicant(

    var orderId: Int? = null,//訂單編號
    var serviceId:Int? =null,//服務編號
    var orderStatus: Int? = null,//訂單狀態

    var theirId: Int? = null,//對方Id
    var theirName: String? = null,//對方名字
    var orderPersonName: String? = null,//訂購人名字
    var orderPosterName:String? =null,//刊登者名字
    var orderPoster:Int? =null,//刊登者ID

    var service: String? = null,//標題
    var serviceDatil: String? = null,//內容
    var startTime: Long? = null,//開始時間
    var endTime: Long? = null,//結束時間
    var orderPrice: Double? = null,//金額
    var area: String? = null,//地區

    var accountId:Int? =null,//應徵者ID
    var accountName: String? = null,//應徵者名字
    var applyStatus:Int? =null,//應徵的狀態
    var applicationResult:Int? =null,//應徵結果

    var memberNo: Int? = null,
    var reject:Int? = null,//拒絕狀態

){

}