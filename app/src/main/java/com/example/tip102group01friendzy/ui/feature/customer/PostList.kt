package com.example.tip102group01friendzy.ui.feature.customer

import com.example.tip102group01friendzy.R

data class PostList(
    var postID: String = "",
    var postTitle: String = "",
    var postContent: String = "",
    var postImg: Int = R.drawable.buddy
//    var reservationState: Boolean = false,
//    var reservationResult: Boolean = false
//    var sartTime:Long,
//    var endTime:Long
){
    override fun equals(other: Any?): Boolean {
        return this.postID == (other as PostList).postID
    }

    override fun hashCode(): Int {
        return this.postID.hashCode()
    }
}