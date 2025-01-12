package com.example.tip102group01friendzy.ui.feature.search

import com.example.tip102group01friendzy.R
import com.google.android.gms.maps.model.LatLng

fun getMockCompanions(): List<CompanionInfo> = listOf(
    CompanionInfo("1", "Nita", "搬家&油漆幫手", "信義區", LatLng(25.0330, 121.5654), "專長1", R.drawable.avatar3),
    CompanionInfo("2", "Becca", "松山區練跑", "松山區", LatLng(25.0573, 121.5637), "專長2", R.drawable.avatar3),
    CompanionInfo("3", "Tim", "Android tutor", "大安區", LatLng(25.0250, 121.5439), "專長3", R.drawable.avatar3),
   CompanionInfo("4", "GY", "DB tutor", "美國區", LatLng(37.4091, -122.0985), "專長3", R.drawable.avatar3)
)