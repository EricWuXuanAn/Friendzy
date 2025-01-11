package com.example.tip102group01friendzy.ui.feature.search

import com.google.android.gms.maps.model.LatLng

// CompanionInfo 資料類
data class CompanionInfo(
    val id: String,
    val nickname: String,
    val description: String,
    val memberName: String,
    val location: LatLng,
    val s: String,
    val avatar3: Int)

//CompanionInfo("1", "Nita", "搬家&油漆幫手", "信義區",LatLng(25.0330, 121.5654), "專長1", R.drawable.avatar3 ),