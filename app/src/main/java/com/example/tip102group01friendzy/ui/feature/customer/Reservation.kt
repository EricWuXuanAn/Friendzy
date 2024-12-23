package com.example.tip102group01friendzy.ui.feature.customer

class Reservation(
    var posterID: String = "",
    var specialty: String = "",
    var startTime: String = "",
    var ecndTime: String = "",
    var location: String = "",
    var price: Double
) {
    override fun equals(other: Any?): Boolean {
        return this.posterID == (other as Reservation).posterID
    }

    override fun hashCode(): Int {
        return this.posterID.hashCode()
    }
}