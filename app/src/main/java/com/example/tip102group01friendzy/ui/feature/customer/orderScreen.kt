package com.example.tip102group01friendzy.ui.feature.customer

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import kotlinx.coroutines.launch


@Composable
fun OrderListScreen(
    orderlistVM: OrderVM,
    customerVM: CustomerVM,
    navController: NavHostController,
    context: Context
) {
    val my_requestList by customerVM.recommendPostListState.collectAsState()
    val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val order_person = preferences.getInt("member_no", 0)
    var tabIndex by remember { mutableStateOf(0) }
    val ordeState by orderlistVM.orderList.collectAsState()
    Log.d("tag_orderState", "orderState: $ordeState")
    val uncomfirm = ordeState.filter { it.order_status == 0 &&  (it.order_person == order_person || it.order_poster.toInt() == order_person)}
    val inProfress = ordeState.filter { it.order_status == 1 && (it.order_person == order_person || it.order_poster.toInt() == order_person)}
    val completed = ordeState.filter { it.order_status == 2 && (it.order_person == order_person || it.order_poster.toInt() == order_person)}
    val reservation = ordeState.filter { it.order_status == 3 &&(it.order_person == order_person || it.order_poster.toInt() == order_person)}
    Log.d("tag_un", "unknowCode: $reservation")
    val my_request = my_requestList.filter { it.service_status == 0 && it.service_poster == order_person}
    Log.d("tag_un", "myRequest: $my_request")

    val tab = listOf(
        stringResource(R.string.order_List),
        stringResource(R.string.unconfirm),
        stringResource(R.string.in_progress),
        stringResource(R.string.complete),
        stringResource(R.string.myreservation),
        stringResource(R.string.my_request)
    )
    val scope = rememberCoroutineScope()
    var snackbar = remember { SnackbarHostState() }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                edgePadding = 0.dp
            ) {
                tab.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = title, softWrap = false) },
                        selected = index == tabIndex,
                        onClick = { tabIndex = index },
                        selectedContentColor = colorResource(R.color.teal_700),
                        unselectedContentColor = Color.Gray,
                        icon = {
                            when (index) {
                                0 -> Icon(
                                    painter = painterResource(R.drawable.orderlist),
                                    contentDescription = "orderlist"
                                )

                                1 -> Icon(
                                    painter = painterResource(R.drawable.unconirm),
                                    contentDescription = "unconfirm"
                                )

                                2 -> Icon(
                                    painter = painterResource(R.drawable.inprogress),
                                    contentDescription = "inprogress"
                                )

                                3 -> Icon(
                                    painter = painterResource(R.drawable.check_circle),
                                    contentDescription = "complete"
                                )

                                4 -> Icon(
                                    painter = painterResource(R.drawable.date_range),
                                    contentDescription = "Reservation"
                                )

                                5 -> Icon(
                                    painter = painterResource(R.drawable.request_24),
                                    contentDescription = "request"
                                )
                            }
                        }
                    )
                }
            }

        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
        )
        when (tabIndex) {
            0 -> orderList(orders = ordeState, onClick = {
                scope.launch {
                   navController.navigate(route = "${Screen.CompanionOrderDetailsScreen.name}/${it.order_id}")
                }
            })

            1 -> orderList(orders = uncomfirm, onClick = {
                scope.launch {
                    navController.navigate(route = "${Screen.CompanionOrderDetailsScreen.name}/${it.order_id}")
                }
            })

            2 -> orderList(orders = inProfress, onClick = {
                scope.launch {
                    navController.navigate(route = "${Screen.CompanionOrderDetailsScreen.name}/${it.order_id}")
                }
            })

            3 -> orderList(orders = completed, onClick = {
                scope.launch {
                    navController.navigate(route = "${Screen.CompanionOrderDetailsScreen.name}/${it.order_id}")
                }
            })

            4 -> orderList(orders = reservation, onClick = {
               navController.navigate(route = "${Screen.CompanionOrderDetailsScreen.name}/${it.order_id}")
            })

            5 -> servicerList(orders = my_request, onClick = {
                navController.navigate(route = "${Screen.ReservationConfirmScreen.name}/${it.service_id}")
                Log.d("tag_", "service_id: ${it.service_id}")
            })
        }
        SnackbarHost(hostState = snackbar)
    }
}

@Composable
fun orderList(
    orders: List<OrderList>,
    onClick: (OrderList) -> Unit
) {
    LazyColumn {
        items(orders) { order ->
            ListItem(
                modifier = Modifier.clickable {
                    Log.d("tag_", "order: $order")
                    onClick(order) },
                headlineContent = { Text(text = "order Title: ${order.order_title}") },
                overlineContent = { Text(text = "Order ID: ${order.order_id}", fontSize = 18.sp) },
                supportingContent = { Text(text = "Order Person:${order.member_name}") },
                trailingContent = {
                    Text(
                        text = "Order Price: \n${order.order_price}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            )
            HorizontalDivider()
        }
    }
}
@Composable
fun servicerList(
    orders: List<Post>,
    onClick: (Post) -> Unit
) {
    LazyColumn {
        items(orders) { order ->
            ListItem(
                modifier = Modifier.clickable {
                    Log.d("tag_", "order: $order")
                    onClick(order) },
                headlineContent = { Text(text = "order Title: ${order.service}") },
                overlineContent = { Text(text = "Order ID: ${order.service_id}", fontSize = 18.sp) },
                supportingContent = { Text(text = "Order Person:${order.member_name}") },
                trailingContent = {
                    Text(
                        text = "Order Price: \n${order.service_charge}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            )
            HorizontalDivider()
        }
    }
}



//@Composable
//@Preview(showBackground = true)
//fun OrderListScreenPreview() {
//    OrderListScreen(orderlistVM = OrderVM(), navController = rememberNavController())
//}