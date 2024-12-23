package com.example.tip102group01friendzy.ui.feature.companion

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit
){
    val datePickerState = rememberDatePickerState()

}

fun MyTimePickerDialog(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit
){

}