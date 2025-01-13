package com.example.tip102group01friendzy.ui.feature.search

import com.example.tip102group01friendzy.R
import com.google.android.gms.maps.model.LatLng

fun getMockCompanions(): List<CompanionInfo> = listOf(
    CompanionInfo("1", "Johnny", "Love to travel and explore new places", "信義區", LatLng(25.0330, 121.5654), "專長1", R.drawable.avatar3),
    CompanionInfo("2", "Johnny", "Love to travel and explore new places", "松山區", LatLng(25.0573, 121.5637), "專長2", R.drawable.avatar3),
    CompanionInfo("3", "Johnny", "Love to travel and explore new places", "大安區", LatLng(25.0250, 121.5439), "專長3", R.drawable.avatar3),
)