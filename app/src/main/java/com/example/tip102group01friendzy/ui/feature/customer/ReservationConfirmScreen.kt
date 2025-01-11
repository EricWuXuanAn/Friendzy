package com.example.tip102group01friendzy.ui.feature.customer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern


@Composable
fun ReservationConfirmScreen(
    navController: NavHostController,
    reservationConfirmVM: ReservationConfirmVM,
    service_id: Int,
    orderVM: OrderVM
) {
//    Log.d("tag_pass", "order_idPass $order_id")
    val scope = rememberCoroutineScope()
    var snackbarHostState = remember { SnackbarHostState() }
    val dateFormat = ofPattern("YYYY-MM-dd")
    var startDate by remember { mutableStateOf(LocalDate.now().format(dateFormat)) }
    var endDate by remember { mutableStateOf(LocalDate.now().format(dateFormat)) }
//    var startTime by remember { mutableStateOf("HH") }
//    var endTime by remember { mutableStateOf("HH") }
    var location by remember { mutableStateOf("台北") }
    var orderState by remember { mutableStateOf<Post?>(null) }
    Log.d("tag_222", "orderState: $orderState")


    LaunchedEffect(Unit) {
        scope.launch {
           orderState = reservationConfirmVM.getSelectedPostList(service_id = service_id)
            orderVM.getOrderList()
        }

    }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val startTime: Long? = orderState?.start_time
    val finishedTime = orderState?.finished_time
    val startTimeFormatter: String? = startTime?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().format(dateFormatter)
    }
    val finishedTimeFormatter: String? = finishedTime?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().format(dateFormatter)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(5.dp)
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.Gray, shape = CircleShape),
                painter = painterResource(R.drawable.friendzy),
                contentDescription = "Image"
            )
            Text(
                text = "Name: \n ${orderState?.member_name}",
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = { navController.navigate(Screen.ChatroomScreen.name) }
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.chat),
                        contentDescription = "Chat"
                    )
                }
            }
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, top = 40.dp),
            text = "POST CONTENT: ",
            fontFamily = FontFamily.Cursive,
            fontSize = 25.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = "${orderState?.service_detail}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, top = 20.dp, bottom = 20.dp),
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(0.87f).padding(top = 20.dp, bottom = 20.dp),
            text = "Start at: ${startTimeFormatter}\n \nEnd at: ${finishedTimeFormatter}\n \nPrice: ${orderState?.service_charge}",
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, bottom = 60.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Bottom

        ) {
            Button(
                modifier = Modifier.weight(0.3f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.DarkGray
                ),
                onClick = {
                    scope.launch {
                        reservationConfirmVM.updateOrderStatus(service_id = service_id)
                        delay(1000)
                        snackbarHostState.showSnackbar(
                            message = "Order Confirm !",
                            withDismissAction = true
                        )
                        delay(2000)
                    }
                    navController.popBackStack()
                }
            ) {
                Text(text = stringResource(R.string.Confirm))
            }
            Button(
                modifier = Modifier.weight(0.3f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.DarkGray
                ),
                onClick = {
                    scope.launch {
                        reservationConfirmVM.updateDeclineStatus(service_id = service_id)
                        delay(1000)
                        snackbarHostState.showSnackbar(
                            message = "Successfully Declined",
                            withDismissAction = true
                        )
                        delay(2000)
                    }
                    navController.popBackStack()
                }
            ) {
                Text(text = "Decline")
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun RSCpreview() {
    ReservationConfirmScreen(rememberNavController(), ReservationConfirmVM(), service_id = 1, orderVM = OrderVM())
}