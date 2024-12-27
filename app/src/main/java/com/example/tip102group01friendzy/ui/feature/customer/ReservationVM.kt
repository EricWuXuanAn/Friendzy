package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReservationVM:ViewModel() {
    private val _reservationState = MutableStateFlow(emptyList<Reservation>())
    var reservationState:StateFlow<List<Reservation>> = _reservationState.asStateFlow()

}