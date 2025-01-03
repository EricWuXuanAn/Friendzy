package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//區域VM
class LocationVM : ViewModel() {
    private val _loactionState = MutableStateFlow(emptyList<Location>())
    val locationState = _loactionState.asStateFlow()

    init {
        _loactionState.value = fetchLocation()
    }

    private fun fetchLocation(): List<Location> {
        return listOf(
            Location("000001", "基隆市", "七堵區"),
            Location("000002", "基隆市", "中山區"),
            Location("000003", "基隆市", "中正區"),
            Location("000004", "基隆市", "信義區"),
            Location("000005", "基隆市", "安樂區"),
            Location("000006", "基隆市", "暖暖區"),
            Location("000007", "基隆市", "仁愛區"),
            Location("000008", "台北市", "中正區"),
            Location("000009", "台北市", "大同區"),
            Location("000010", "台北市", "中山區"),
            Location("000011", "台北市", "松山區"),
            Location("000012", "台北市", "大安區"),
            Location("000013", "台北市", "萬華區"),
            Location("000014", "台北市", "信義區"),
            Location("000015", "台北市", "士林區"),
            Location("000016", "台北市", "北投區"),
            Location("000017", "台北市", "內湖區"),
            Location("000018", "台北市", "南港區"),
            Location("000019", "台北市", "文山區"),
            Location("000020", "新北市", "板橋區"),
            Location("000021", "新北市", "新莊區"),
            Location("000022", "新北市", "中和區"),
            Location("000023", "新北市", "永和區"),
            Location("000024", "新北市", "土城區"),
            Location("000025", "新北市", "三峽區"),
            Location("000026", "新北市", "樹林區"),
            Location("000027", "新北市", "鶯歌區"),
            Location("000028", "新北市", "三重區"),
            Location("000029", "新北市", "蘆洲區"),
            Location("000030", "新北市", "五股區"),
            Location("000031", "新北市", "泰山區"),
            Location("000032", "新北市", "林口區"),
            Location("000033", "新北市", "淡水區"),
            Location("000034", "新北市", "金山區"),
            Location("000035", "新北市", "八里區"),
            Location("000036", "新北市", "萬里區"),
            Location("000037", "新北市", "石門區"),
            Location("000038", "新北市", "三芝區"),
            Location("000039", "新北市", "瑞芳區"),
            Location("000040", "新北市", "汐止區"),
            Location("000041", "新北市", "平溪區"),
            Location("000042", "新北市", "雙溪區"),
            Location("000043", "新北市", "貢寮區"),
            Location("000044", "新北市", "深坑區"),
            Location("000045", "新北市", "石碇區"),
            Location("000046", "新北市", "新店區"),
            Location("000047", "新北市", "坪林區"),
            Location("000048", "新北市", "烏來區"),
        )
    }
}
//區域類別
data class Location(var locationNumber: String = "", var city: String = "", var district: String = "") {
    override fun equals(other: Any?): Boolean {
        return this.locationNumber == (other as Location).locationNumber
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}