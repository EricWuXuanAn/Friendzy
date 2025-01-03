package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CompanionAppointmentVM():ViewModel(){
    private val _appointmentState = MutableStateFlow(emptyList<Appointment>())
    val appointmentState = _appointmentState.asStateFlow()

    init {
        _appointmentState.value = fetchAppointment()
    }

    private val _setAppointmentState = MutableStateFlow(Appointment())
    val setAppointment = _setAppointmentState.asStateFlow()

    private fun fetchAppointment() :List<Appointment>{
        return listOf(
            Appointment("名字A","",1,true,"標題01","2024/01/01 12:00","2024/01/01 14:00","台北市","中正區"),
            Appointment("名字B","",2,true,"標題02","2024/01/02 12:00","2024/01/02 14:00","基隆市","中山區"),
            Appointment("名字C","",3,true,"標題03","2024/01/03 12:00","2024/01/03 14:00","基隆市","七堵區"),
        )
    }


}

data class Appointment(
    var memberName:String = "名字AAA",//對方名字
    var memberImg:String = "",//對方頭像
    var memberId:Int = 0,//對方編號
    var posterStatus: Boolean = true, //刊登身分 T陪伴者 F顧客
    var serviceTitle: String = "",//我刊登標題O
    var startTime: String = "",//開始時間O
    var endTime: String = "",//結束時間O
    var serviceCity: String = "",//服務位置 城市
    var serviceDistrict: String = "",//服務位置 區

){

}