package com.example.tip102group01friendzy.ui.feature.customer

import com.example.tip102group01friendzy.R

class Favorite_List(
    var hunterID:Int ,
    var hunterName: String = "",
    var beHuntedID: Int ,
    var beHuntedImg: Int = R.drawable.ic_launcher_foreground
) {
    override fun equals(other: Any?): Boolean {
        return this.beHuntedID == (other as Favorite_List).beHuntedID
    }

    override fun hashCode(): Int {
        return this.beHuntedID.hashCode()
    }
}