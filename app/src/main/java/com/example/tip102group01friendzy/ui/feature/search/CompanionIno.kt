package com.example.tip102group01friendzy.ui.feature.search

import com.google.android.gms.maps.model.LatLng

// CompanionInfo 資料類
data class CompanionInfo(
    val id: String,
    val name: String,
    val description: String,
    val memberName: String,
    val location: LatLng,
    val s: String,
    val avatar3: Int)
{
    val expertise: String
        get() {
            TODO()
        }
    val introduction: Any
        get() {
            TODO()
        }
    val profilePicRes: Int
        get() {
            TODO()
        }
}
