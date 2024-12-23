package com.example.tip102group01friendzy.ui.feature.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ReservationScreen(
    navController: NavHostController,
    reservationVM: ReservationVM
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

    }
}






@Composable
@Preview(showBackground = true)
fun ReservationScreenPreview(){
    ReservationScreen(rememberNavController(), ReservationVM())
}