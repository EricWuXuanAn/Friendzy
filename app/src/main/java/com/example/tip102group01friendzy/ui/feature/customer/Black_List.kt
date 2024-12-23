package com.example.tip102group01friendzy.ui.feature.customer

import com.example.tip102group01friendzy.R

class Black_List(
    var userId: Int,
    var black_account: Int,
    var black_accountImg: Int = R.drawable.ic_launcher_foreground,
    var content: String
) {
    override fun equals(other: Any?): Boolean {
        return this.black_account == (other as Black_List).black_account
    }

    override fun hashCode(): Int {
        return this.black_account.hashCode()
    }
}